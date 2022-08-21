package com.hpi.modules.ydpub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 移动端轮播图展示
 * </p>
 *
 * @author dhj
 * @since 2022-08-20
 */
@Getter
@Setter
@TableName("YD_SWIPERPIC")
public class YdSwiperpic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "SWIPERPIC", type = IdType.ASSIGN_UUID)
    private String swiperpic;

    /**
     * 标题
     */
    @TableField("TITLE")
    private String title;

    /**
     * 所属企业
     */
    @TableField("COMPANYID")
    private String companyid;

    /**
     * 类型
     */
    @TableField("TYPES")
    private String types;

    /**
     * 序号
     */
    @TableField("SEQ")
    private Integer seq;

    /**
     * 状态（1：正常；0：停用）
     */
    @TableField("STATUS")
    private String status;

    /**
     * 路径
     */
    @TableField("PATH")
    private String path;

    @TableField("LINKURL")
    private String linkurl;

    @TableField("LINKITEMID")
    private String linkitemid;

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


}
