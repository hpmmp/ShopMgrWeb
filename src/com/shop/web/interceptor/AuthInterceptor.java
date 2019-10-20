package com.shop.web.interceptor;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Cater.Tian on 2016/4/1.
 */
public class AuthInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    private String[] allowUrls;
    private String[] validityUrls;
   // private EhcacheService ehcacheService = DubboServiceFactory.get(EhcacheService.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        String requestURL = httpServletRequest.getRequestURL().toString();
        httpServletRequest.getQueryString();
        if (allowUrls != null && allowUrls.length > 0) {
            for (String allowUrl : allowUrls) {
                if (requestURL.contains(allowUrl) || requestURL.contains(".html")) {
                    return true;
                }
            }
        }
        if (validityUrls != null && validityUrls.length > 0) {
            for (String url : validityUrls) {
                if (requestURL.contains(url) && !userLoginStatus(httpServletRequest, httpServletResponse)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }

    public void setValidityUrls(String[] validityUrls) {
        this.validityUrls = validityUrls;
    }

    private boolean userLoginStatus(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {
        logger.info("preHandle requestURL:{}", httpServletRequest.getRequestURI());
      /*  LinkedHashMap token = (LinkedHashMap) ehcacheService.getUserTokenVal(httpServletRequest.getSession().getId()); //从缓存中取出用户信息
        if (token == null) {
            ObjectNode nodes = JsonNodeFactory.instance.objectNode();
            nodes.put("code", -7);
            nodes.put("desc", "尊敬的用户，您没有登陆，请先登陆");
            response(httpServletResponse, nodes.toString());
            return false;
        } else if (token.containsKey("sysUrls")) {
            List<String> sysUrls = (ArrayList) token.get("sysUrls");
            for (String url : sysUrls) {
                if (httpServletRequest.getRequestURI().contains(url)) {
                    return true;
                }
            }
        }
        ObjectNode nodes = JsonNodeFactory.instance.objectNode();
        nodes.put("code", -6);
        nodes.put("desc", "尊敬的用户，您没有权限访问该功能模块");
        response(httpServletResponse, nodes.toString());
        return false;
        */
        return true;
    }
}
