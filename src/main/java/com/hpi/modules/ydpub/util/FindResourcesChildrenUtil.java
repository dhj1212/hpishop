/*
 * Copyright 2022 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.hpi.modules.ydpub.util;
/**
 * @title: FindResourcesChildrenUtil
 * @projectName hpishop
 * @description: TODO
 * @author du
 * @date 2022/8/248:57
 */

import com.hpi.common.util.CommUtils;
import com.hpi.modules.ydpub.entity.YdResources;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description :
 * @date :2022/8/24 8:57
 * @author : dhj
 * @modyified By:
 */
public class FindResourcesChildrenUtil {
    
    /**
     * @description: 获取树
     * @param listResources
     * @return java.util.List<com.hpi.modules.ydpub.entity.YdResources>
     * @throws
     * @author dhj
     * @date 2022/8/24 10:30 
    */
    public static List<YdResources> getResourcesTree(List<YdResources> listResources) {
       //遍历父节点
        List<YdResources> collect = listResources.stream().filter(r ->CommUtils.isEmpty(r.getPid())).map(
                (m) -> {
                    m.setChildren(getChildrenList(m, listResources));
                    return m;
                }
        ).collect(Collectors.toList());
        return collect;
    }
    //
    /**
     * @description: 获取子节点
     * @param tree
     * @param list
     * @return java.util.List<com.hpi.modules.ydpub.entity.YdResources>
     * @throws
     * @author dhj
     * @date 2022/8/24 10:32
    */
    public static List<YdResources> getChildrenList(YdResources tree, List<YdResources> list){
        List<YdResources> children = list.stream().filter(item -> Objects.equals(item.getPid(), tree.getResourceid())).map(
                (item) -> {
                    item.setChildren(getChildrenList(item, list));
                    return item;
                }
        ).collect(Collectors.toList());
        return children;
    }
}
