package com.shop.web.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Token implements Serializable {

    public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String account; //用户账号    
    public Integer id; //用户ID
    public String name;
    public String password;
    public String tokenId;
    public Timestamp time;
    public String toString(){
		return "{\"password\":\""+password+"\",\"tokenId\":\""+tokenId+"\",\"name\":\""+name+"\",\"id\":"+id+",\"time\":"+time+",\"account\":\""+account+"\"}"
    	;
	
    }
}
