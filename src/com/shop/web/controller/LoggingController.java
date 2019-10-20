package com.shop.web.controller;

import com.shop.entity.LoggingInfoPO;
import com.shop.message.PubResultSet;
import com.shop.message.StatusCode;
import com.shop.model.LoggingInfoBO;
import com.shop.service.ILoggingService;
import com.shop.web.message.DataListRespMsg;
import com.shop.web.message.OrderInfoReqMsg;
import com.shop.web.service.DubboServiceFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ChenTengfei
 * @date 2019/8/14 18:06
 **/
@Controller
@RequestMapping(value = BaseController.Bill_PATH + "logging")
public class LoggingController extends BaseController {
	
    private ILoggingService loggingService = DubboServiceFactory.get(ILoggingService.class);
    
    
    @ResponseBody
    @RequestMapping(value ="/queryLogInfos",method = RequestMethod.POST)
    public DataListRespMsg<LoggingInfoBO> queryLogInfos(@RequestBody OrderInfoReqMsg req){
        logger.info("queryLogInfos req : " + req.toString());
        DataListRespMsg<LoggingInfoBO> resp = new DataListRespMsg<>();
        resp.setCode(StatusCode.REQUEST_ERROR.code());
        resp.setDesc(StatusCode.REQUEST_ERROR.desc());
        if (req.isMsgBodyBlank()) {
            resp.setCode(StatusCode.PARAM_ERROR.code());
            resp.setDesc(StatusCode.PARAM_ERROR.desc());
            return resp;
        }

        PubResultSet<LoggingInfoBO> obdResultSet = null;
        try {
            obdResultSet = loggingService.queryLogInfos(req.getBeginDate(), req.getEndDate());
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
    @RequestMapping(value ="/insertLogInfo",method = RequestMethod.POST)
    public void queryLogInfos(@RequestBody LoggingInfoPO po){
        logger.info(" LoggingInfoPO : " + po.toString());
        loggingService.insertLogInfo(po);
    }
    
}