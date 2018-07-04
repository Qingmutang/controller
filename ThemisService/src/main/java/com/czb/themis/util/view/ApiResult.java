package com.czb.themis.util.view;

import java.io.Serializable;

/**
 * Custom Define Json Result View
 *
 * @author ljmatlight
 * @date 2018/4/20
 */
public class ApiResult implements Serializable {

    /**
     * 默认失败响应
     */
    public final static ApiResult ERROR = new ApiResult(ResultStatus.ERROR.getValue(), ResultStatus.ERROR
            .getReasonPhrase(), null);

    /**
     * 默认成功响应
     */
    public final static ApiResult SUCCESS = new ApiResult(ResultStatus.SUCCESS.getValue(), ResultStatus.SUCCESS
            .getReasonPhrase(), null);

    /**
     * 状态码
     */
    private int status;

    /**
     * 状态描述
     */
    private String message;

    /**
     * 响应数据内容
     */
    private Object data;

    private ApiResult(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ApiResult error(String message) {
        return new ApiResult(ResultStatus.ERROR.getValue(), message, null);
    }

    public static ApiResult success(Object data) {
        return new ApiResult(ResultStatus.SUCCESS.getValue(), ResultStatus.SUCCESS.getReasonPhrase(), data);
    }

    public static ApiResult success(String message, Object data) {
        return new ApiResult(ResultStatus.SUCCESS.getValue(), message, data);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResult{" + "status=" + status + ", message='" + message + '\'' + ", data=" + data + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiResult)) {
            return false;
        }
        ApiResult apiResult = (ApiResult) o;
        if (status != apiResult.status) {
            return false;
        }
        if (!message.equals(apiResult.message)) {
            return false;
        }
        return data.equals(apiResult.data);
    }

    @Override
    public int hashCode() {
        int result = status;
        result = 31 * result + message.hashCode();
        result = 31 * result + data.hashCode();
        return result;
    }

    /**
     * 响应结果构造器
     */
    public static class ApiResultBuilder {

        /**
         * 状态码
         */
        private int status;

        /**
         * 状态描述
         */
        private String message;

        /**
         * 响应数据内容
         */
        private Object data;

        public ApiResultBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ApiResultBuilder message(String message) {
            this.message = message;
            return this;
        }

        @Override
        public String toString() {
            return "ApiResultBuilder{" + "status=" + status + ", message='" + message + '\'' + ", data=" + data + '}';
        }

        public ApiResultBuilder data(Object data) {
            this.data = data;
            return this;
        }

        public ApiResult build() {
            return new ApiResult(this.status, this.message, this.data);
        }

    }

}
