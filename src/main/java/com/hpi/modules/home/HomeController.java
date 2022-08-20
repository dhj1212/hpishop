/*
 * Copyright 2022 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.hpi.modules.home;/**
 * @title: HomeController
 * @projectName hpishop
 * @description: TODO
 * @author du
 * @date 2022/8/2012:34
 */

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @description :
 * @date :2022/8/20 12:34
 * @author : dhj
 * @modyified By:
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping(value = "/{id}")
    public Map findOrg(@PathVariable(name="id") String id){
        Map map = new HashMap();
        map.put("id",id);
        return map;
    }

}
