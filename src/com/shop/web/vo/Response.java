package com.shop.web.vo;

import com.shop.entity.sys.SysUser;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;

/**
 * Created by lihaodi on 2016/4/8.
 */
public class Response {
    @JsonView(SysUser.Record.class)
    public int code = MsgCode.Success.value();

    @JsonView(SysUser.Record.class)
    public String desc = "操作成功";

    @JsonView(SysUser.Record.class)
    public Object result = new ArrayList<>();

    public long total = 0;

    public Response(){}

    public Response(MsgCode msgCode){
        this.code = msgCode.value();
        this.desc = msgCode.desc();
    }
}
