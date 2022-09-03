package com.hpi.system.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(value={"com.hpi.modules.**.mapper*"})
public class MybatisPlusConfig
{
	  /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
	/**
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
    	PerformanceInterceptor performanceInterceptor =new PerformanceInterceptor();
        //格式化sql语句
        Properties properties =new Properties();
        properties.setProperty("format", "faalse");
        performanceInterceptor.setProperties(properties);
        return performanceInterceptor;
        return new PerformanceInterceptor();
    }
	**/
    /**
     * 分页插件 已经过时
     */
    /*@Bean
    public PaginationInterceptor paginationInterceptor() {
    	 *//*PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
         // 设置方言
         paginationInterceptor.setDialectType("mysql");
         return paginationInterceptor;*//*
    	return new PaginationInterceptor().setLimit(-1);//设置sql的limit为无限制，默认是500
        //return new PaginationInterceptor();
    }*/
    /**
     * @description: 分页支持
     * @param
     * @return
     * @throws
     * @author dhj
     * @date 2022/8/20 14:00
    */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor paginationInterceptor=new MybatisPlusInterceptor();
        //乐观锁插件
        paginationInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //分页插件
        paginationInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return paginationInterceptor;
    }
    
   


}
