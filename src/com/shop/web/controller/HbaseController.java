package com.shop.web.controller;

import com.shop.entity.Torder;
import com.shop.message.PubResultSet;
import com.shop.message.StatusCode;
import com.shop.model.ShopOrderInfo;
import com.shop.service.IHbaseService;
import com.shop.service.IStatisticsSerivce;
import com.shop.web.entity.ShopOrderModel;
import com.shop.web.message.DataListRespMsg;
import com.shop.web.message.OrderInfoReqMsg;
import com.shop.web.service.DubboServiceFactory;
import com.shop.web.utils.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = BaseController.Bill_PATH + "hbase")
public class HbaseController extends BaseController{
	private IHbaseService hbaseService= DubboServiceFactory.get(IHbaseService.class);
	 private IStatisticsSerivce statisticsSerivce = DubboServiceFactory.get(IStatisticsSerivce.class);
	
	@ResponseBody
    @RequestMapping(value ="/queryOrderInfos",method = RequestMethod.POST)
	public DataListRespMsg<ShopOrderModel> queryLogInfos(@RequestBody OrderInfoReqMsg req){
		logger.info("HbaseController queryOrderInfos req : " + req.toString());
		DataListRespMsg<ShopOrderModel> resp = new DataListRespMsg<>();
		resp.setCode(StatusCode.REQUEST_ERROR.code());
        resp.setDesc(StatusCode.REQUEST_ERROR.desc());
        if (req.isMsgBodyBlank()) {
            resp.setCode(StatusCode.PARAM_ERROR.code());
            resp.setDesc(StatusCode.PARAM_ERROR.desc());
            return resp;
        }
        
        PubResultSet<ShopOrderInfo> obdResultSet = null;
        List<ShopOrderModel> shopOrderModels = new ArrayList<>();
        try {
            obdResultSet = hbaseService.queryOrderInfos(req.getBeginDate(), req.getEndDate());
            //时间转换
            List<ShopOrderInfo> shopOrderInfos = obdResultSet.getData();
            for (ShopOrderInfo shopOrderInfo : shopOrderInfos) {
                ShopOrderModel shopOrderModel = new ShopOrderModel();
                BeanUtils.copyProperties(shopOrderInfo,shopOrderModel);
                shopOrderModel.setOrderTime(DateUtil.formatDateTime(shopOrderInfo.getOrderTime()));
                shopOrderModels.add(shopOrderModel);
            }
            shopOrderModels.sort((o1, o2) -> {
                String s1 = o1.getOrderTime();
                String s2 = o2.getOrderTime();
                return s1.compareTo(s2);
            });
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            resp.setCode(StatusCode.EXCEPTION.code());
            resp.setDesc(StatusCode.EXCEPTION.desc());
        }

        if (null != obdResultSet) {
            resp.setCode(obdResultSet.getCode());
            resp.setDesc(obdResultSet.getDesc());
            resp.setData(shopOrderModels);
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

