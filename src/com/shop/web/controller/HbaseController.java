package com.shop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shop.entity.LoggingInfoPO;
import com.shop.entity.Torder;
import com.shop.message.PubResultSet;
import com.shop.message.StatusCode;
import com.shop.model.LoggingInfoBO;
import com.shop.model.ShopOrderInfo;
import com.shop.service.IHbaseService;
import com.shop.service.ILoggingService;
import com.shop.service.IStatisticsSerivce;
import com.shop.web.message.DataListRespMsg;
import com.shop.web.message.OrderInfoReqMsg;
import com.shop.web.service.DubboServiceFactory;

@Controller
@RequestMapping(value = BaseController.Bill_PATH + "hbase")
public class HbaseController extends BaseController{
	private IHbaseService hbaseService= DubboServiceFactory.get(IHbaseService.class);
	 private IStatisticsSerivce statisticsSerivce = DubboServiceFactory.get(IStatisticsSerivce.class);
	
	@ResponseBody
    @RequestMapping(value ="/queryOrderInfos",method = RequestMethod.POST)
	public DataListRespMsg<ShopOrderInfo> queryLogInfos(@RequestBody OrderInfoReqMsg req){
		logger.info("HbaseController queryOrderInfos req : " + req.toString());
		DataListRespMsg<ShopOrderInfo> resp = new DataListRespMsg<>();
		resp.setCode(StatusCode.REQUEST_ERROR.code());
        resp.setDesc(StatusCode.REQUEST_ERROR.desc());
        if (req.isMsgBodyBlank()) {
            resp.setCode(StatusCode.PARAM_ERROR.code());
            resp.setDesc(StatusCode.PARAM_ERROR.desc());
            return resp;
        }
        
        PubResultSet<ShopOrderInfo> obdResultSet = null;
        try {
            obdResultSet = hbaseService.queryOrderInfos(req.getBeginDate(), req.getEndDate());
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
    @RequestMapping(value ="/insertOrderInfo",method = RequestMethod.POST)
    public void queryLogInfos(@RequestBody Torder torder){
        logger.info(" torder : " + torder.toString());
        hbaseService.insertOrderInfo(torder);
    }
}

