package icu.redamancy.common.utils.handlerequest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;

/**
 * @Author redamancy
 * @Date 2022/5/5 16:40
 * @Version 1.0
 */
public class HandlerParameter {
    private static class GetParameterInnerClass{
        private static final HandlerParameter INSTANCE = new HandlerParameter();
    }

    public static HandlerParameter getHandlerParameter(){
        return GetParameterInnerClass.INSTANCE;
    }

//    获得当前request中的body中的参数
    public JsonObject getJSONParamBody(){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        JsonObject jsonParam = null;
        try {
            // 获取输入流
//            如果想接收任意长度的数据，而且避免乱码的产生，就可以使用BufferedReader。
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

            // 写入数据到Stringbuilder
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            jsonParam = JsonParser.parseString(sb.toString()).getAsJsonObject();
            // 直接将json信息打印出来
        } catch (Exception e) {
            System.out.println("json数据为空");
        }
        return jsonParam;
    }

    public Map<String,String[]> getParamUrl() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        return request.getParameterMap();
    }

    public HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

}
