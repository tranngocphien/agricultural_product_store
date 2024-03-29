package com.example.agricultural_product_store.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ResponseData<T> {
    public static final Integer OK_CODE = 200;
    private int code;
    private String message;
    private T data;
    private PaginationInfo paginationInfo;
    ResponseData() {
    }
    ResponseData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ResponseData<T> onSuccess(T data) {
        ResponseData<T> response = new ResponseData<>();
        response.data = data;
        response.code = HttpStatus.OK.value();
        response.message = "SUCCESS";
        return response;
    }

    public static <T> ResponseData<T> onSuccess(T data, PaginationInfo paginationInfo) {
        ResponseData<T> response = new ResponseData<>();
        response.data = data;
        response.code = HttpStatus.OK.value();
        response.message = "SUCCESS";
        response.paginationInfo = paginationInfo;
        return response;
    }

    public static <T> ResponseData<T> onFail(int code, String message) {
        return new ResponseData<>(code, message);
    }

    public static <T> ResponseData<T> onFail() {
        return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "FAIL");
    }
}
