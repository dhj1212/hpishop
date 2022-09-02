package com.hpi.modules.ydpub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品信息
 * </p>
 *
 * @author dhj
 * @since 2022-09-01
 */
@Getter
@Setter
@TableName("PUB_GOODS")
public class PubGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    @TableId(value = "GOODID", type = IdType.ASSIGN_UUID)
    private String goodid;

    /**
     * 资源ID
     */
    @TableField("RESOURCEID")
    private String resourceid;

    /**
     * 商品类型
     */
    @TableField("GOOD_TYPE")
    private String goodType;

    /**
     * 商品名称
     */
    @TableField("GOODNAME")
    private String goodname;

    /**
     * 简称
     */
    @TableField("SHORTNAME")
    private String shortname;

    /**
     * 序号
     */
    @TableField("SEQ")
    private Integer seq;

    /**
     * 价格
     */
    @TableField("PRICE")
    private double price;

    /**
     * 货币类别
     */
    @TableField("CURRENCY")
    private String currency;

    /**
     * 库存数量
     */
    @TableField("STOCKQUANTITY")
    private double stockquantity;

    /**
     * 计量单位
     */
    @TableField("UNIT")
    private String unit;

    /**
     * 供应商
     */
    @TableField("SUPPLIER")
    private String supplier;

    /**
     * 商品大类
     */
    @TableField("GOODBIGCLASS")
    private String goodbigclass;

    /**
     * 商品中类
     */
    @TableField("GOODMIDCLASS")
    private String goodmidclass;

    /**
     * 商品分类(最终归属哪一类)
     */
    @TableField("GOODCLASS")
    private String goodclass;

    /**
     * 折扣
     */
    @TableField("DISCOUNT")
    private double discount;

    /**
     * 显示位置
     */
    @TableField("SHOWPLACE")
    private String showplace;

    /**
     * 是否热门
     */
    @TableField("ISHOT")
    private Integer ishot;

    /**
     * 商品介绍
     */
    @TableField("INTRODUCE")
    private String introduce;

    /**
     * 商品大图标
     */
    @TableField("GOODS_BIG_LOGO")
    private String goodsBigLogo;

    /**
     * 商品小图标
     */
    @TableField("GOODS_SMALL_LOGO")
    private String goodsSmallLogo;

    /**
     * 是否删除
     */
    @TableField("ISDEL")
    private Integer isdel;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATEDATE")
    private Date createdate;

    /**
     * 尺码
     */
    @TableField("GOODSIZE")
    private String goodsize;

    /**
     * 型号
     */
    @TableField("MODEL")
    private String model;

    /**
     * 邮费
     */
    @TableField("POSTAGE")
    private double postage;

    /**
     * 创建人
     */
    @TableField("CREATEDATEBY")
    private String createdateby;

    /**
     * 商品更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATEDATE")
    private Date updatedate;

    /**
     * 商品更新人
     */
    @TableField("UPDATEBY")
    private String updateby;

    /**
     * 删除时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("DELDATE")
    private Date deldate;

    /**
     * 商品删除人
     */
    @TableField("DELBY")
    private String delby;

    /**
     * 状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 大图片
     */
    @TableField("PIC_BIG")
    private String picBig;

    /**
     * 中图片
     */
    @TableField("PIC_MID")
    private String picMid;

    /**
     * 小图片
     */
    @TableField("PIC_SMALL")
    private String picSmall;

    /**
     * 大图片1
     */
    @TableField("PIC_BIG_URL")
    private String picBigUrl;

    /**
     * 中图片1
     */
    @TableField("PIC_MID_URL")
    private String picMidUrl;

    /**
     * 小图片1
     */
    @TableField("PIC_SMALL_URL")
    private String picSmallUrl;

    /**
     * 商品有效期(为空为长期)
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("USEENDDATE")
    private Date useenddate;

    /**
     * 是否支持保价(0:不支持保价，其他：保价天数)
     */
    @TableField("ISVALUATION")
    private Integer isvaluation;

    /**
     * 是否支持退货(0:不支持；其他：退货有效天数)
     */
    @TableField("ISSALESRETURN")
    private Integer issalesreturn;

    /**
     * 是否全国联保(0:不支持；其他：保修天数)
     */
    @TableField("ISWARRANTY")
    private Integer iswarranty;

    /**
     * 描述
     */
    @TableField("DESCRIBE")
    private String describe;


}
