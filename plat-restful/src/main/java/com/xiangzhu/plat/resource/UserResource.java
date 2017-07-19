package com.xiangzhu.plat.resource;

import com.google.gson.Gson;
import com.xiangzhu.plat.domain.Pagination;
import com.xiangzhu.plat.domain.business.User;
import com.xiangzhu.plat.service.business.UserService;
import com.xiangzhu.plat.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by liluoqi on 2017/7/9.
 * 用户rest接口
 */
@Component
@Path("user")
public class UserResource {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(UserResource.class);
    @Autowired
    private UserService userService;

    @Path("add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String add(@FormParam("username") String username, @FormParam("password") String password) {
        return new Gson().toJson(userService.add(new User(username, password)));
    }

    @Path("queryAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String query(@QueryParam("currentPage") int currentPage, @QueryParam("pageSize") int pageSize) {
        return JsonUtils.toJson(userService.queryAllPagination(new Pagination(currentPage, pageSize)));
    }

    @Path("queryByOpenId/{openId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String queryByOpenId(@PathParam("openId")String openId){
        return JsonUtils.toJson(userService.queryByOpenId(openId));
    }

    @Path("validate")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String validate(@FormParam("username") String userName, @FormParam("password") String password) {
        return JsonUtils.toJson(userService.validate(userName, password));
    }
}
