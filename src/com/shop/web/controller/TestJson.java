package com.shop.web.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.shop.web.vo.Token;

import net.sf.json.JSONObject;

public class TestJson {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  Token token = new Token();
	        token.id = 111;
	        token.tokenId ="1111"; //用户登录成功，生成tokenId
	        token.account ="test";
	        token.name="aaaa"; 
	          
	        token.password = "123123";
	        System.out.println(token.toString());
	        
	        JSONObject row = new JSONObject();
	      System.out.println(  row.fromObject(token).toString());
	        JSONObject jsonObj= JSONObject.fromObject(token);
	        System.out.println(jsonObj.toString());
	        
	        Map<String, Object> map = new HashMap<>();

			map.put("moneyVolume", "11211");
			map.put("months", "aaaa");
			JSONObject json = new JSONObject().fromObject(map);
			System.out.println( json.toString());
 
	        
	}

}
