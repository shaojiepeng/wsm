package com.wsm.common.util;

import java.io.Serializable;


/**
 * $.ajax后需要接受的JSON
 * 
 * @author
 * 
 */
public class AjaxJson implements Serializable{
    private static final long serialVersionUID = -1491499610244557029L;

    public AjaxJson(){}
    
    public static int CODE_SUCCESS = 0;
    public static int CODE_FAILURED = -1;
    public static String[] NOOP = new String[]{};

    /**
     * 处理状态：0: 成功, 1: 失败
     */
    private int code;
    /**
     * 消息 
     */
    private String msg;
    /**
     * 返回数据
     */
    private Object data; 
    /**
     * 返回数据的总数
     */
    private int count;

    private AjaxJson(int code, String msg, Object data, int count) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.count = count;
    }

    /**
     * 处理成功，并返回数据
     *
     * @param data 数据对象
     * @return data
     */
    public static final AjaxJson success(Object data) {
        return new AjaxJson(CODE_SUCCESS, "操作成功", data, 0);
    }
    
    /**
     * 处理成功，并返回数据
     *
     * @param data 数据对象
     * @return data
     */
    public static final AjaxJson success(Object data, int count) {
    	return new AjaxJson(CODE_SUCCESS, "操作成功", data, count);
    }

    /**
     * 处理成功
     *
     * @return data
     */
    public static final AjaxJson success() {
        return new AjaxJson(CODE_SUCCESS, "操作成功", NOOP, 0);
    }

    /**
     * 处理成功
     *
     * @param msg 消息
     * @return data
     */
    public static final AjaxJson success(String msg) {
        return new AjaxJson(CODE_SUCCESS, msg, NOOP, 0);
    }

    /**
     * 处理成功
     *
     * @param msg 消息
     * @param data    数据对象
     * @return data
     */
    public static final AjaxJson success(String msg, Object data) {
        return new AjaxJson(CODE_SUCCESS, msg, data, 0);
    }
    
    /**
     * 处理成功
     *
     * @param msg 消息
     * @param data    数据对象
     * @return data
     */
    public static final AjaxJson success(String msg, Object data, int count) {
    	return new AjaxJson(CODE_SUCCESS, msg, data, count);
    }

    /**
     * 处理失败，并返回数据（一般为错误信息）
     *
     * @param code    错误代码
     * @param msg 消息
     * @return data
     */
    public static final AjaxJson failure(int code, String msg) {
        return new AjaxJson(code, msg, NOOP, 0);
    }

    /**
     * 处理失败
     *
     * @param msg 消息
     * @return data
     */
    public static final AjaxJson failure(String msg) {
        return failure(CODE_FAILURED, msg);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
    public String toString() {
        return "JsonResult [code=" + code + ", msg=" + msg + ", data="
                + data + "]";
    }
}
