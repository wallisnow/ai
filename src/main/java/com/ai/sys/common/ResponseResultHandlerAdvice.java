package com.ai.sys.common;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@ControllerAdvice(basePackages = "com.ai")
@Slf4j
public class ResponseResultHandlerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, @NonNull MethodParameter returnType,
                                  @NonNull MediaType selectedContentType, @NonNull Class selectedConverterType,
                                  @NonNull ServerHttpRequest request, @NonNull ServerHttpResponse response) {
        if(MediaType.APPLICATION_JSON.equals(selectedContentType) || MediaType.APPLICATION_JSON_UTF8.equals(selectedContentType)){
            if (returnType.getGenericParameterType().equals(Response.class)){
                // 如果响应返回的对象为统一响应体，则直接返回body
                return body;
            }else{
                // 只有正常返回的结果才会进入这个判断流程，所以返回正常成功的状态码
                // TODO
                //  1.需要对Error做同样的处理
                //  2.需要优化下面的代码，例如返回body的值，而不是写死
                return new ResponseWrapper(
                        20000,
                        "成功",
                        body
                );
            }
        }
        // 非JSON格式body直接返回即可
        return body;
    }
}