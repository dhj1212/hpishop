package com.hpi.modules.ydpub.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hpi.common.util.CommUtils;
import com.hpi.common.vo.api.Result;
import com.hpi.modules.ydpub.entity.PubGoods;
import com.hpi.modules.ydpub.service.IPubGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 商品信息 前端控制器
 * </p>
 *
 * @author dhj
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/ydpub/goods")
public class PubGoodsController {

    @Autowired
    private IPubGoodsService iPubGoodsService;

    /**
     * @description: 查询商品信息
     * @param goods
     * @return com.hpi.common.vo.api.Result<java.util.List<com.hpi.modules.ydpub.entity.PubGoods>>
     * @throws
     * @author dhj
     * @date 2022/9/1 18:59
    */
    @GetMapping(value = "/queryGoods")
    public Result<List<PubGoods>> queryGoods (PubGoods goods) {
        Result<List<PubGoods>> result = new Result();
        //List<Map<String, Object>> listreturn = new ArrayList<>();
        LambdaQueryWrapper<PubGoods> query = new LambdaQueryWrapper<>();
        if (goods !=null ){
            if (CommUtils.isNotEmpty(goods.getGoodname()) ){
                query.like(PubGoods::getGoodname, goods.getGoodname().trim());
            }
            if (CommUtils.isNotEmpty(goods.getShortname()) ){
                query.like(PubGoods::getShortname, goods.getShortname().trim());
            }
            if (CommUtils.isNotEmpty(goods.getGoodType()) ){
                query.like(PubGoods::getGoodType, goods.getGoodType().trim());
            }
            if (CommUtils.isNotEmpty(goods.getGoodclass()) ){
                query.eq(PubGoods::getGoodclass, goods.getGoodclass().trim());
            }
            if (CommUtils.isNotEmpty(goods.getResourceid()) ){
                query.eq(PubGoods::getResourceid, goods.getResourceid().trim());
            }
        }
        query.orderByAsc(PubGoods::getSeq);
        List<PubGoods> list=this.iPubGoodsService.list(query);
        result.setResult(list);
        result.setSuccess(true);
        return result;
    }
}
