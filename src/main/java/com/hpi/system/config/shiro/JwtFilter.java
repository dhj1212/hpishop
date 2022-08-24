package com.hpi.system.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.hpi.common.constant.CommonConstant;
import com.hpi.common.util.CommUtils;
import com.hpi.common.vo.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter  
{
	 /**
     * 果带有 token，则对 token 进行检查，否则直接通过
     *  /*
     * 1. 返回true，shiro就直接允许访问url
     * 2. 返回false，shiro才会根据onAccessDenied的方法的返回值决定是否允许访问url
     * 
     * @author HCY
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws UnauthorizedException
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
       
    	try {
			executeLogin(request, response);
		} catch (Exception e) {
			log.info("Token失效或者不存在，请重新登录！");
			try {
				onLoginFail(response,"");
			} catch (IOException e1) {
				log.error(e1.getMessage());
			}
		}
    	return true;
    }
    
	@Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
    	//System.out.println("token============executeLogin=====");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String token = httpServletRequest.getHeader(CommonConstant.AUTHORIZATION_TOKEN);
        String URL=httpServletRequest.getRequestURI();
        System.out.println("获取的URL===:"+URL+";token:"+token);

		if ("/".equals(URL) ){
			System.out.println("login 允许访问");
			return false;
		}
		if (token==null){
			//token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MjA3MDE0NjgsInVzZXJuYW1lIjoiYWRtaW4ifQ.A4TlqSEmtjRI1HWn4gCuFt-Ixl6BzBKUs7b8azge8iE";
		}
        System.out.println("login token=="+token);
		JwtToken jwtToken = new JwtToken(token);
		// 提交给realm进行登入，如果错误他会抛出异常并被捕获
		getSubject(request, response).login(jwtToken);
		// 如果没有抛出异常则代表登入成功，返回true
		return true;
	}
    
    
    /**
     * 返回结果为true表明登录通过
     */
    /**@Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
    	log.warn("onAccessDenied 方法被调用");
    	System.out.println("================onAccessDenied 方法被调用====================");
    	HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader(Constants.AUTHORIZATION_TOKEN);
        String URL=request.getRequestURI();
        System.out.println("URL=="+URL);
        if ("/".equals(URL)){
        	return true;
        }
       // log.info("请求的 Header 中藏有 jwtToken {}", jwt);
        JwtToken jwtToken = new JwtToken(jwt);
        
         //下面就是固定写法
          
        try {
            // 委托 realm 进行登录认证
            //所以这个地方最终还是调用JwtRealm进行的认证
            getSubject(servletRequest, servletResponse).login(jwtToken);
            //也就是subject.login(token)
        } catch (Exception e) {
            e.printStackTrace();
            onLoginFail(servletResponse,"");
            //调用下面的方法向客户端返回错误信息
            return false;
        }

        return true;
    }**/
    
    /**protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
    	 System.out.println("================onAccessDenied====================");
    	Subject subject = getSubject(servletRequest, servletResponse);
         if (subject.getPrincipal() == null) {
             // 如果未登录，保存当前页面，重定向到登录页面
             saveRequestAndRedirectToLogin(servletRequest, servletResponse);
         } else {
        	 //String unauthorizedUrl = getUnauthorizedUrl();
            // if (StringUtils.hasText(unauthorizedUrl)) {
                 //如果匿名访问地址存在，则跳转去匿名访问地址
             //    WebUtils.issueRedirect(request, response, unauthorizedUrl);
            // } else {
                 //不存在则返回404
            //     WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            // }
         }
         return false;
    }**/
    
    private void onLoginFail(ServletResponse response,String message) throws IOException  {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        //httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        if (CommUtils.isEmpty(message)){
        	message="Token失效或者不存在，请重新登录！";
        }
        httpResponse.getWriter().write(JSONObject.toJSONString(Result.error(message)));
		httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
        
    }
    /**
     * 判断用户是否想要登入。
     * 检测 header 里面是否包含 Token 字段
     * @author HCY
     * @since 2020-10-11
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
       // String URL=req.getRequestURI();
        String token = req.getHeader(CommonConstant.AUTHORIZATION_TOKEN);
        return token != null;
    }
    
    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        /**if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
        	httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        	httpServletResponse.setHeader("Access-Control-Allow-Methods", httpServletRequest.getMethod());
        	httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        	httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }**/
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /unauthorized/**
     */
    private void responseError(ServletResponse response, String message) {
        try {
        	HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            //设置编码，否则中文字符在重定向时会变为空字符串
            message = URLEncoder.encode(message, "UTF-8");
            httpServletResponse.sendRedirect("/unauthorized/" + message);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


}
