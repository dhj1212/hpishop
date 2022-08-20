package com.hpi.common.CommonConstant;
/**
 * 
 * <pre>
 * @业务名:
 * @功能说明: 公共部分常量定义
 * @编写日期:	2021年4月27日
 * @作者:	duhj
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface CommonConstant
{
	/** {@code 500 Server Error} (HTTP/1.0 - RFC 1945) */
    public static final Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
    /** {@code 200 OK} (HTTP/1.0 - RFC 1945) */
    public static final Integer SC_OK_200 = 200;
    
    /**访问权限认证未通过 510*/
    public static final Integer SC_JEECG_NO_AUTHZ=510;
    
    /**字典翻译文本后缀*/
    public static final String DICT_TEXT_SUFFIX = "_dictText";
    
    /**
     * 返回结果操作
     */
    public static final String DEFINE_RESULT_OPERATE= "RESULT_OPERATE";
    	public static final Integer DEFINE_RESULT_OPERATE_1= 1;//执行代码转换


    /**
     * 权限TOKEN
     */
    public static final String AUTHORIZATION_TOKEN= "x-auth-token";

}
