package com.ai.sys.common;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
@Data
@AllArgsConstructor
public class ResponseWrapper implements Serializable {
    /**
     * 返回状态码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 数据
     */
    private Object data;

}