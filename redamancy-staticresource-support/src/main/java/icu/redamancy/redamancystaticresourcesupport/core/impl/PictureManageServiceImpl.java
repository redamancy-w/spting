package icu.redamancy.redamancystaticresourcesupport.core.impl;

import com.aliyun.ocr_api20210707.models.RecognizeHouseholdRequest;
import com.aliyun.ocr_api20210707.models.RecognizeIdcardRequest;
import com.aliyun.ocr_api20210707.models.RecognizeIdcardResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teautil.models.RuntimeOptions;
import icu.redamancy.common.model.dao.picture.service.PictureService;
import icu.redamancy.common.model.pojo.picture.Picture;
import icu.redamancy.common.serviceinterface.staticresource.PictureManageService;
import icu.redamancy.common.properties.MinioProperties;
import icu.redamancy.common.utils.exceptionhandling.BaseException;
import icu.redamancy.common.utils.handlefile.ImageHandle;
import icu.redamancy.common.utils.result.ResultCode;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.SneakyThrows;
import org.apache.dubbo.config.annotation.DubboService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author redamancy
 * @Date 2022/5/15 22:55
 * @Version 1.0
 */

@Component
@DubboService
public class PictureManageServiceImpl implements PictureManageService {

    private static final String[] ALLOWED_EXTENSIONS = {
            ".jpg", ".img", ".png", ".gif", ".jpeg"
    };

    @Resource
    private PictureService pictureService;

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioProperties minioProperties;

    @Autowired
    private com.aliyun.ocr_api20210707.Client aLiYunOrcClient;

    @Override
    public String parsing(String type, MultipartFile file) {
        if (type.equals("idcard")) {
            return IdCard(file);
        } else {
            return householdRegister(file);
        }
    }

    
    @SneakyThrows
    private String householdRegister(MultipartFile file) {
        InputStream in = file.getInputStream();
        RecognizeHouseholdRequest recognizeHouseholdRequest = new RecognizeHouseholdRequest()
                .setBody(in);
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            return aLiYunOrcClient.recognizeHouseholdWithOptions(recognizeHouseholdRequest, runtime).body.data;
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }

        return null;
    }

    @SneakyThrows
    private String IdCard(MultipartFile file) {
        InputStream in = file.getInputStream();
        RecognizeIdcardRequest recognizeIdcardRequest = new RecognizeIdcardRequest()
                .setBody(in);
        RuntimeOptions runtime = new RuntimeOptions();
        try {
            // 复制代码运行请自行打印 API 的返回值
            RecognizeIdcardResponse re = aLiYunOrcClient.recognizeIdcardWithOptions(recognizeIdcardRequest, runtime);

            System.out.println(re.body.data);
            return re.body.data;
        } catch (TeaException error) {
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);
            // 如有需要，请打印 error
            com.aliyun.teautil.Common.assertAsString(error.message);
        }
        return null;
    }

    @SneakyThrows
    @Override
    public List<Picture> upload(String objectName, String userId, MultipartFile[] file) {

        this.validationBucket();

        List<Picture> orgFilesList = new ArrayList<>(file.length);

        for (MultipartFile multipartfile : file) {
            String orgFileName = multipartfile.getOriginalFilename();
            assert orgFileName != null;


//            文件后缀
            String fileSuffix = orgFileName.substring(orgFileName.lastIndexOf("."), orgFileName.length());
//            文件存储名字
            String filename = this.createFileName(objectName, fileSuffix, userId);
//            查看文件后缀是否合法
            if (!isValidExtension(fileSuffix)) {
                throw new BaseException(ResultCode.INTERNAL_SERVER_ERROR.code(), "文件格式错误");
            }

//            将文件存入服务器
            String filepath = "/" + objectName + "/";

            MultipartFile newFile = null;

            if (multipartfile.getSize() >= (1024 * 1024)) {
                newFile = ImageHandle
                        .getImageHandle()
                        .compressImage(multipartfile, 1f, 0.2f, filename);

            } else {

                newFile = multipartfile;

            }
            try {
                InputStream in = newFile.getInputStream();
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(minioProperties.getBucketname())
                                .object(filepath + filename)
                                .stream(in, newFile.getSize(), -1)
                                .contentType(multipartfile.getContentType())
                                .build());

                in.close();

            } catch (IOException | ErrorResponseException | InsufficientDataException | InternalException |
                     InvalidKeyException | InvalidResponseException | NoSuchAlgorithmException | ServerException |
                     XmlParserException e) {
                throw new BaseException(ResultCode.INTERNAL_SERVER_ERROR.code(), "发布失败");
            }
//            将存入到minio中到文件信息返回
            orgFilesList.add(new Picture()
                    .setFilename(filename)
                    .setFilepath(minioProperties.getBucketname() + filepath + filename)
                    .setUserId(Long.valueOf(userId))
                    .setObjectName(objectName)
            );

        }

        return orgFilesList;
    }

    @Override
    public List<Long> addPictureToDS(List<Picture> pictureList) {
//        List<Picture> pictures = pictureBOList.stream().map(v -> {
//            return new Picture()
//                    .setObjectName(objectName)
//                    .setUserId(Integer.valueOf(userId))
//                    .setFilename(v.getFilename())
//                    .setFilepath(v.getFilepath());
//        }).collect(Collectors.toList());


        List<Long> idList = new LinkedList<>();

        boolean isOk = pictureService.saveBatch(pictureList);
        if (isOk) {
            for (Picture p : pictureList) {
                idList.add(p.getId());
            }
        }

        return idList;
    }


    private String createFileName(String projectName, String fileSuffix, String userID) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStamp = simpleDateFormat.format(new Date());

        String[] arr = {projectName, timeStamp, userID,};
        String filename = String.join("-", arr);

        return filename + fileSuffix;
    }

    private void validationBucket() throws ServerException,
            InsufficientDataException,
            ErrorResponseException,
            IOException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            InvalidResponseException,
            XmlParserException,
            InternalException {

        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketname()).build());
        if (!found) {
            minioClient.makeBucket(MakeBucketArgs
                    .builder()
                    .bucket(minioProperties.getBucketname())
                    .build()
            );
        }

    }

    private boolean isValidExtension(String extension) {
        return Arrays.asList(ALLOWED_EXTENSIONS)
                .contains(extension);
    }
}
