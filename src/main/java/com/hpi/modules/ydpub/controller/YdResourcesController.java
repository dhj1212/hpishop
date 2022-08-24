package com.hpi.modules.ydpub.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hpi.common.util.CommUtils;
import com.hpi.common.vo.api.Result;
import com.hpi.modules.ydpub.entity.YdResources;
import com.hpi.modules.ydpub.util.FindResourcesChildrenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 资源库 前端控制器
 * </p>
 *
 * @author dhj
 * @since 2022-08-22
 */
@Slf4j
@RestController
@RequestMapping("/ydpub/resources")
public class YdResourcesController {

    @Autowired
    private com.hpi.modules.ydpub.service.IYdResourcesService iYdResourcesService;

    /**
     * @description: 获取资源信息
     * @param companyid
     * @param code
     * @return com.hpi.common.vo.api.Result<java.util.List<com.hpi.modules.ydpub.entity.YdResources>>
     * @throws
     * @author dhj
     * @date 2022/8/22 8:48
    */
    @GetMapping(value = "/{companyid}/{code}")
    public Result<List<YdResources>> getResources (@PathVariable(name="companyid") String companyid,@PathVariable(name="code") String code){
        Result<List<YdResources>> result = new Result<List<YdResources>>();

        LambdaQueryWrapper<YdResources> query = new LambdaQueryWrapper<>();
        query.eq(YdResources::getCompanyid,companyid);
        query.eq(YdResources::getCode,code);
        query.orderByAsc(YdResources::getSeq);
        //query.lambda().orderByDesc(SzgqGonghuiYusuan::getId).orderByDesc(SzgqGonghuiYusuan::getCreateTime);
        List<YdResources> list=this.iYdResourcesService.list(query);
        result.setResult(list);
        result.setSuccess(true);
        return result;
    }

    /**
     * @description: 获取资源2层数据
     * @param companyid
     * @param code
     * @return com.hpi.common.vo.api.Result<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     * @throws
     * @author dhj
     * @date 2022/8/22 10:56
    */
    @GetMapping(value = "/level2/{companyid}/{code}")
    public Result<List<Map<String ,Object>>> getResources2Level (@PathVariable(name="companyid") String companyid,@PathVariable(name="code") String code){
        Result<List<Map<String ,Object>>> result = new Result();
        List<Map<String ,Object>> listreturn=new ArrayList<>();
        LambdaQueryWrapper<YdResources> query = new LambdaQueryWrapper<>();
        query.eq(YdResources::getCompanyid,companyid);
        query.eq(YdResources::getCode,code);
        query.orderByAsc(YdResources::getSeq);
        //query.lambda().orderByDesc(SzgqGonghuiYusuan::getId).orderByDesc(SzgqGonghuiYusuan::getCreateTime);
        List<YdResources> list=this.iYdResourcesService.list(query);
        List<YdResources> rootlist=list.stream().filter(s -> CommUtils.isEmpty(s.getPid()) ).collect(Collectors.toList());
        for (YdResources resources : rootlist){
            if (resources!=null){
                Map<String ,Object> map = new HashMap<>();
                map.put("root",resources);
                String resourceid=resources.getResourceid();
                List<YdResources> childlist=list.stream().filter(s -> CommUtils.isNotEmpty(s.getPid()) && s.getPid().equals(resourceid)).collect(Collectors.toList());
                map.put("child_list",childlist);
                listreturn.add(map);
            }
        }
        result.setResult(listreturn);
        result.setSuccess(true);
        return result;
    }
    /**
     * @description: 获取资源树
     * @param companyid
     * @param code
     * @return com.hpi.common.vo.api.Result<java.util.List<java.util.Map<java.lang.String,java.lang.Object>>>
     * @throws
     * @author dhj
     * @date 2022/8/22 17:23 
    */
    @GetMapping(value = "/tree/{companyid}/{code}")
    public Result<List<YdResources>> getResourcesTree (@PathVariable(name="companyid") String companyid,@PathVariable(name="code") String code) {
        Result<List<YdResources>> result = new Result();
        //List<Map<String, Object>> listreturn = new ArrayList<>();
        LambdaQueryWrapper<YdResources> query = new LambdaQueryWrapper<>();
        query.eq(YdResources::getCompanyid, companyid);
        query.eq(YdResources::getCode, code);
        query.orderByAsc(YdResources::getSeq);
        //result.setResult(listreturn);
        List<YdResources> list=this.iYdResourcesService.list(query);
        //FindResourcesChildrenUtil.getResourcesTree(list);
        result.setResult(FindResourcesChildrenUtil.getResourcesTree(list));
        result.setSuccess(true);
        return result;
    }




}
