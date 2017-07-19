package com.xiangzhu.plat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liluoqi on 2017/7/13.
 */
@Controller
public class IndexController {

    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        return "index";

    }
}
