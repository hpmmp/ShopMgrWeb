package com.shop.web.controller;

import com.shop.entity.Tstatistics;
import com.shop.message.PubResultSet;
import com.shop.message.StatusCode;
import com.shop.service.IStatisticsSerivce;
import com.shop.web.entity.TstatisticsModel;
import com.shop.web.message.DataListRespMsg;
import com.shop.web.service.DubboServiceFactory;
import com.shop.web.utils.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
 

@Controller
@RequestMapping(value = BaseController.Bill_PATH + "statistics")
public class StatisticsController extends BaseController {
		
		  private IStatisticsSerivce statisticsSerivce = DubboServiceFactory.get(IStatisticsSerivce.class);
		  
			@ResponseBody
			  @RequestMapping(value = "/queryStatisticsInfos", method = RequestMethod.POST)
			  public DataListRespMsg<TstatisticsModel> queryStatisticsInfos() {
				  DataListRespMsg<TstatisticsModel> resp = new DataListRespMsg<>();
					resp.setCode(StatusCode.REQUEST_ERROR.code());
					resp.setDesc(StatusCode.REQUEST_ERROR.desc());

					PubResultSet<Tstatistics> obdResultSet = null;
					List<TstatisticsModel> tstatisticsModels = new ArrayList<>();
					try {
						obdResultSet = statisticsSerivce.queryStatisticsInfos();
						//时间转换
						List<Tstatistics> tstatisticsList = obdResultSet.getData();
						for (Tstatistics tstatistics : tstatisticsList) {
							TstatisticsModel tstatisticsModel = new TstatisticsModel();
							BeanUtils.copyProperties(tstatistics,tstatisticsModel);
							tstatisticsModel.setUpdateTime(DateUtil.formatDateTime(tstatistics.getUpdateTime()));
							tstatisticsModels.add(tstatisticsModel);
						}
					} catch (Exception ex) {
						logger.error(ex.getMessage(), ex);
						resp.setCode(StatusCode.EXCEPTION.code());
						resp.setDesc(StatusCode.EXCEPTION.desc());
					}

					if (null != obdResultSet) {
						resp.setCode(obdResultSet.getCode());
						resp.setDesc(obdResultSet.getDesc());
						resp.setData(tstatisticsModels);
					}

					return resp;
			}
}
