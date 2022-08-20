package com.hpi.modules.ydpub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hpi.modules.ydpub.entity.YdSwiperpic;
import com.hpi.modules.ydpub.mapper.YdSwiperpicMapper;
import com.hpi.modules.ydpub.service.IYdSwiperpicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 移动端轮播图展示 服务实现类
 * </p>
 *
 * @author dhj
 * @since 2022-08-20
 */
@Service
public class YdSwiperpicServiceImpl extends ServiceImpl<YdSwiperpicMapper, YdSwiperpic> implements IYdSwiperpicService {

    @Autowired(required = false)
    private YdSwiperpicMapper ydSwiperpicMapper;

    @Override
    public List<YdSwiperpic> getSwiper(String companyid) {
        LambdaQueryWrapper<YdSwiperpic> query = new LambdaQueryWrapper<>();
        //query.lambda().eq(YdSwiperpic::getCompanyid,companyid);
        query.orderByAsc(YdSwiperpic::getSeq);
        //query.lambda().orderByDesc(SzgqGonghuiYusuan::getId).orderByDesc(SzgqGonghuiYusuan::getCreateTime);
        List<YdSwiperpic> list=this.ydSwiperpicMapper.selectList(query);

        return list;
    }
}
