package com.ai.sys.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口返回 module
 */
public class Response extends ResponseEntity<HashMap<String, Object>> {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE = "message";
    private static final String DATA = "data";

    public Response(HttpStatus httpStatus) {
        super(httpStatus);
    }

    public Response(HashMap<String, Object> responseMap, HttpStatus httpStatus) {
        super(responseMap, httpStatus);
    }

    public static Response httpWith(HttpStatus httpStatus){
        return new Response(httpStatus);
    }

    public static Response httpError() {
        return new Response(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static Response httpError(String msg) {
        return httpError(HttpStatus.INTERNAL_SERVER_ERROR, msg);
    }

    public static Response httpError(HttpStatus status, String msg) {
        Map<String, Object> message = Map.of(MESSAGE, msg);
        return new Response(put(message), status);
    }

    public static Response httpError(HttpStatus status, Object data) {
        return new Response(put(Map.of(DATA, data)), status);
    }

    public static Response httpError(HttpStatus status, String msg, Object data) {
        Map<String, Object> message = Map.of(MESSAGE, msg, DATA, data);
        return new Response(put(message), status);
    }

    public static Response httpOk(Map<String, Object> map) {
        return new Response(put(map), HttpStatus.OK);
    }

    public static Response httpOk(Object data) {
        return new Response(put(Map.of(DATA, data)), HttpStatus.OK);
    }

    public static Response httpOk(String msg) {
        return new Response(put(Map.of(MESSAGE, msg)), HttpStatus.OK);
    }

    public static Response httpOk() {
        return new Response(HttpStatus.OK);
    }

    public static HashMap<String, Object> put(Map<String, Object> map) {
        return new HashMap<>(map);
    }
}