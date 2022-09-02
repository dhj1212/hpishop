package com.hpi.system.util.xss;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hpi.common.constant.CommonConstant;
import com.hpi.common.system.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper
{
	// 存放JSON数据主体
	//https://www.cnblogs.com/shanheyongmu/p/11765017.html
	 	HttpServletRequest orgRequest = null;

		//private final String body;
		private final byte[] body;

		private static String badStrReg =
			"\\b(and|or)\\b.{1,6}?(=|>|<|\\bin\\b|\\blike\\b)|\\/\\*.+?\\*\\/|<\\s*script\\b|\\bEXEC\\b|UNION.+?SELECT|UPDATE.+?SET|INSERT\\s+INTO.+?VALUES|(SELECT|DELETE).+?FROM|(CREATE|ALTER|DROP|TRUNCATE)\\s+(TABLE|DATABASE)";
		private static final Pattern sqlPattern = Pattern.compile(badStrReg, Pattern.CASE_INSENSITIVE);
		private static String badSqlStr = "'|exec|execute|insert|select|delete|update|count|drop|mid|master|truncate|"
			+ "char|declare|sitename|net user|xp_cmdshell|,|like'|and|create|"
			+ "table|from|grant|group_concat|column_name|"
			+ "information_schema.columns|table_schema|union|where|order|"
			+ "chr|;|--|,|like|//|/|%|#";

	public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) throws IOException {
			super(servletRequest);
			orgRequest = servletRequest;
			//InputStream inputStream = servletRequest.getInputStream();
			//body=StreamUtils.copyToByteArray(inputStream);
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader bufferedReader = null;
			try {
				InputStream inputStream = servletRequest.getInputStream();
				//byte[] inputStream_=org.springframework.util.StreamUtils.copyToByteArray(inputStream);
				System.out.println("inputStream==="+inputStream);
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
				String bodyStr = stringBuilder.toString();
				System.out.println("bodyStr==="+bodyStr);
				if (bodyStr ==null || bodyStr.trim().length()<=0) {
					body = bodyStr.getBytes(StandardCharsets.UTF_8);
				} else {
					boolean fileUpload=false;
					boolean isMultipartContent = ServletFileUpload.isMultipartContent(servletRequest);
					CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(servletRequest.getSession().getServletContext());
					boolean isMultipart = commonsMultipartResolver.isMultipart(servletRequest);
					if (isMultipartContent || isMultipart) {
						fileUpload = true;
					}

					JSONObject bodyJson = JSON.parseObject(bodyStr);
					JSONObject formDataJson = bodyJson.getJSONObject("formData");
					String requestUri = super.getRequestURI();
					if (formDataJson != null) {
						// 有 formData 参数的请求
						for (Map.Entry<String, Object> entry : formDataJson.entrySet()) {
							String paramKey = entry.getKey();
							Object paramValue = entry.getValue();
							//System.out.println("paramKey==="+paramKey+";paramValue="+paramValue);
							if (null == paramValue) {
								continue;
							}

							// 咨询管理 编辑保存
							if (requestUri.contains("/api/inf/insertOrUpdate")) {
								// content contentText字段不作字符处理
								if (!"content".equals(paramKey) && !"contentText".equals(paramKey)) {
									//paramValue = stripXSS(paramValue.toString());
									paramValue = cleanSQLInject(stripXSS(paramValue!=null ? paramValue.toString():""));
								}
							} // 前后台登录
							else if (requestUri.contains("/login")) {
								// password 密码字段不作字符处理
								if (!"password".equals(paramKey)) {
									paramValue = cleanSQLInject(stripXSS(paramValue!=null ?paramValue.toString():""));
								}
							} else {
								paramValue = stripXSS(paramValue.toString());
							}
							System.out.println("paramValue==="+paramValue);
							formDataJson.put(entry.getKey(), paramValue);
						}
						bodyJson.put("formData", formDataJson);
						if (bodyJson!=null){
							body = bodyJson.toJSONString().getBytes(StandardCharsets.UTF_8);
						}else{
							body = bodyStr.getBytes(StandardCharsets.UTF_8);
						}

						System.out.println("body------===="+body);
					} else {
						// 没有 formData 参数的请求
						System.out.println("fileUpload==="+fileUpload);
						if (!fileUpload) {
							// 获取body中的请求参数
							JSONObject bjson = JSONObject.parseObject(new String(bodyStr));

							// 校验并过滤xss攻击和sql注入
							for (Map.Entry<String, Object> entryjson : bjson.entrySet()) {
								String paramKey = entryjson.getKey();
								Object paramValue = entryjson.getValue();
								System.out.println("k:"+paramKey+";vaule:"+paramValue);
								Object paramValue_ = cleanSQLInject(stripXSS(paramValue!=null ? paramValue.toString():""));
								if (paramValue!=null && !paramValue.equals(paramValue_)){
									bjson.put(paramKey,paramValue_);
									System.out.println("paramValue=="+paramValue_);
								}

								//throw new AppException("非法的字符"+json.getString(k));
							}
							body =bjson.toJSONString().getBytes(StandardCharsets.UTF_8);
						}else{
							body = bodyStr.getBytes(StandardCharsets.UTF_8);
						}

					}
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

		}
		

	/*	private String getSqlFliteredValues(String requestUri, String value) {
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
		}*/

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
					//encodedValues[i] = convertJsonValue(values[i], requestUri, "password", "newpassword2", "");
				} // 后台用户管理 编辑保存
				else if (requestUri.contains("/api/user/insertOrUpdate")) {
					//encodedValues[i] = convertJsonValue(values[i], requestUri, "password", "oldpassword", "");
				} else {
					// 过滤JS攻击
					String encodeStr = stripXSS(values[i]);
					System.out.println("encodeStr===============encodeStr=="+encodeStr);
					// 过滤SQL攻击
					//encodedValues[i] = getSqlFliteredValues(requestUri, encodeStr);
					encodedValues[i] =cleanSQLInject(encodeStr);
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
			//value = getSqlFliteredValues(requestUri, value);
			value=cleanSQLInject(value);
			return value;
		}

		/**
		 * 覆盖getHeader方法，将参数名和参数值都做xss过滤。 如果需要获得原始的值，则通过super.getHeaders(name)来获取
		 * getHeaderNames 也可能需要覆盖
		 */
		@Override
		public String getHeader(String name) {
			String value = super.getHeader(name);

			String requestUri = super.getRequestURI();
			if (value == null){
				return null;
			}
			// 过滤JS攻击
			value = stripXSS(value);
			if (!CommonConstant.AUTHORIZATION_TOKEN.equals(name)){
				// 过滤SQL攻击
				value=cleanSQLInject(value);
			}

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
			String temp = XssFilterUtil.stripXss(value);
			System.out.println("temp=="+temp);
			if (!value.equals(temp)) {

				log.error("xss攻击检查：参数含有非法攻击字符，已禁止继续访问,请检查参数，-->" + value);

				throw new AppException("参数含有非法字符" );
			}
			return temp;
		}
		public String cleanSQLInject(String value) {
			// 非法sql注入正则
			//Pattern sqlPattern = Pattern.compile(badStrReg, Pattern.CASE_INSENSITIVE);

			/*Matcher matcher = sqlPattern.matcher(src.toLowerCase());
			if (matcher.find()) {
				log.error("sql注入检查：输入信息存在SQL攻击！");
				src = matcher.replaceAll("");
				throw new AppException("参数含有非法攻击字符，已禁止继续访问");
			}*/
			if (value!=null && value.length()>0){
				String value_=cleanSQL(value);
				if (!value.equals(value_)){
					log.error("sql注入检查：输入信息存在SQL攻击，请检查参数："+value);
					throw new AppException("参数含有非法字符");
				}
				return value_;
			}else{
				return value;
			}
		}

		private String cleanSQL(String value) {
			String[] badStrs = badSqlStr.split("\\|");
			for (int i = 0; i < badStrs.length; i++) {
				if (value.toLowerCase().trim().contains((badStrs[i]))) {
					log.info("存在方法字符："+badStrs[i]);
					value = value.replaceAll(badStrs[i],"");
					break;
				}
			}
			return value;
		}


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
					//paramValue = getSqlFliteredValues(requestUri, paramValue.toString());
					paramValue=cleanSQL(paramValue.toString());
				}
				formDataJson.put(entry.getKey(), paramValue);
			}
			return bodyJson.toJSONString();
		}
}
