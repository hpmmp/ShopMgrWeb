package com.shop.web.message;
 

public class DataObjectRespMsg<T> {

	private Integer code; //返回结果标识，0x00成功，其它失败
	private String desc;

	private T data; //返回内容

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}
