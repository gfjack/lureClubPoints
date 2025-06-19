package com.lureclub.points.entity.common;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 统一API响应类
 *
 * @author system
 * @date 2025-06-19
 */
@Schema(description = "统一响应格式")
public class ApiResponse<T> {

    @Schema(description = "响应码")
    private Integer code;

    @Schema(description = "响应消息")
    private String message;

    @Schema(description = "响应数据")
    private T data;

    @Schema(description = "时间戳")
    private Long timestamp;

    // 构造函数
    public ApiResponse() {
        this.timestamp = System.currentTimeMillis();
    }

    public ApiResponse(Integer code, String message, T data) {
        this();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功响应
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "成功", data);
    }

    /**
     * 成功响应带消息
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(200, message, data);
    }

    /**
     * 成功响应无数据
     */
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>(200, message, null);
    }

    /**
     * 错误响应
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(500, message, null);
    }

    /**
     * 错误响应带状态码
     */
    public static <T> ApiResponse<T> error(Integer code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    // Getter和Setter方法
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}