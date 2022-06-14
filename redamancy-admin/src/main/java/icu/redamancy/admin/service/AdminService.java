package icu.redamancy.admin.service;

import icu.redamancy.admin.utils.Result;
import icu.redamancy.common.model.pojo.cloud.Materials;
import io.swagger.models.auth.In;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AdminService {
    Result updateDeclare(Long id, int state);

    Result addMaterial(String titleId,Materials material, MultipartFile[] file) throws IOException;

    Result getDeclare(Integer current, Integer size, Long updateTime);

    Result getMaterial();

    Result updateMaterial(Long id, int price, int number);

    Result getDeclareStateCount();

    Result getUserStateCount();

    Result getMaterialNumber();

    Result getDeclareTime();

    Result getUser(Integer current, Integer size, Long updateTime);

    Result updateUserById(Long userId, Integer state);

    Result updateOrder(Long id, Integer state);

    Result getUnit();

    Result getOrder();

}
