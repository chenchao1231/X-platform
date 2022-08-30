package cn.exrick.xboot.support.retn;

/**
 * @author chenchao
 * @Description:
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class ResultTool {
	public static JsonResult success() {
        return new JsonResult(true);
    }

    public static JsonResult success(ResultCode resultEnum) {
        return new JsonResult(true,resultEnum);
    }

	public static <T> JsonResult<T> success(T data) {
        return new JsonResult(true, data);
    }

    public static <T> JsonResult<T> success(ResultCode resultEnum,T data){
        return new JsonResult<>(true,resultEnum,data);
    }

    public static JsonResult fail() {
        return new JsonResult(false);
    }

    public static JsonResult fail(ResultCode resultEnum) {
        return new JsonResult(false, resultEnum);
    }
}
