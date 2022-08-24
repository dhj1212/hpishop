/*
 * Copyright 2022 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.hpi.modules.system.controller;/**
 * @title: MyExceptionAdvice
 * @projectName hpishop
 * @description: TODO
 * @author du
 * @date 2022/8/2417:10
 */

import com.hpi.common.vo.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @description :
 * @date :2022/8/24 17:10
 * @author : dhj
 * @modyified By:
 */
//@Controller
@Slf4j
@RestController
public class MyExceptionAdvice implements ErrorController {
    @Autowired
    HttpServletRequest request;

    /**
     * @description: 报错实现错误代码的处理
     * @param
     * @return java.lang.String
     * @throws
     * @author dhj
     * @date 2022/7/18 11:12
     */
    @RequestMapping("/error")
    public Result<?> getErrorPath() {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        switch (statusCode) {
            case 404:
                return Result.error(404,"请求不存在");
            case 400:
                //return "/400.html";
                return Result.error(400,"系统发生错误");
            case 403:
                return Result.error(403,"系统发生错误");
            default:
                return Result.error(500,"系统发生错误");
        }
    }



}
