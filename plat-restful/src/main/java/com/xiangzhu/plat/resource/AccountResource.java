package com.xiangzhu.plat.resource;

import com.xiangzhu.plat.domain.Pagination;
import com.xiangzhu.plat.service.business.AccountService;
import com.xiangzhu.plat.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by liluoqi on 2017/7/15.
 * 账户restful接口
 */
@Component
@Path("account")
public class AccountResource {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(AccountResource.class);
    /**
     * accountService
     */
    @Autowired
    private AccountService accountService;

    /**
     * 根据微信openId获取账户信息
     *
     * @param openId openId
     * @return account json string
     */
    @Path("queryByOpenId/{openId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAccountByOpenId(@PathParam("openId") String openId) {
        logger.info(String.format("查询微信open id:%s的账户信息", openId));
        return JsonUtils.toJson(accountService.getAccountByOpenId(openId));
    }

    /**
     * 分页查询所有账户
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return
     */
    @Path("queryAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String queryAll(@QueryParam("currentPage")int currentPage,@QueryParam("paheSize")int pageSize){
        return JsonUtils.toJson(accountService.queryAll(new Pagination(currentPage,pageSize)));
    }
}
