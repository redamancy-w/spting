package icu.redamancy.common.utils.exceptionhandling;

import icu.redamancy.common.utils.result.ResultCode;
import jdk.jfr.DataAmount;
import lombok.Data;

/**
 * @Author redamancy
 * @Date 2022/5/15 15:50
 * @Version 1.0
 */
@Data
public class BaseException extends RuntimeException {
    private static final int BASE_EXCEPTION_CODE = ResultCode.INTERNAL_SERVER_ERROR.code();
    private static final String BASE_EXCEPTION_MESSAGE = ResultCode.INTERNAL_SERVER_ERROR.message();
    private Integer code;
    private String message;


    public BaseException() {
        super(BASE_EXCEPTION_MESSAGE);
        this.code = BASE_EXCEPTION_CODE;
        this.message = BASE_EXCEPTION_MESSAGE;
    }

    public BaseException(String message) {
        super(message);
        this.code = BASE_EXCEPTION_CODE;
        this.message = message;
    }

    public BaseException(ResultCode resultCode) {
        super(resultCode.message());
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    public BaseException(Throwable cause) {
        super(cause);
        this.code = BASE_EXCEPTION_CODE;
        this.message = BASE_EXCEPTION_MESSAGE;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.code = BASE_EXCEPTION_CODE;
        this.message = message;
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

}
