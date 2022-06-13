package icu.redamancy.admin.service;

import icu.redamancy.admin.utils.Result;
import icu.redamancy.common.model.pojo.cloud.Materials;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface AdminService {
    Result updateDeclare(Long id, int state);

    Result addMaterial(Materials material, MultipartFile[] file) throws IOException;

    Result getDeclare(Integer current, Integer size, Long updateTime);

    Result getMaterial();

    Result updateMaterial(Long id, int price, int number);

    Result getDeclareStateCount();

    Result getUserStateCount();

    Result getMaterialNumber();

    Result getDeclareTime();

    Result getUser(Integer current, Integer size, Long updateTime);

    Result updateUserById(Long userId, Integer state);

    Result processMaterial(Long id);
}
