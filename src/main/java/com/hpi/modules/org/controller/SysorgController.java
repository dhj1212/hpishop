package com.hpi.modules.org.controller;

import com.hpi.modules.org.entity.Sysorg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组织机构 前端控制器
 * </p>
 *
 * @author dhj
 * @since 2022-08-20
 */
@RestController
@RequestMapping("/org/sysorg")
public class SysorgController {

    @Autowired
    com.hpi.modules.org.service.ISysorgService iSysorgService;

    @GetMapping(value = "/{id}")
    public Map findOrg(@PathVariable(name="id") String id){
        Map map = new HashMap();

        List<Sysorg> list=iSysorgService.list();
        map.put("list",list);
        return map;
    }
}
