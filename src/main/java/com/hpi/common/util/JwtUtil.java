package com.hpi.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.hpi.common.constant.CommonConstant;
import com.hpi.common.system.exception.AppException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


/**
 * @Author Scott
 * @Date 2018-07-12 14:23
 * @Desc JWT工具类
 **/
public class JwtUtil {

	// Token过期时间30分钟（用户登录过期时间是此时间的两倍，以token在reids缓存时间为准）
	public static final long EXPIRE_TIME = 30 * 60 * 1000;
	
	private static final String SING = "HCY";
	/**
	 * 校验token是否正确
	 *
	 * @param token  密钥
	 * @param secret 用户的密码
	 * @return 是否正确
	 */
	public static boolean verify(String token, String username, String secret) {
		try {
			// 根据密码生成JWT效验器
			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
			// 效验TOKEN
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}

	/**
	 * 获得token中的信息无需secret解密也能获得
	 *
	 * @return token中包含的用户名
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 生成签名,5min后过期
	 *
	 * @param username 用户名
	 * @param secret   用户的密码
	 * @return 加密的token
	 */
	public static String sign(String username, String secret) {
		Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
		Algorithm algorithm = Algorithm.HMAC256(secret);
		// 附带username信息
		return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);
	}
	
	/**
	 * 
	 * @方法说明: 产生token
	 * @参数： @param map
	 * @参数： @return   
	 * @返回值： String  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年4月27日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public static String getToken(Map<String , String> map){
        //设置过期时间 , 过期时间为7天
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE , 7);

        //创建 Builder
        JWTCreator.Builder builder = JWT.create();
        //遍历map,设置token参数
        map.forEach((key,value)->{
            builder.withClaim(key,value);
        });
        //设置过期时间
        String token = builder.withExpiresAt(instance.getTime())
                //设置签名
                .sign(Algorithm.HMAC256(SING));

        return token;
    }
	
	/**
	 * 
	 * @方法说明: 获取token中的某一个数据(String)
	 * @参数： @param token
	 * @参数： @param search
	 * @参数： @return   
	 * @返回值： String  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年4月27日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public static String getString(String token,String search){
        try {
        	if (token==null || token.trim().length()<=0){
        		throw new AppException("无法获取TOKEN，请检查用户是否登录");
        	}
            DecodedJWT decodedJWT = JWT.decode(token);
            System.out.println("ExpiresAt====="+DateUtils.formatTime(decodedJWT.getExpiresAt()));
            long diffmin=DateUtils.getdiffdateMin(decodedJWT.getExpiresAt(),DateUtils.getDate() );
            if (diffmin>60){
            	//throw new AppException("TOKEN失效！");
            }
            System.out.println(""+diffmin+"分！");
            String data = decodedJWT.getClaim(search).asString();
            return data;
        } catch (JWTDecodeException e) {
        	return null;
        }
    }
	/**
	 * @description: 获取token中的某一个数据(String)
	 * @param token
	 * @param search
	 * @param expires_in 有效期，分钟
	 * @return java.lang.String
	 * @throws
	 * @author dhj
	 * @date 2022/7/25 10:47
	*/
	public static String getString(String token,String search,Integer expires_in){
		try {
			if (token==null || token.trim().length()<=0){
				throw new AppException("无法获取TOKEN，请检查用户是否登录");
			}
			DecodedJWT decodedJWT = JWT.decode(token);
			System.out.println("ExpiresAt====="+DateUtils.formatTime(decodedJWT.getExpiresAt()));
			long diffmin=DateUtils.getdiffdateMin(decodedJWT.getExpiresAt(),DateUtils.getDate() );
			if (diffmin>expires_in){
				//throw new AppException("TOKEN失效！");
				System.out.println("Tiken 失效，有效时间是"+expires_in+"分钟");
				return "failure";
			}
			System.out.println(""+diffmin+"分！");
			String data = decodedJWT.getClaim(search).asString();
			return data;
		} catch (JWTDecodeException e) {
			return null;
		}
	}
	
	/**
	 * 
	 * @方法说明: 获取token中的某一个数据(Integer)
	 * @参数： @param token
	 * @参数： @param search
	 * @参数： @return   
	 * @返回值： Integer  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年4月27日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public static Integer getInteger(String token,String search){
        try {
        	if (token==null || token.trim().length()<=0){
        		throw new AppException("无法获取TOKEN，请检查用户是否登录");
        	}
            DecodedJWT decodedJWT = JWT.decode(token);
            Integer data = decodedJWT.getClaim(search).asInt();
            return data;
        } catch (JWTDecodeException e) {
            return null;
        }
    }
	
	/**
	 * 
	 * @方法说明:  获取token中的某一个数据(Double)
	 * @参数： @param token
	 * @参数： @param search
	 * @参数： @return   
	 * @返回值： Double  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年4月27日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public static Double getDouble(String token,String search){
        try {
        	if (token==null || token.trim().length()<=0){
        		throw new AppException("无法获取TOKEN，请检查用户是否登录");
        	}
            DecodedJWT decodedJWT = JWT.decode(token);
            Double data = decodedJWT.getClaim(search).asDouble();
            return data;
        } catch (JWTDecodeException e) {
            return null;
        }
    }
	
	/**
	 * 
	 * @方法说明: 获取token得信息
	 * @参数： @param token
	 * @参数： @return   
	 * @返回值： DecodedJWT  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年4月27日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public static DecodedJWT getTokenInfo(String token){
        //获取 token 得 DecodedJWT
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING))
                .build()
                .verify(token);
        return verify;
    }
	
	/**
	 * 
	 * @方法说明: 获取token得信息
	 * @参数： @param token
	 * @参数： @param sing
	 * @参数： @return   
	 * @返回值： DecodedJWT  
	 * @异常：
	 * @作者： duhj
	 * @创建日期 2021年4月27日
	 *
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 */
	public static DecodedJWT getTokenInfo(String token,String sing){
        //获取 token 得 DecodedJWT
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(sing))
                .build()
                .verify(token);

