package com.shop.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.shop.cache.ICacheService;
import com.shop.model.AccountModel;
import com.shop.service.sys.ISysUser;
import com.shop.web.message.SysUserLoginResp;
import com.shop.web.service.DubboServiceFactory;
import com.shop.web.utils.MD5;
import com.shop.web.vo.MsgCode;
import com.shop.web.vo.Token;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * Created by Cater.Tian on 2016/4/8.
 * <p>系统用户登录</p>
 */
@Controller
@RequestMapping(value = BaseController.APICtrlRootPath + "sysUser")
public class SysUserController extends BaseController {

    private ISysUser sysUserService = DubboServiceFactory.get(ISysUser.class);
    private ICacheService cacheService = DubboServiceFactory.get(ICacheService.class);

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public SysUserLoginResp login(@RequestBody JsonNode nodes, HttpServletRequest httpServletRequest) {
        SysUserLoginResp resp = new SysUserLoginResp();
        String password = nodes.get("password").asText();

        AccountModel sysUserInfo = sysUserService.getByAccount(nodes.get("account").asText(), (short) 1);
        if (sysUserInfo == null) {
            resp.code = MsgCode.Failure.value();
            resp.desc = "登录失败，账号不存在";
        } else if (!sysUserInfo.getPassword().equals(password)) {
            resp.code = MsgCode.Failure.value();
            resp.desc = "登录失败，密码不正确";
        } else {
            resp.code = MsgCode.Success.value();
            resp.desc = "登录成功";
            sysUserLogin(resp, sysUserInfo);

        }
        return resp;
    }

    private void sysUserLogin(SysUserLoginResp resp, AccountModel sysUserInfo) {
        createToken(resp, sysUserInfo);
    }


    private void createToken(SysUserLoginResp resp, AccountModel sysUserInfo) {
        Token token = new Token();
        token.id = sysUserInfo.getId();
        token.tokenId = createTokenId(sysUserInfo.getAccount()); //用户登录成功，生成tokenId
        token.account = sysUserInfo.getAccount();
        token.name = sysUserInfo.getName();

        token.password = sysUserInfo.getPassword();
        token.time = new Timestamp(System.currentTimeMillis());

        JSONObject jsonObj = JSONObject.fromObject(token);
        System.out.println(jsonObj.toString());
        //cacheService.saveUserToken(sysUserInfo.getAccount(), token.toString(), 2 * 60 * 60); //用户信息加入缓存，并设置超时时长（2小时）
        resp.code = MsgCode.Success.value();
        resp.desc = "登录成功";
        resp.name = sysUserInfo.getName();

        resp.tokenId = token.tokenId;
    }

    private String createTokenId(String account) {
        MD5 md = new MD5(account);
        String tokenId = md.compute();
        tokenId = tokenId + new Timestamp(System.currentTimeMillis()).getTime();
        return tokenId;
    }
}
