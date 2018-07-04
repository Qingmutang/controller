package com.czb.themis.util.view;

/**
 * Enumeration of Request Result status codes
 *
 * @author ljmatlight
 * @date 2018/4/20
 */
public enum ResultStatus {

    /**
     * 响应成功返回状态: 000000
     */
    SUCCESS(000000, "请求成功"),

    /**
     * 响应失败返回状态: 000001
     */
    ERROR(000001, "系统繁忙");

    /**
     * 状态码
     */
    private final int value;

    /**
     * 状态描述
     */
    private final String reasonPhrase;

    ResultStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public static ResultStatus valueOf(int statusCode) {
        for (ResultStatus value : values()) {
            if (value.value == statusCode) {
                return value;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
    }

    public int getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}