        return verify;
    }
	
	/**
	 * 根据request中的token获取用户账号
	 * 
	 * @param request
	 * @return
	 * @throws AppException
	 */
	public static String getUserNameByToken(HttpServletRequest request) throws AppException {
		String accessToken = request.getHeader(CommonConstant.AUTHORIZATION_TOKEN);
		String username = getUsername(accessToken);
		if (CommUtils.isEmpty(username)) {
			throw new AppException("未获取到用户");
		}
		return username;
	}
	
	
	/**
	  *  从session中获取变量
	 * @param key
	 * @return
	 */
	public static String getSessionData(String key) {
		//${myVar}%
		//得到${} 后面的值
		String moshi = "";
		if(key.indexOf("}")!=-1){
			 moshi = key.substring(key.indexOf("}")+1);
		}
		String returnValue = null;
		if (key.contains("#{")) {
			key = key.substring(2,key.indexOf("}"));
		}
		if (CommUtils.isNotEmpty(key)) {
			HttpSession session = SpringContextUtils.getHttpServletRequest().getSession();
			returnValue = (String) session.getAttribute(key);
		}
		//结果加上${} 后面的值
		if(returnValue!=null){returnValue = returnValue + moshi;}
		return returnValue;
	}
	
	/**
	  * 从当前用户中获取变量
	 * @param key
	 * @param user
	 * @return
	 */
	//TODO 急待改造 sckjkdsjsfjdk
	/**public static String getUserSystemData(String key,SysUserCacheInfo user) {
		if(user==null) {
			user = JeecgDataAutorUtils.loadUserInfo();
		}
		//#{sys_user_code}%
		
		// 获取登录用户信息
		LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		
		String moshi = "";
		if(key.indexOf("}")!=-1){
			 moshi = key.substring(key.indexOf("}")+1);
		}
		String returnValue = null;
		//针对特殊标示处理#{sysOrgCode}，判断替换
		if (key.contains("#{")) {
			key = key.substring(2,key.indexOf("}"));
		} else {
			key = key;
		}
		//替换为系统登录用户帐号
		if (key.equals(DataBaseConstant.SYS_USER_CODE)|| key.toLowerCase().equals(DataBaseConstant.SYS_USER_CODE_TABLE)) {
			if(user==null) {
				returnValue = sysUser.getUsername();
			}else {
				returnValue = user.getSysUserCode();
			}
		}
		//替换为系统登录用户真实名字
		else if (key.equals(DataBaseConstant.SYS_USER_NAME)|| key.toLowerCase().equals(DataBaseConstant.SYS_USER_NAME_TABLE)) {
			if(user==null) {
				returnValue = sysUser.getRealname();
			}else {
				returnValue = user.getSysUserName();
			}
		}
		
		//替换为系统用户登录所使用的机构编码
		else if (key.equals(DataBaseConstant.SYS_ORG_CODE)|| key.toLowerCase().equals(DataBaseConstant.SYS_ORG_CODE_TABLE)) {
			if(user==null) {
				returnValue = sysUser.getOrgCode();
			}else {
				returnValue = user.getSysOrgCode();
			}
		}
		//替换为系统用户所拥有的所有机构编码
		else if (key.equals(DataBaseConstant.SYS_MULTI_ORG_CODE)|| key.toLowerCase().equals(DataBaseConstant.SYS_MULTI_ORG_CODE_TABLE)) {
			if(user.isOneDepart()) {
				returnValue = user.getSysMultiOrgCode().get(0);
			}else {
				returnValue = Joiner.on(",").join(user.getSysMultiOrgCode());
			}
		}
		//替换为当前系统时间(年月日)
		else if (key.equals(DataBaseConstant.SYS_DATE)|| key.toLowerCase().equals(DataBaseConstant.SYS_DATE_TABLE)) {
			returnValue = user.getSysDate();
		}
		//替换为当前系统时间（年月日时分秒）
		else if (key.equals(DataBaseConstant.SYS_TIME)|| key.toLowerCase().equals(DataBaseConstant.SYS_TIME_TABLE)) {
			returnValue = user.getSysTime();
		}
		//流程状态默认值（默认未发起）
		else if (key.equals(DataBaseConstant.BPM_STATUS)|| key.toLowerCase().equals(DataBaseConstant.BPM_STATUS_TABLE)) {
			returnValue = "1";
		}
		if(returnValue!=null){returnValue = returnValue + moshi;}
		return returnValue;
	}**/
	
	
	public static void main(String[] args) {
		 String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjUzMzY1MTMsInVzZXJuYW1lIjoiYWRtaW4ifQ.xjhud_tWCNYBOg_aRlMgOdlZoWFFKB_givNElHNw3X0";
		 System.out.println(JwtUtil.getUsername(token));
	}
}
