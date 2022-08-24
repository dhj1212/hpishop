package com.hpi.system.config.shiro;

import com.hpi.common.util.MD5Utils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig
{
	@Bean("shirFilter")
	 public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
	      System.out.println("--------------ShiroConfiguration.shirFilter()---------------");
	      ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
	     // shiroFilterFactoryBean.setSecurityManager(securityManager);
	      shiroFilterFactoryBean.setSecurityManager(securityManager);
	      //拦截器.
	      Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
	      // 配置不会被拦截的链接 顺序判断，因为前端模板采用了thymeleaf，这里不能直接使用 ("/static/**", "anon")来配置匿名访问，必须配置到每个静态目录
	      filterChainDefinitionMap.put("/static/css/**", "anon");
	      //filterChainDefinitionMap.put("/public/**", "anon");
	        filterChainDefinitionMap.put("/static/images/**", "anon");
	        filterChainDefinitionMap.put("/static/js/**", "anon");
	        //filterChainDefinitionMap.put("/html/**", "anon");
	        filterChainDefinitionMap.put("/dologin", "anon");
	        filterChainDefinitionMap.put("/test", "anon");
	        filterChainDefinitionMap.put("/login", "anon");
	        filterChainDefinitionMap.put("/userlogin", "anon");
	        filterChainDefinitionMap.put("/randomImage/**", "anon");
	        filterChainDefinitionMap.put("/403", "anon");
	        filterChainDefinitionMap.put("/**/*.js", "anon");
			filterChainDefinitionMap.put("/**/*.css", "anon");
			//filterChainDefinitionMap.put("/**/*.html", "anon");
			filterChainDefinitionMap.put("/**/*.svg", "anon");
			filterChainDefinitionMap.put("/**/*.pdf", "anon");
			filterChainDefinitionMap.put("/**/*.jpg", "anon");
			filterChainDefinitionMap.put("/**/*.png", "anon");
			filterChainDefinitionMap.put("/**/*.ico", "anon");
			
			filterChainDefinitionMap.put("/druid/**", "anon");
			filterChainDefinitionMap.put("/swagger-ui.html", "anon");
			filterChainDefinitionMap.put("/swagger**/**", "anon");
			filterChainDefinitionMap.put("/v2/api-docs", "anon");
			filterChainDefinitionMap.put("/csrf", "anon");
			filterChainDefinitionMap.put("/webjars/**", "anon");
	        filterChainDefinitionMap.put("/static/layui/**", "anon");
	        
	        //filterChainDefinitionMap.put("/static/public/ant-design-vue/public/cdn/babel-polyfill/", "anon");
	        
	        filterChainDefinitionMap.put("/getCaptchaImage", "anon");
	      //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
	      filterChainDefinitionMap.put("/logout", "logout");
	   // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
	      shiroFilterFactoryBean.setLoginUrl("/");
	      shiroFilterFactoryBean.setUnauthorizedUrl("/sys/common/403");
	      
		  filterChainDefinitionMap.put("/**","user");
		//所有路径必须授权访问（登录），且必须放在最后
		  filterChainDefinitionMap.put("/**", "authc");
		  Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
		  filterMap.put("anon", new AnonymousFilter());
		  filterMap.put("jwt", new JwtFilter());
		  filterMap.put("logout", new LogoutFilter());
		  shiroFilterFactoryBean.setFilters(filterMap);
			
			
	      //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
	      //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
	      
	      //filterChainDefinitionMap.put("/*", "authc");//表示需要认证才可以访问
	      //filterChainDefinitionMap.put("/*.*", "authc");//表示需要认证才可以访问
	      
	      // 登录成功后要跳转的链接
	      shiroFilterFactoryBean.setSuccessUrl("/index");
	
	      //未授权界面;
	      shiroFilterFactoryBean.setUnauthorizedUrl("/noright");
	   // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
	   		filterChainDefinitionMap.put("/**", "jwt");

	      shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
	      //System.out.println("--------------ShiroConfiguration.shirFilter()--------end-------");
	      return shiroFilterFactoryBean;
	  }

	   /**
	    * 凭证匹配器
	    * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	    * ）
	    * @return
	    */
	   //@Bean
	   public HashedCredentialsMatcher hashedCredentialsMatcher(){
	      HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
	      hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
	      hashedCredentialsMatcher.setHashIterations(MD5Utils.ITERATIONS);//散列的次数，比如散列两次，相当于 md5(md5(""));
	     return hashedCredentialsMatcher;
	    //  return null;
	   }

	   @Bean("shiroRealm")
	   public ShiroRealm shiroRealm(){
		   ShiroRealm shiroRealm = new ShiroRealm();
		   //取消密码匹配
		  // shiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
	      return shiroRealm;
	   }

	   	@Bean
	    public SubjectFactory subjectFactory() {
	        return new JwtDefaultSubjectFactory();
	    }
	   
	  // @Bean
	   //public Realm realm() {
	   //     return new ShiroRealm();
	  // }
	  // @Bean
	  /** public SecurityManager securityManager(){
	      DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
	      securityManager.setRealm(shiroRealm());
	    //注入记住我管理器
	      securityManager.setRememberMeManager(rememberMeManager());
	      // 关闭 ShiroDAO 功能
	        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
	        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
	        // 不需要将 Shiro Session 中的东西存到任何地方（包括 Http Session 中）
	        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
	        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
	        securityManager.setSubjectDAO(subjectDAO);
	        securityManager.setSubjectFactory(subjectFactory());
	      return securityManager;
	   }*/
	   @Bean("securityManager")
	   public DefaultWebSecurityManager securityManager(ShiroRealm myRealm) {
	        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
	        securityManager.setRealm(shiroRealm());
	        /*
	         * b
	         * */
	        // 关闭 ShiroDAO 功能
	        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
	        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
	        // 不需要将 Shiro Session 中的东西存到任何地方（包括 Http Session 中）
	        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
	        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
	        securityManager.setSubjectDAO(subjectDAO);
	        //禁止Subject的getSession方法
	        securityManager.setSubjectFactory(subjectFactory());
	        return securityManager;
	    }
	   /**
	    *  开启shiro aop注解支持.
	    *  使用代理方式;所以需要开启代码支持;
	    * @param securityManager
	    * @return
	    */
	   @Bean
	   public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
	      AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
	      authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
	      return authorizationAttributeSourceAdvisor;
	   }
	   //由异常统一处理
	   /**@Bean(name="simpleMappingExceptionResolver")
	   public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
	      SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
	      Properties mappings = new Properties();
	      mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
	      mappings.setProperty("UnauthorizedException","/403");
	      r.setExceptionMappings(mappings);  // None by default
	      r.setDefaultErrorView("error");    // No default
	      r.setExceptionAttribute("error");     // Default is "exception"
	      //r.setWarnLogCategory("example.MvcLogger");     // No default
	      return r;
	   }**/
	   //https://blog.csdn.net/qq_22860341/article/details/85001668
	   //https://www.jianshu.com/p/fb72d833ed9a
	   @Bean
	   public SimpleCookie rememberMeCookie() {
		    // 设置cookie名称，对应login.html页面的<input type="checkbox" name="rememberMe"/>
		    SimpleCookie cookie = new SimpleCookie("rememberMe");
		    // 设置cookie的过期时间，单位为秒，这里为一天
		    //cookie.setMaxAge(86400);
		    cookie.setMaxAge(180);
		 // cookie生效时间30天,单位秒;
	        //cookie.setMaxAge(2592000);
		    // 浏览器中通过document.cookie可以获取cookie属性，设置了HttpOnly=true,在脚本中就不能的到cookie，可以避免cookie被盗用
		    cookie.setHttpOnly(true);
		 // JSESSIONID的path为/用于多个系统共享JSESSIONID
		    cookie.setPath("/");
		    return cookie;
		}
	   /**
	    * 
	    * @方法说明: 添加注解支持
	    * @参数： @return   
	    * @返回值： DefaultAdvisorAutoProxyCreator  
	    * @异常：
	    * @作者： duhj
	    * @创建日期 2021年4月27日
	    *
	    * 历史记录
	    * 1、修改日期：
	    *    修改人：
	    *    修改内容：
	    */
	   	/**@Bean
	    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
	        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
	        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
	        // https://zhuanlan.zhihu.com/p/29161098
	        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
	        return defaultAdvisorAutoProxyCreator;
	    }**/
	   
	   	/**
	   	 * 
	   	 * @方法说明: 添加注解支持
	   	 * @参数： @return   
	   	 * @返回值： DefaultAdvisorAutoProxyCreator  
	   	 * @异常：
	   	 * @作者： duhj
	   	 * @创建日期 2021年4月28日
	   	 *
	   	 * 历史记录
	   	 * 1、修改日期：
	   	 *    修改人：
	   	 *    修改内容：
	   	 */
	   	@Bean
		@DependsOn("lifecycleBeanPostProcessor")
		public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
			DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
			defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
			/**
			* 解决重复代理问题 github#994
			* 添加前缀判断 不匹配 任何Advisor
			*/
			defaultAdvisorAutoProxyCreator.setUsePrefix(true);
			defaultAdvisorAutoProxyCreator.setAdvisorBeanNamePrefix("_no_advisor");
			return defaultAdvisorAutoProxyCreator;
		}
	   	
	   	/**
	   	 * 
	   	 * @方法说明: TODO
	   	 * @参数： @return   
	   	 * @返回值： LifecycleBeanPostProcessor  
	   	 * @异常：
	   	 * @作者： duhj
	   	 * @创建日期 2021年4月27日
	   	 *
	   	 * 历史记录
	   	 * 1、修改日期：
	   	 *    修改人：
	   	 *    修改内容：
	   	 */
	   	@Bean
	    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
	        return new LifecycleBeanPostProcessor();
	    }
	   
		/**
		 * cookie管理对象
		 * @return
		 */
	   	@Bean
		public CookieRememberMeManager rememberMeManager() {
		    CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		    cookieRememberMeManager.setCookie(rememberMeCookie());
		    // rememberMe cookie加密的密钥 
		    cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		    return cookieRememberMeManager;
		}
	   	
	    /**
	     * （新增方法）
	     * FormAuthenticationFilter 过滤器 过滤记住我
	     * @return
	     */
	   	@Bean
	    public FormAuthenticationFilter formAuthenticationFilter(){
	        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
	        //对应前端的checkbox的name = rememberMe
	        formAuthenticationFilter.setRememberMeParam("rememberMe");
	        return formAuthenticationFilter;
	    }
	        		

}
