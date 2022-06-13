package icu.redamancy.common.utils.result;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Result implements Serializable {
    private static final long serialVersionUID = 6308315887056661996L;
    private Integer code;
    private String message;
    private Map<String,Object> data = new HashMap<String, Object>();

    public Result(){
    }

    private Result(Map<String,Object> data){
        this.data = data;
    }

    public static Result one(String val, Object key){

        Map<String,Object> map = new HashMap<>();
        map.put(val,key);
        return new Result(map);
    }

    public  Result lambdaPut(String val,Object key){
        this.data.put(val,key);
        return this;
    }
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public Result success(){
        setResult(ResultCode.SUCCESS);
        return this;
    }


    public Result failure(){
        setResult(ResultCode.BAD_REQUEST);
        return this;
    }

    public Result setResult(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        return this;
    }

    public Result setResult(ResultCode resultCode, Map<String,Object> data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.setData(data);
        return this;
    }
}
