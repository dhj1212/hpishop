package com.hpi.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer
{
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
      //  registry.addViewController("/").setViewName("login");
      // registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addViewController("/").setViewName("login");//前拼templates，后拼.html
        //registry.addViewController("/index.html").setViewName("index");//浏览器发送/请求来到login.html页面，不用写controller控制层的请求方法了
    }

	/**
     * 将static下面的js，css文件加载出来
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");
    	registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //registry.addResourceHandler("/static/").addResourceLocations("classpath:/static/");
		if(!registry.hasMappingForPattern("/static/**")){
		    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		}
        registry.addResourceHandler("/yd/resources/**").addResourceLocations("file:D:/ydpic/");
        //registry.addResourceHandler("/static/photos/img/**").addResourceLocations("file:D:/static/photos/img/");

    }
    
    /**
     * 
     * @方法说明: 决ie responsebody返回json的时候提示下载问题 
     * @参数： @return   
     * @返回值： MappingJackson2HttpMessageConverter  
     * @异常：
     * @作者： duhj
     * @创建日期 2020年8月24日
     *
     * 历史记录
     * 1、修改日期：
     *    修改人：
     *    修改内容：
     */
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        List<MediaType> supportedMediaTypes = new ArrayList<MediaType>();
        MediaType media = new MediaType(MediaType.TEXT_HTML, Charset.forName("UTF-8"));
        supportedMediaTypes.add(media);
        jsonConverter.setSupportedMediaTypes(supportedMediaTypes);
        return jsonConverter;
    }
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customJackson2HttpMessageConverter());
    }
    
   
    
}
