package com.ai.sys.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口返回 module
 */
public class Response extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	private static final String STATUS = "status";
	private static final String MESSAGE = "message";
	private static final String DATA = "data";

	public Response() {
		put(STATUS, 200);
		put(MESSAGE, "ok");
	}

	public static Response error() {
		return error("500", "系统错误，请联系管理员");
	}

	public static Response error(String msg) {
		return error("500", msg);
	}

	public static Response error(String status, String msg) {
		Response r = new Response();
		r.put(STATUS, status);
		r.put(MESSAGE, msg);
		return r;
	}

	public static Response error(ApiResultEnum resultEnum) {
		Response r = new Response();
		r.put(STATUS, resultEnum.getStatus());
		r.put(MESSAGE, resultEnum.getMessage());
		return r;
	}

	public static Response ok(Map<String, Object> map) {
		Response r = new Response();
		r.putAll(map);
		return r;
	}
	public static Response ok(Object data) {
		Response responce = new Response();
		responce.put(DATA,data);
		return responce;
	}

	public static Response ok() {
		return new Response();
	}

	@Override
	public Response put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}