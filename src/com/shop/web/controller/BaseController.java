package com.shop.web.controller;

import com.alibaba.dubbo.rpc.RpcException;
import com.shop.web.utils.ObjectMapperUtils;
import com.shop.web.vo.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.SocketTimeoutException;
import java.util.LinkedHashMap;

import static com.shop.web.utils.HttpResponseUtil.response;

/**
 * Created by Cater.Tian on 2016/4/1.
 */
public class BaseController implements ApplicationContextAware {

    protected static ApplicationContext context;
    protected Logger logger = LoggerFactory.getLogger(getClass().getName());
   //txl private EhcacheService ehcacheService = DubboServiceFactory.get(EhcacheService.class);

    public final static String Bill_PATH = "/resource/";
    public final static String COMPANY_PATH = "/company/";
    public final static String COMMON = "/common/";
    public final static String APICtrlRootPath = "/api/";
    public final static String PUBLIC = "/public/";
    public final static String INTERPUBLIC = "/outer/";
    private static final String SUCCESSFUL_MESSAGE_CODE = "0";


    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
    }

    protected HttpServletResponse getResponse() {
        return ((ServletWebRequest) RequestContextHolder
                .getRequestAttributes()).getResponse();
    }

    protected HttpSession getSession() {
        return getRequest().getSession(true);
    }

    protected int getWorkerNum() {
        LinkedHashMap token = null;//txl(LinkedHashMap) ehcacheService.getUserTokenVal(getSession().getId());
        if (token != null && token.containsKey("id")) {
            return (int) token.get("id");
        } else {
            return -1;
        }
    }

    protected Token getToken(){
        LinkedHashMap linkedHashMap =null;//txl (LinkedHashMap) ehcacheService.getUserTokenVal(getSession().getId());
        Token token;
        if(linkedHashMap != null){
            String json = ObjectMapperUtils.beanToJson(linkedHashMap);
            token = ObjectMapperUtils.jsonToBean(json, Token.class);
            return token;
        }
        return null;
    }

    @ExceptionHandler
    public void exception(HttpServletResponse httpResponse, Exception ex) {
        logger.error(ex.toString());
        ex.printStackTrace();

        if (ex instanceof NullPointerException) {
            response(httpResponse, "{\"code\":\"-1\",\"desc\":\"" + "系统异常！" + "\"}");
        } else if (ex instanceof RpcException) {
            response(httpResponse, "{\"code\":\"-1\",\"desc\":\"" + "请求超时,请重试！" + "\"}");
        } else if (ex instanceof SocketTimeoutException) {
            response(httpResponse, "{\"code\":\"-1\",\"desc\":\"" + "请求超时,请重试！" + "\"}");
        } else {
            response(httpResponse, "{\"code\":\"-1\",\"desc\":\"" + "系统异常！" + "\"}");
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (context == null) {
            context = applicationContext;
        }
    }



}
