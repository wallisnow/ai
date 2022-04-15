package com.ai.sys.common;

import com.ai.sys.exception.ResourceOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理自定义的业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = ResourceOperationException.class)
    @ResponseBody
    public Response resourceExceptionHandler(ResourceOperationException e) {
        log.debug("发生业务异常！原因是：{}", e.getMessage());
        return Response.httpError(40000, e.getMessage());
    }

    /**
     * 处理其他异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response exceptionHandler(Exception e) {
        log.debug("未知异常！原因是:", e);
        return Response.httpError(HttpStatus.INTERNAL_SERVER_ERROR, "发生异常!", e);
    }
}
