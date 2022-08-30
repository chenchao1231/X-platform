package cn.exrick.xboot.support.retn;

/**
 * 返回码定义
 * 规定:
 * #1表示成功
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 * @author chenchao
 */
public enum ResultCode {
    /* 成功 */
    SUCCESS(200, "请求成功"),
    SUCCESS_login(200, "用户登录成功"),
    SUCCESS_logout(200, "用户退出成功"),
    SUCCESS_UPDATE_PASSWORD(200,"修改密码成功"),

    /* 默认失败 */
    COMMON_FAIL(999, "失败"),

    SF_STATION_NOT_EXIST(4041,"室分车站不存在"),
    SF_HOST_NOT_EXIST(4042,"无监控主机信息"),
    SF_BELONG_UNIT_DELETE_FILE(4003,"该站点下存在监控主机，请移除后再删除"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    
    TOKEN_NOT_FOUND(1100,"未找到TOKEN"),
    
    SESSION_INVALID(1200,"会话失效"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "账号或密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号已在其他地方登陆"),

    /*角色错误
     */
    ROLE_CODE_ALREADY_EXIST(5001,"roleCode已存在"),

    /* 业务错误 */
    NO_PERMISSION(3001, "当前账号没有权限");



    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据code获取message
     *
     * @param code
     * @return
     */
    public static String getMessageByCode(Integer code) {
        for (ResultCode ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getMessage();
            }
        }
        return null;
    }
}