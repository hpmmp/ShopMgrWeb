package com.shop.web.message;

import com.shop.utils.StringHelper;

public class OrderInfoReqMsg  extends InnerRequestMsg implements java.io.Serializable{
	public String  beginDate	;//开始时间	 格式：yyyy-mm-dd
	public String  endDate	;//结束时间	 格式：yyyy-mm-dd
	
 
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
 


	public boolean isMsgBodyBlank(){
		if(  StringHelper.isBlank(this.beginDate)	|| StringHelper.isBlank(this.endDate)				 ){
			return true;		
		}else{
			return false;
		}
	}
	public String toString(){
		return "beginDate=" + beginDate 
				+ "; endDate=" + endDate ;
		
		 
	}

}
