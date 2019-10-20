package com.shop.web.vo;

public enum MsgCode {

    /**
     * 操作成功
     */
    Success(0, "操作成功"),

    /**
     * 操作失败
     */
    Failure(-1, "操作失败"),

    /**
     * 参数错误
     */
    Param_Error(-2, "参数错误"),

    /**
     * 内部错误
     */
    Error(-99, "内部错误");

    MsgCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }

    public int value() {
        return code;
    }

    public String desc(){
        return desc;
    }

    private int code;
    private String desc;
}
