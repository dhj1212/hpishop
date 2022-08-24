package com.hpi.modules.ydpub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 资源库
 * </p>
 *
 * @author dhj
 * @since 2022-08-22
 */
@Getter
@Setter
@TableName("YD_RESOURCES")
public class YdResources implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    @TableId(value = "RESOURCEID", type = IdType.ASSIGN_UUID)
    private String resourceid;

    /**
     * 序号
     */
    @TableField("SEQ")
    private Integer seq;

    /**
     * 资源名称
     */
    @TableField("RESOURCENAME")
    private String resourcename;

    /**
     * 标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * 资源标识(代码)
     */
    @TableField("CODE")
    private String code;

    /**
     * 资源类型
     */
    @TableField("SUFFIX")
    private String suffix;

    /**
     * 所属企业
     */
    @TableField("COMPANYID")
    private String companyid;

    /**
     * 上级节点
     */
    @TableField("PID")
    private String pid;

    /**
     * 宽
     */
    @TableField("WIDTH")
    private Integer width;

    /**
     * 高
     */
    @TableField("HEIGHT")
    private Integer height;

    /**
     * 单位
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 地址
     */
    @TableField("URL")
    private String url;

    /**
     * 跳转地址
     */
    @TableField("NAVIGATOR_URL")
    private String navigatorUrl;

    /**
     * 打开方式
     */
    @TableField("OPEN_TYPE")
    private String openType;

    /**
     * 资源数据来源
     */
    @TableField("SOURCE")
    private String source;

    /**
     * 资源对应对象ID
     */
    @TableField("ITEMID")
    private String itemid;

    /**
     * 状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 创建日期
     */
    @TableField("CREATEDATE")
    private Date createdate;

    /**
     * 创建人
     */
    @TableField("CREATEBY")
    private String createby;

    /**
     * 描述
     */
    @TableField("DESCRIBE")
    private String describe;

    @TableField(exist = false)
    private List<YdResources> children = new ArrayList<>();

}
