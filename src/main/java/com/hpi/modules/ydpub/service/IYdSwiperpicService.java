package com.hpi.modules.ydpub.service;

import com.hpi.modules.ydpub.entity.YdSwiperpic;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 移动端轮播图展示 服务类
 * </p>
 *
 * @author dhj
 * @since 2022-08-20
 */
public interface IYdSwiperpicService extends IService<YdSwiperpic> {

    public List<YdSwiperpic> getSwiper(String companyid);
}
