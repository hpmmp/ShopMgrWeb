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
import com.shop.entity.Tstatistics;
import com.shop.message.PubResult;
import com.shop.message.PubResultSet;
import com.shop.message.StatusCode;
import com.shop.model.ShopOrderInfo;
import com.shop.model.StateInfo;
import com.shop.service.IShopOrder;
import com.shop.service.IStatisticsSerivce;
import com.shop.service.kafka.KafkaProducerServer;
import com.shop.web.message.AddOrderInfoReqMsg;
import com.shop.web.message.DataListRespMsg;
import com.shop.web.message.DataObjectRespMsg;
import com.shop.web.message.OrderInfoReqMsg;
import com.shop.web.service.DubboServiceFactory;
 

@Controller
@RequestMapping(value = BaseController.Bill_PATH + "statistics")
public class StatisticsController extends BaseController {
		
		  private IStatisticsSerivce statisticsSerivce = DubboServiceFactory.get(IStatisticsSerivce.class);
		  
			@ResponseBody
			  @RequestMapping(value = "/queryStatisticsInfos", method = RequestMethod.POST)
			  public DataListRespMsg<Tstatistics> queryStatisticsInfos() {
				  DataListRespMsg<Tstatistics> resp = new DataListRespMsg<Tstatistics>();
					resp.setCode(StatusCode.REQUEST_ERROR.code());
					resp.setDesc(StatusCode.REQUEST_ERROR.desc());

					PubResultSet<Tstatistics> obdResultSet = null;
					try {
						obdResultSet = statisticsSerivce.queryStatisticsInfos();
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
}
