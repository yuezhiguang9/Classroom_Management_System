package demo.campus_management_system.entity.DTO;


public class ResultDTO<T> {
    // 状态码：200成功，401未认证，500服务器错误等
    private int code;
    // 提示消息
    private String msg;
    // 业务数据（成功时返回）
    private T data;

    // 成功响应（带数据）
    public static <T> ResultDTO<T> success(T data) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setCode(200);
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    // 成功响应（带数据和自定义消息）
    public static <T> ResultDTO<T> success(T data, String msg) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setCode(200);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    // 失败响应（带错误信息）
    public static <T> ResultDTO<T> fail(int code, String msg) {
        ResultDTO<T> result = new ResultDTO<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
