package icu.redamancy.common.utils.handlefile;

import icu.redamancy.common.utils.handlerequest.HandlerParameter;
import net.coobird.thumbnailator.Thumbnails;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author redamancy
 * @Date 2022/5/24 22:53
 * @Version 1.0
 */
public class ImageHandle {
    private static class GetImageHandleClass {
        private static final ImageHandle INSTANCE = new ImageHandle();
    }

    public static ImageHandle getImageHandle() {
        return GetImageHandleClass.INSTANCE;
    }


    public MultipartFile compressImage(MultipartFile file, float scale, float quality, String filename) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Thumbnails.of(file.getInputStream())
                .scale(scale)
                .outputQuality(quality)
                .toOutputStream(outputStream);

        byte[] bytes = outputStream.toByteArray();
        InputStream inputStream = new ByteArrayInputStream(bytes);

        return new MockMultipartFile(filename, filename, file.getContentType(), inputStream);
    }
}