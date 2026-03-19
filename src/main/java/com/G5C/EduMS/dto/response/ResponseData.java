package com.G5C.EduMS.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseData<T> {

    private int status;

    private String message;
    private String path;
    private T data;

    // ==================== Success ====================

//    public static <T> ResponseData<T> success(T data) {
//        return ResponseData.<T>builder()
//                .status(200)
//                .message("Success")
//                .data(data)
//                .build();
//    }

    public static <T> ResponseData<T> success(String message, T data, int status) {
        return ResponseData.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    // ==================== Error (không có data) ====================

    public static <T> ResponseData<T> error(int status, String message, String path) {
        return ResponseData.<T>builder()
                .status(status)
                .message(message)
                .path(path)
                .build();
    }

    // ==================== Error (có data — dùng cho validation errors) ====================

    public static <T> ResponseData<T> error(int status, String message, String path, T data) {
        return ResponseData.<T>builder()
                .status(status)
                .message(message)
                .path(path)
                .data(data)
                .build();
    }
}

