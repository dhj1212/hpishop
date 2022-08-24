package com.hpi.system.filters;

import com.alibaba.fastjson.JSON;
import com.hpi.common.vo.api.Result;
import com.hpi.system.util.xss.XssHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//暂时不用 XssFilter代替
/*@WebFilter(filterName="myXssFilter",urlPatterns={"/*"},
		initParams = {
				@WebInitParam(name = "excludeUrl", value = "/exclude")
		})
@Order(1)*/
//@Slf4j
public class MyXssFilter implements Filter
{
	FilterConfig filterConfig = null;

	private static final Pattern pattern = Pattern.compile("^[\\d]+$");

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//String exclusions = filterConfig.getInitParameter("excludeUrl");
		//项目启动时初始化,只始化一次
		//log.info("filter 初始化,excludeUrl:"+exclusions);
		this.filterConfig = filterConfig;
	}

	@Override
	public void destroy() {
		this.filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		//logger.info("自定义过滤器->doFilter");
		HttpServletRequest req = (HttpServletRequest) request;
		String requestUri = req.getRequestURI();
		String queryString = req.getQueryString();
		System.out.println("requestUri======================================"+requestUri);
		if(requestUri!=null && queryString!=null) {

			/**if(requestUri.contains("getSingleById")) {
				if(cleanSQL(queryString)) {
					HttpServletResponse res = (HttpServletResponse) response;
					res.sendRedirect("/logon");
				}
			}else {
				//System.out.println("ok2");
			}*/
			//过滤跨站脚本攻击
			if(cleanJS(queryString)) {
				HttpServletResponse res = (HttpServletResponse) response;
				//res.sendRedirect("/login");
				//res.sendRedirect("/logon");
				res.setCharacterEncoding("UTF-8");
				res.setContentType("application/json;charset=UTF-8");
                PrintWriter out = res.getWriter();
                Result<Object> result = new Result<Object>();
                result.error500("您所访问的页面请求中有违反安全规则元素存在，拒绝访问!");
                out.write(JSON.toJSONString(result));
                return;
			}else {
				//System.out.println("ok3");
			}
		}
		chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
	}
	
	private boolean cleanSQL(String value) {
		boolean result = false;
		
		String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|%|chr|mid|master|truncate|"
				+ "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|"
				+ "table|from|grant|use|group_concat|column_name|"
				+ "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|"
				+ "chr|mid|master|truncate|char|declare|or|;|-|--|,|like|%|#";
		
		String[] badStrs = badStr.split("\\|");
		for (int i = 0; i < badStrs.length; i++) {
			if (value.toLowerCase().trim().contains((badStrs[i]))) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	private boolean cleanJS(String value) {
		boolean result = false;
		value = value.toLowerCase();
		if(value.indexOf("script")>-1) {
			return true;
		}
		if(value.indexOf("eval")>-1) {
			return true;
		}
		if(value.indexOf("e­xpression")>-1) {
			return true;
		}
		if(value.indexOf("onload")>-1) {
			return true;
		}
		return result;
	}
	
	private boolean isInteger(String str) {
        return pattern.matcher(str).matches();  
    }
	
	public static void main(String[] args) {
		String reg_html="script"+"[^>]*?>[\\s\\S]*?<\\/"+"script>";
	    Pattern pattern=Pattern.compile(reg_html,Pattern.CASE_INSENSITIVE); 
	    Matcher matcher=pattern.matcher("c=zxgg<javascript>alert(1)</javascript>");
	    while(matcher.find()) {
	    	//System.out.println("find");
	    	break;
	    }
	    reg_html="<javascript"+"[^>]*?>[\\s\\S]*?<\\/"+"javascript>";
	    pattern=Pattern.compile(reg_html,Pattern.CASE_INSENSITIVE); 
	    matcher=pattern.matcher("c=zxgg<javascript>alert(1)</javascript>");
	    while(matcher.find()) {
	    	//System.out.println("find");
	    	break;
	    }
	    String ss = System.getProperty("user.dir");
	    //System.out.println(ss);
	}
}
