package com.hpi.system.util.xss;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.util.Map;
import java.util.regex.Pattern;

public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper
{
	// 存放JSON数据主体
	//https://www.cnblogs.com/shanheyongmu/p/11765017.html
	 	HttpServletRequest orgRequest = null;

		//private final String body;
		private final byte[] body;

		private static final Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
		private static final Pattern script3cPattern = Pattern.compile("%3Cscript%3E(.*?)%3C/script%3E", Pattern.CASE_INSENSITIVE);
		private static final Pattern srcPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		private static final Pattern scriptPattern1 = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
		private static final Pattern scriptPattern2 = Pattern.compile("%3C/script%3E", Pattern.CASE_INSENSITIVE);
		private static final Pattern scriptPattern3 = Pattern.compile("<script(.*?)>",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		private static final Pattern scriptPattern4 = Pattern.compile("%3Cscript(.*?)%3E",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		private static final Pattern evalPattern = Pattern.compile("eval\\((.*?)\\)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		private static final Pattern e­Pattern = Pattern.compile("e­xpression\\((.*?)\\)",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
		private static final Pattern javascriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
		private static final Pattern vbscriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
		private static final Pattern onloadPattern = Pattern.compile("onload(.*?)=",Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);


		public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) throws IOException {
			super(servletRequest);
			orgRequest = servletRequest;
			InputStream inputStream = servletRequest.getInputStream();
			body=StreamUtils.copyToByteArray(inputStream);
		}
		
		/**public XssHttpServletRequestWrapper1(HttpServletRequest servletRequest) throws IOException {
			super(servletRequest);

			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader bufferedReader = null;
			try {
				InputStream inputStream = servletRequest.getInputStream();
				org.springframework.util.StreamUtils.copyToByteArray(ininputStream);
				if (inputStream != null) {
					bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
					char[] charBuffer = new char[128];
					int bytesRead = -1;
					while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
						stringBuilder.append(charBuffer, 0, bytesRead);
					}
				} else {
					stringBuilder.append("");
				}
				
			} catch (IOException ex) {
				throw ex;
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException ex) {
						throw ex;
					}
				}
			}
			String bodyStr = stringBuilder.toString();
			System.out.println("bodyStr==="+bodyStr);
			if (bodyStr ==null || bodyStr.trim().length()<=0) {
				body = bodyStr;
			} else {

				String requestUri = super.getRequestURI();
				//bodyStr=URLDecoder.decode(bodyStr, "UTF-8");
				//bodyStr = bodyStr.substring(bodyStr.indexOf("{"));
				JSONObject bodyJson = JSON.parseObject(bodyStr);
				JSONObject formDataJson = bodyJson.getJSONObject("formData");
				System.out.println("formDataJson==="+formDataJson.toJSONString());
				if (formDataJson != null) {
					// 有 formData 参数的请求
					for (Map.Entry<String, Object> entry : formDataJson.entrySet()) {
						String paramKey = entry.getKey();
						Object paramValue = entry.getValue();
						System.out.println("paramKey==="+paramKey+";paramValue="+paramValue);
						if (null == paramValue) {
							continue;
						}

						// 咨询管理 编辑保存
						if (requestUri.contains("/api/inf/insertOrUpdate")) {
							// content contentText字段不作字符处理
							if (!"content".equals(paramKey) && !"contentText".equals(paramKey)) {
								paramValue = stripXSS(paramValue.toString());
							}
						} // 前后台登录
						else if (requestUri.contains("/loginUser")) {
							// password 密码字段不作字符处理
							if (!"password".equals(paramKey)) {
								paramValue = stripXSS(paramValue.toString());
							}
						} else {
							paramValue = stripXSS(paramValue.toString());
						}
						System.out.println("paramValue==="+paramValue);
						formDataJson.put(entry.getKey(), paramValue);
					}
					bodyJson.put("formData", formDataJson);
					body = bodyJson.toJSONString();
				} else {
					// 没有 formData 参数的请求
					body = bodyStr;
				}
			}
		}
		**/
		private String getSqlFliteredValues(String requestUri, String value) {
			String result = value;
			if (requestUri != null && value != null) {
				try {
					value = new String(value.getBytes("UTF-8"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
				}
				//if (requestUri.contains("getSingleById")) {
				//	value = cleanSQL(value);
				//}
			}
			return result;
		}

		@Override
		public String[] getParameterValues(String parameter) {
			System.out.println("parameter=="+parameter);
			String[] values = super.getParameterValues(parameter);
			String requestUri = super.getRequestURI();
			if (values == null) {
				System.out.println("values==null");
				return null;
			}
			System.out.println("getParameterValues===============values=="+values.length);
			int count = values.length;
			String[] encodedValues = new String[count];
			for (int i = 0; i < count; i++) {
				// 后台修改密码
				if (requestUri.contains("/api/user/updateByIdForPs")) {
					encodedValues[i] = convertJsonValue(values[i], requestUri, "password", "newpassword2", "");
				} // 后台用户管理 编辑保存
				else if (requestUri.contains("/api/user/insertOrUpdate")) {
					encodedValues[i] = convertJsonValue(values[i], requestUri, "password", "oldpassword", "");
				} else {
					// 过滤JS攻击
					String encodeStr = stripXSS(values[i]);
					System.out.println("encodeStr===============encodeStr=="+encodeStr);
					// 过滤SQL攻击
					encodedValues[i] = getSqlFliteredValues(requestUri, encodeStr);
				}
			}

			return encodedValues;
		}

		/*
		 * 覆盖getParameter方法，将参数名和参数值都做xss过滤。
		 * 如果需要获得原始的值，则通过super.getParameterValues(name)来获取
		 * getParameterNames,getParameterValues和getParameterMap也可能需要覆盖
		 */
		@Override
		public String getParameter(String parameter) {
			String value = super.getParameter(parameter);
			System.out.println("getParameter===============value="+value);
			String requestUri = super.getRequestURI();
			if (value == null) {
				return null;
			}
			// 过滤JS攻击
			value = stripXSS(value);
			// 过滤SQL攻击
			value = getSqlFliteredValues(requestUri, value);
			return value;
		}

		/**
		 * 覆盖getHeader方法，将参数名和参数值都做xss过滤。 如果需要获得原始的值，则通过super.getHeaders(name)来获取
		 * getHeaderNames 也可能需要覆盖
		 */
		@Override
		public String getHeader(String name) {
			String value = super.getHeader(name);
			System.out.println("getHeader======value========="+value);
			String requestUri = super.getRequestURI();
			if (value == null){
				return null;
			}
			// 过滤JS攻击
			value = stripXSS(value);
			// 过滤SQL攻击
			value = getSqlFliteredValues(requestUri, value);
			return value;
		}


		/*
		 * private String cleanJS(String value) {
		 * 
		 * // You'll need to remove the spaces from the html entities below value =
		 * value.replaceAll("<", "& lt;").replaceAll(">", "& gt;"); //value =
		 * value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;"); //value =
		 * value.replaceAll("'", "& #39;"); value =
		 * value.replaceAll("eval\\((.*)\\)", ""); value =
		 * value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		 * value = value.replaceAll("script", ""); //value = value.replaceAll("[*]",
		 * "[" + "*]"); //value = value.replaceAll("[+]", "[" + "+]"); //value =
		 * value.replaceAll("[?]", "[" + "?]"); return value; }
		 */
		private String stripXSS(String value) {
			System.out.println("stripXSS======value========="+value);
			if (value != null && value.indexOf("script") > -1) {
				// NOTE: It's highly recommended to use the ESAPI library and
				// uncomment the following line to
				// avoid encoded attacks.
				// value = ESAPI.encoder().canonicalize(value);
				// Avoid null characters
				value = value.replaceAll("", "");
				// Avoid anything between script tags

				value = scriptPattern.matcher(value).replaceAll("");
				value = script3cPattern.matcher(value).replaceAll("");
				// Avoid anything in a src="..." type of e­xpression
				value = srcPattern.matcher(value).replaceAll("");

				//value = scriptPattern.matcher(value).replaceAll("");
				// Remove any lonesome </script> tag
				value = scriptPattern1.matcher(value).replaceAll("");

				value = scriptPattern2.matcher(value).replaceAll("");

				value = scriptPattern3.matcher(value).replaceAll("");
				value = scriptPattern4.matcher(value).replaceAll("");
				// Avoid eval(...) e­xpressions
				value = evalPattern.matcher(value).replaceAll("");
				// Avoid e­xpression(...) e­xpressions
				value = e­Pattern.matcher(value).replaceAll("");
				// Avoid javascript:... e­xpressions
				value = javascriptPattern.matcher(value).replaceAll("");
				// Avoid vbscript:... e­xpressions
				value = vbscriptPattern.matcher(value).replaceAll("");
				// Avoid onload= e­xpressions
				value = onloadPattern.matcher(value).replaceAll("");
			}

			value = XssFilterUtil.stripXss(value);
			return value;
		}

		/*private String cleanSQL(String value) {

			String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|%|chr|mid|master|truncate|"
					+ "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|"
					+ "table|from|grant|use|group_concat|column_name|"
					+ "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|"
					+ "chr|mid|master|truncate|char|declare|or|;|-|--|,|like|//|/|%|#";

			String[] badStrs = badStr.split("\\|");
			for (int i = 0; i < badStrs.length; i++) {
				if (value.toLowerCase().trim().contains((badStrs[i]))) {
					value = "forbid";
					break;
				}
			}
			return value;
		}
*/
		/**private String cleanXSS(String value) {
			// You'll need to remove the spaces from the html entities below
			value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
			// value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "&
			// #41;");
			// value = value.replaceAll("'", "& #39;");
			value = value.replaceAll("eval\\((.*)\\)", "");
			value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
			value = value.replaceAll("script", "");
			// value = value.replaceAll("[*]", "[" + "*]");
			// value = value.replaceAll("[+]", "[" + "+]");
			// value = value.replaceAll("[?]", "[" + "?]");

			// replace sql 这里可以自由发挥
			String[] values = value.split(" ");

			String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|%|chr|mid|master|truncate|"
					+ "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|"
					+ "table|from|grant|use|group_concat|column_name|"
					+ "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|"
					+ "chr|mid|master|truncate|char|declare|or|;|-|--|,|like|//|/|%|#";

			String[] badStrs = badStr.split("\\|");
			for (int i = 0; i < badStrs.length; i++) {
				for (int j = 0; j < values.length; j++) {
					if (values[j].equalsIgnoreCase(badStrs[i])) {
						values[j] = "forbid";
					}
				}
			}
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < values.length; i++) {
				if (i == values.length - 1) {
					sb.append(values[i]);
				} else {
					sb.append(values[i] + " ");
				}
			}

			value = sb.toString();

			return value;
		}**/
		
		@Override    
	    public ServletInputStream getInputStream() throws IOException {    
	        final ByteArrayInputStream bais = new ByteArrayInputStream(body);    
	        return new ServletInputStream() {    
	    
	            @Override    
	            public int read() throws IOException {    
	                return bais.read();    
	            }  

	            @Override  
	            public boolean isFinished() {  
	                // TODO Auto-generated method stub  
	                return false;  
	            }  

	            @Override  
	            public boolean isReady() {  
	                // TODO Auto-generated method stub  
	                return false;  
	            }  

	            @Override  
	            public void setReadListener(ReadListener arg0) {  
	                // TODO Auto-generated method stub  
	                  
	            }    
	        };    
	    }

		
		/**@Override
		public ServletInputStream getInputStream() throws IOException {
			final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes("UTF-8"));
			ServletInputStream servletInputStream = new ServletInputStream() {
				@Override
				public int read() throws IOException {
					return byteArrayInputStream.read();
				}

				@Override
				public boolean isFinished() {
					return false;
				}

				@Override
				public boolean isReady() {
					return false;
				}

				@Override
				public void setReadListener(ReadListener listener) {

				}
			};
			return servletInputStream;
		}
		**/
		@Override
		public BufferedReader getReader() throws IOException {
			return new BufferedReader(new InputStreamReader(this.getInputStream()));
		}

		//public String getBody() {
			//return this.body;
		//}
		
		/**
	     * 获取最原始的request
	     * 
	     * @return
	     */
	    public HttpServletRequest getOrgRequest() {
	        return orgRequest;
	    }

	    /**
	     * 获取最原始的request的静态方法
	     * 
	     * @return
	     */
	    public static HttpServletRequest getOrgRequest(HttpServletRequest req) {
	        if (req instanceof XssHttpServletRequestWrapper) {
	            return ((XssHttpServletRequestWrapper) req).getOrgRequest();
	        }

	        return req;
	    }

	    
		/**
		 * 过滤特殊请求参数特殊字符
		 * 
		 * @param jsonString
		 * @param requestUri
		 * @param key1
		 * @param key2
		 * @param key3
		 * @return
		 */
		private String convertJsonValue(String jsonString, String requestUri, String key1, String key2, String key3) {
			JSONObject bodyJson = JSON.parseObject(jsonString);
			JSONObject formDataJson = bodyJson.getJSONObject("formData");

			// 有 formData 参数的请求
			for (Map.Entry<String, Object> entry : formDataJson.entrySet()) {
				String paramKey = entry.getKey();
				Object paramValue = entry.getValue();
				if (null == paramValue || paramValue.toString()==null ||paramValue.toString().trim().length()<=0) {
					continue;
				}
				// password newpassword2密码字段不作字符处理
				if (!key1.equalsIgnoreCase(paramKey) && !key2.equalsIgnoreCase(paramKey)
						&& !key3.equalsIgnoreCase(paramKey)) {
					// 过滤JS攻击
					paramValue = stripXSS(paramValue.toString());
					// 过滤SQL攻击
					paramValue = getSqlFliteredValues(requestUri, paramValue.toString());
				}
				formDataJson.put(entry.getKey(), paramValue);
			}
			return bodyJson.toJSONString();
		}
}
