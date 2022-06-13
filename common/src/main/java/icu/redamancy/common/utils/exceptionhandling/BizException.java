package icu.redamancy.common.utils.exceptionhandling;

import icu.redamancy.common.utils.result.ResultCode;

/**
 * @Author redamancy
 * @Date 2022/5/15 16:10
 * @Version 1.0
 */
public class BizException extends BaseException {
    
    public BizException(ResultCode resultCode) {
        super(resultCode);
    }
}
