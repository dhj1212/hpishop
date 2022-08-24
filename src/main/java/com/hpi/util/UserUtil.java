package com.hpi.util;


import com.hpi.common.util.JwtUtil;
import com.hpi.modules.users.entity.SysUser;
import org.apache.shiro.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;



public class UserUtil
{
	/**
	 *
	 * @方法说明: 获取当前用户信息
	 * @参数： @return
	 * @返回值： Sysuser
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2020年6月18日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public static SysUser getUser(){
		Map<String, SysUser> principal=(Map)SecurityUtils.getSubject().getPrincipal();
		if(principal!=null){
			SysUser user =  principal.get("user");
			return user;
		}else{
			return null;
		}
	}
	/**
	 *
	 * @方法说明: 获取用户ID信息
	 * @参数： @return   
	 * @返回值： String
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2020年6月18日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public static String getUserId(){
		Map<String, SysUser> principal=(Map)SecurityUtils.getSubject().getPrincipal();
		if(principal!=null){
			SysUser user =  principal.get("user");
			if (user!=null){
				return user.getUserid();
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	/**
	 *
	 * @方法说明: 获取loginid
	 * @参数： @return   
	 * @返回值： String
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年5月13日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public static String getLoginId(){
		Map<String, SysUser> principal=(Map)SecurityUtils.getSubject().getPrincipal();
		if(principal!=null){
			SysUser user =  principal.get("user");
			if (user!=null){
				return user.getLoginid();
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	/**
	 *
	 * @方法说明: 获取Token用户信息
	 * @参数： @param request
	 * @参数： @return   
	 * @返回值： String
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年5月13日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public static String getTokenLogId(HttpServletRequest request){
		return JwtUtil.getUserNameByToken(request);
	}

	/**
	 *
	 * @方法说明: 获取用户登录ID
	 * @参数： @return   
	 * @返回值： String
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2020年6月18日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public static String getUserLoginId(){
		Map<String, SysUser> principal=(Map)SecurityUtils.getSubject().getPrincipal();
		if(principal!=null){
			SysUser user =  principal.get("user");
			if (user!=null){
				return user.getLoginid();
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	/**
	 *
	 * @方法说明: 获取用户姓名
	 * @参数： @return   
	 * @返回值： String
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2020年6月18日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public static String getUserUserName(){
		Map<String, SysUser> principal=(Map)SecurityUtils.getSubject().getPrincipal();
		if(principal!=null){
			SysUser user =  principal.get("user");
			if (user!=null){
				return user.getUsername();
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
}
