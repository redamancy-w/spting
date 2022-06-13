package icu.redamancy.common.utils.result;


import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;


@Accessors(chain = true)
public class ResultResponse {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";


    // 成功返回数据
    public static Result success(Map<String,Object> data) {
        return new Result()
                .setResult(ResultCode.SUCCESS, data);
    }


    // 失败
    public static Result failure(ResultCode resultCode) {
        return new Result()
                .setResult(resultCode);
    }

    // 失败
    public static Result failure(ResultCode resultCode, Map<String,Object> data) {
        return new Result()
                .setResult(resultCode, data);
    }
}
