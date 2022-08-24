package com.hpi.system.filters;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebFilter(urlPatterns = { "/*", "/mesattach/*"})
public class CorsFilter implements Filter
{

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		System.out.println("request.getMethod()=="+request.getMethod());
		/**response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Origin", "*"); **/
        String TOKEN="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTk1Nzc3OTQsInVzZXJuYW1lIjoiZHUxIn0.rO6zR_o8JM67E-h1jph2mpc9WqImr3lp3UvTiSaxlG8";
        //response.setHeader(Constants.AUTHORIZATION_TOKEN, TOKEN);
        /**if("OPTIONS".equals(request.getMethod())) {
        	response.setStatus(204); //HttpStatus.SC_NO_CONTENT = 204
        	response.setHeader("Access-Control-Allow-Credentials", "true");
        	response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, Token");  
        	response.setHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST,DELETE,PUT"); 
		}**/
        
        
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));

        	response.setHeader("Access-Control-Allow-Origin","*");
        //response.setHeader(Constants.AUTHORIZATION_TOKEN, TOKEN);
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())) {
        	response.setStatus(HttpStatus.OK.value());
           // return false;
        }
        
        
        
        //response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        //response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        //response.setHeader("Access-Control-Max-Age", "3600");
        //response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
       // String TOKEN="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTk1Nzc3OTQsInVzZXJuYW1lIjoiZHUxIn0.rO6zR_o8JM67E-h1jph2mpc9WqImr3lp3UvTiSaxlG8";
        //response.setHeader("Access-Control-Expose-Headers", TOKEN);
        //response.setHeader("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTk1Nzc3OTQsInVzZXJuYW1lIjoiZHUxIn0.rO6zR_o8JM67E-h1jph2mpc9WqImr3lp3UvTiSaxlG8");
       
        chain.doFilter(request, response);
	}

}
