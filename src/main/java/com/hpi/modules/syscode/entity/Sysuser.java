package com.hpi.modules.syscode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author dhj
 * @since 2022-08-20
 */
@Getter
@Setter
@TableName("SYSUSER")
public class Sysuser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(value = "USERID", type = IdType.ASSIGN_UUID)
    private String userid;

    /**
     * 组织机构ID
     */
    @TableField("ORGID")
    private String orgid;

    /**
     * 登陆ID
     */
    @TableField("LOGINID")
    private String loginid;

    /**
     * 用户名称
     */
    @TableField("USERNAME")
    private String username;

    /**
     * 登陆密码
     */
    @TableField("PASSWORD")
    private String password;

    /**
     * md5密盐
     */
    @TableField("SALT")
    private String salt;

    /**
     * 序号
     */
    @TableField("SEQ")
    private BigDecimal seq;

    /**
     * 用户编号
     */
    @TableField("USERNO")
    private String userno;

    /**
     * 用户类型(1：普通用户；9：管理员)
     */
    @TableField("USERTYPE")
    private String usertype;

    /**
     * 性别(1:男；0：女)
     */
    @TableField("SEX")
    private String sex;

    /**
     * 身份证号
     */
    @TableField("IDNO")
    private String idno;

    /**
     * 手机号码
     */
    @TableField("PHONE")
    private String phone;

    /**
     * E_MAIL
     */
    @TableField("E_MAIL")
    private String eMail;

    /**
     * 状态(1:启用；0：禁用)
     */
    @TableField("STATUS")
    private String status;

    /**
     * 是否删除(1:已经删除；0：正常)
     */
    @TableField("DELFLAG")
    private String delflag;

    /**
     * 创建日期
     */
    @TableField("CREATEDATE")
    private LocalDateTime createdate;

    /**
     * 出生日期
     */
    @TableField("BIRTHDAY")
    private LocalDateTime birthday;

    /**
     * 岗位职务
     */
    @TableField("POST")
    private String post;

    /**
     * 上级领导
     */
    @TableField("SUPLEADERS")
    private String supleaders;

    /**
     * 住址
     */
    @TableField("ADDRESS")
    private String address;

    /**
     * 电话
     */
    @TableField("TEL")
    private String tel;

    /**
     * 提示问题
     */
    @TableField("PWQUESTION")
    private String pwquestion;

    /**
     * 提示问题答案
     */
    @TableField("PWANSWER")
    private String pwanswer;

    /**
     * 传真
     */
    @TableField("FAX")
    private String fax;

    /**
     * 登陆次数
     */
    @TableField("LOGINSUM")
    private BigDecimal loginsum;

    /**
     * 最后登录IP
     */
    @TableField("IP")
    private String ip;

    /**
     * 最近登陆日期
     */
    @TableField("LATEDATE")
    private LocalDateTime latedate;

    /**
     * 房间号码
     */
    @TableField("ROOMNO")
    private String roomno;

    /**
     * 置顶（-1：不置顶）
     */
    @TableField("TOPNO")
    private BigDecimal topno;

    /**
     * 修改人日期
     */
    @TableField("MODIFYDATE")
    private LocalDateTime modifydate;

    /**
     * 修改人
     */
    @TableField("MODIFYMAN")
    private String modifyman;

    /**
     * 微信用户ID
     */
    @TableField("WXUSERID")
    private String wxuserid;

    /**
     * 用户来源（1：系统注册；2：自己注册；3：系统同步；4：企业微信同步）
     */
    @TableField("USERSOURCE")
    private String usersource;


}
