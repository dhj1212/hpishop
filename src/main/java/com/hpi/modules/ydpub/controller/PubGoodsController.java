package com.hpi.modules.ydpub.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hpi.common.util.CommUtils;
import com.hpi.common.vo.api.Result;
import com.hpi.modules.ydpub.entity.PubGoods;
import com.hpi.modules.ydpub.service.IPubGoodsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * @description: 分页查询商品信息
     * @param pageNo
     * @param pageSize
     * @param req
     * @return com.hpi.common.vo.api.Result<com.baomidou.mybatisplus.core.metadata.IPage<com.hpi.modules.ydpub.entity.PubGoods>>
     * @throws
     * @author dhj
     * @date 2022/9/2 21:37
    */
    @ApiOperation("分页查询商品信息")
    @GetMapping(value = "/queryGoodsPage")
    public Result<IPage<PubGoods>> findMyDBlist(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest req) {
        Result<IPage<PubGoods>> result = new Result<IPage<PubGoods>>();
        QueryWrapper<PubGoods> query = new QueryWrapper<PubGoods>();
        //String UserId="eb03262c-ab60-4bc6-a4c0-96e66a4229fe";

        String resourceid = req.getParameter("resourceid");
        String goodname = req.getParameter("goodname");
        if (CommUtils.isNotEmpty(resourceid)){
            query.lambda().eq(PubGoods::getResourceid, resourceid.trim());
        }

        if (CommUtils.isNotEmpty(goodname)){
            query.lambda().like(PubGoods::getGoodname, goodname.trim());
        }

        query.lambda().orderByAsc(PubGoods::getSeq);
        Page<PubGoods> page = new Page<PubGoods>(pageNo, pageSize);
        IPage<PubGoods> pageList = iPubGoodsService.page(page, query);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * @description: 获取商品详情信息
     * @param goodid
     * @return com.hpi.common.vo.api.Result<com.hpi.modules.ydpub.entity.PubGoods>
     * @throws
     * @author dhj
     * @date 2022/9/3 22:48
    */
    @ApiOperation("获取商品详情信息")
    @GetMapping(value = "/goodsinfo/{goods_id}")
    public Result<PubGoods> getPubGoods (@PathVariable(name="goods_id") String goodid) {
        Result<PubGoods> result = new Result();
        PubGoods goods=this.iPubGoodsService.getById(goodid);
        result.setResult(goods);
        result.setSuccess(true);
        return result;
    }

}
