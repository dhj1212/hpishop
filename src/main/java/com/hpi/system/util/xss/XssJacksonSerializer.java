package com.hpi.system.util.xss;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;


/**
 * 
 * <pre>
 * @业务名:JSON字符串响应结果处理
 * @功能说明: 
 * @编写日期:	2021年10月19日
 * @作者:	duhj
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class XssJacksonSerializer extends JsonSerializer<String>
{
	@Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		 System.out.println("XssJacksonSerializer==s==="+s);
		jsonGenerator.writeString(StringEscapeUtils.escapeHtml4(s));
    }

}
