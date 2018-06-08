package com.wsm.sso.model;

import java.io.Serializable;

/**
 * common return
 *
 *  2015-12-4 16:32:31
 * @param <T>
 */
public class ResultT<T> implements Serializable {
	public static final long serialVersionUID = 42L;

	public static final int SUCCESS_CODE = 200;
	public static final int FAIL_CODE = 500;
	public static final ResultT<String> SUCCESS = new ResultT<String>(null);
	public static final ResultT<String> FAIL = new ResultT<String>(FAIL_CODE, null);
	
	private int code;
	private String msg;
	private T data;
	
	public ResultT(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	public ResultT(T data) {
		this.code = SUCCESS_CODE;
		this.data = data;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

}
