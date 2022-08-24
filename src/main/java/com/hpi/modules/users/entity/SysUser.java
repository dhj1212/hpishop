package com.hpi.modules.users.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author dhj
 * @since 2021-04-25
 */
@Data
public class SysUser implements Serializable {
    /**
	 * @Fields serialVersionUID : TODO
	 */
	
	
	private static final long serialVersionUID = 2204377921590520951L;

    // 用户ID
    private String userid;

    private String orgid;

    /**
     * 登陆ID
     */
    private String loginid;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * md5密盐
     */
    private String salt;

    /**
     * 序号
     */
    private Integer seq;


    /**
     * 用户类型
     */
    private String usertype;
    
    /**
     * 用户来源
     */
    private String usersource;

    /**
     * 性别(1:男；0：女)
     */
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
     * 状态
     */
    @TableField("STATUS")
    private String status;
    
    /**
     * 是否删除(0:已经删除；1：正常)
     */
    @TableField("DELFLAG")
    private String delflag;

    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdate;

    /**
     * 出生日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 微信用户ID
     */
    private String wxuserid;

    /**
     * 岗位职务
     */
    private String post;

    /**
     * 上级领导
     */
    private String supleaders;

    /**
     * 住址
     */
    private String address;

    /**
     * 电话
     */
    private String tel;



    /**
     * 登陆次数
     */
    private Integer loginsum;

    /**
     * 最后登录IP
     */
    private String ip;

    /**
     * 最近登陆日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date latedate;

    /**
     * 房间号码
     */
    private String roomno;

    /**
     * 置顶（-1：不置顶）
    private Integer topno;

    /**
     * 修改人日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifydate;

    /**
     * 修改人
     */
    private String modifyman;

    
  //用户权限列表,不属于用户表字段，需要排除
    List<String> permissionList;
    
  //用户权限角色列表,不属于用户表字段，需要排除
    List<String> roleList;

    private String orgname;

    private String departmentName;

    private String id;

    
    //角色信息
    private String[] roles;
    


}
