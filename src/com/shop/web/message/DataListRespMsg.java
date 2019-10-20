package com.shop.web.message;

import java.util.List;
 

public class DataListRespMsg<T> {

	private Integer code; //返回结果标识，0x00成功，其它失败
	private String desc;

	private List<T> data; //返回内容

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
}
