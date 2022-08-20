package com.hpi.modules.ydpub.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hpi.common.vo.api.Result;
import com.hpi.modules.ydpub.entity.YdSwiperpic;
import com.hpi.modules.ydpub.service.IYdSwiperpicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 移动端轮播图展示 前端控制器
 * </p>
 *
 * @author dhj
 * @since 2022-08-20
 */
@RestController
@RequestMapping("/ydpub/swiper")
public class YdSwiperpicController {

    @Autowired
    private IYdSwiperpicService iYdSwiperpicService;

    /**
     * @description: 获取轮播图信息
     * @param companyid
     * @return com.hpi.common.vo.api.Result<java.util.List<com.hpi.modules.ydpub.entity.YdSwiperpic>>
     * @throws
     * @author dhj
     * @date 2022/8/20 23:31
    */
    @GetMapping(value = "/{companyid}")
    public Result<List<YdSwiperpic>> getSwiper (@PathVariable(name="companyid") String companyid){
        Result<List<YdSwiperpic>> result = new Result<List<YdSwiperpic>>();

        LambdaQueryWrapper<YdSwiperpic> query = new LambdaQueryWrapper<>();
        query.eq(YdSwiperpic::getCompanyid,companyid);
        query.orderByAsc(YdSwiperpic::getSeq);
        //query.lambda().orderByDesc(SzgqGonghuiYusuan::getId).orderByDesc(SzgqGonghuiYusuan::getCreateTime);
        List<YdSwiperpic> list=this.iYdSwiperpicService.list(query);
        result.setResult(list);
        result.setSuccess(true);
        return result;
    }
}
