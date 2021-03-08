package cn.fenqing168.springcloud.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResult<T> {

    private Integer code;
    private T data;
    private String msg;

    public static <T> BaseResult<T> success(){
        return success(null);
    }

    public static <T> BaseResult<T> success(T data){
        return success(data, "success");
    }

    public static <T> BaseResult<T> success(T data, String msg){
        return new BaseResult<>(200, data, msg);
    }

    public static <T> BaseResult<T> error(){
        return error(null);
    }

    public static <T> BaseResult<T> error(T data){
        return error(data, "error");
    }

    public static <T> BaseResult<T> error(T data, String msg){
        return new BaseResult<>(500, data, msg);
    }


}
