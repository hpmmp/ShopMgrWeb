package com.shop.web.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.dao.HiveOperate;
import com.shop.entity.Torder;
import com.shop.message.PubResult;
import com.shop.message.PubResultSet;
import com.shop.message.StatusCode;
import com.shop.model.ShopOrderInfo;
import com.shop.model.StateInfo;
import com.shop.service.IShopOrder;
import com.shop.service.kafka.KafkaProducerServer;
import com.shop.web.message.AddOrderInfoReqMsg;
import com.shop.web.message.DataListRespMsg;
import com.shop.web.message.DataObjectRespMsg;
import com.shop.web.message.OrderInfoReqMsg;
import com.shop.web.service.DubboServiceFactory;
 

@Controller
@RequestMapping(value = BaseController.Bill_PATH + "order")
public class ShopBillController extends BaseController {
		
		  private IShopOrder shopOrderService = DubboServiceFactory.get(IShopOrder.class);
		  
			@ResponseBody
			  @RequestMapping(value = "/queryOrderInfos", method = RequestMethod.POST)
			  public DataListRespMsg<ShopOrderInfo> queryOrderInfos(@RequestBody OrderInfoReqMsg req) {
				  logger.info("queryOrderInfos req : " + req.toString());
				  DataListRespMsg<ShopOrderInfo> resp = new DataListRespMsg<ShopOrderInfo>();
					resp.setCode(StatusCode.REQUEST_ERROR.code());
					resp.setDesc(StatusCode.REQUEST_ERROR.desc());
					if (req.isMsgBodyBlank()) {
						resp.setCode(StatusCode.PARAM_ERROR.code());
						resp.setDesc(StatusCode.PARAM_ERROR.desc());
						return resp;
					}

					PubResultSet<ShopOrderInfo> obdResultSet = null;
					try {
						obdResultSet = shopOrderService.queryOrderInfos(req.getBeginDate(), req.getEndDate());
					} catch (Exception ex) {
						logger.error(ex.getMessage(), ex);
						resp.setCode(StatusCode.EXCEPTION.code());
						resp.setDesc(StatusCode.EXCEPTION.desc());
					}

					if (null != obdResultSet) {
						resp.setCode(obdResultSet.getCode());
						resp.setDesc(obdResultSet.getDesc());
						resp.setData(obdResultSet.getData());
					}

					return resp;
			}

		    
		    @ResponseBody
			@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
			public DataObjectRespMsg<StateInfo> addOrder(
					@RequestBody AddOrderInfoReqMsg req) {
				logger.info("AddOrderInfoReqMsg req : " + req.toString());
				DataObjectRespMsg<StateInfo> resp = new DataObjectRespMsg<StateInfo>();
				resp.setCode(StatusCode.REQUEST_ERROR.code());
				resp.setDesc(StatusCode.REQUEST_ERROR.desc());
				if (req.isMsgBodyBlank() ) {
					resp.setCode(StatusCode.PARAM_ERROR.code());
					resp.setDesc(StatusCode.PARAM_ERROR.desc());
					return resp;
				}

				PubResult<Integer> obdResult = null;

				try {
					Torder order=new Torder();
					order.setMoney(req.getMoney());
					order.setOrderNum(req.getOrderNum());
					order.setPrice(req.getPrice());
					order.setProductId(req.getProductId());
					order.setProductName(req.getProductName());
					obdResult = shopOrderService.saveOrUpdate(order);	
				} catch (Exception ex) {
					logger.error(ex.getMessage(), ex);
					resp.setCode(StatusCode.EXCEPTION.code());
					resp.setDesc(StatusCode.EXCEPTION.desc());
				}

				if (null != obdResult) {
					resp.setCode(obdResult.getCode());

					if (obdResult.isSuccessful()) {
						resp.setDesc("亲！您已成功订购"+req.getProductName());
					} else {
						resp.setDesc(obdResult.getDesc());
					}
					StateInfo state = new StateInfo();
					state.setState(obdResult.getCode()); 
					resp.setData(state);
				}
				return resp;

			}

			 


}
