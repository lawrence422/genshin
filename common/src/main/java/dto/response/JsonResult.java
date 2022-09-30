package dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class JsonResult <T>{
    private int code;
    private String msg;
    private T data;

    public static <T> JsonResult<T> success(T data){
        JsonResult<T> jsonResult=new JsonResult<>();
        jsonResult.setCode(HttpStatus.OK.value());
        jsonResult.setMsg(HttpStatus.OK.getReasonPhrase());
        jsonResult.setData(data);
        return jsonResult;
    }

    public static <T> JsonResult<T> fail(int code,String msg){
        JsonResult<T> jsonResult=new JsonResult<>();
        jsonResult.setCode(code);
        jsonResult.setMsg(msg);
        jsonResult.setData(null);
        return jsonResult;
    }

}
