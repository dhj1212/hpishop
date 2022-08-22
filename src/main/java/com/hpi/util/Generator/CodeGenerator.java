package com.hpi.util.Generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;
import java.util.Scanner;

public class CodeGenerator
{
	public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        //String url = "jdbc:mysql://localhost:3306/hero_springboot_demo?useUnicode=true&characterEncoding=utf8&useSSL=true";
        String projectPath = System.getProperty("user.dir");
        String url = "jdbc:oracle:thin:@localhost:1521:oralin";
        String username = "hpshop";
        String password = "hpshop";
        String author = "dhj";
        //String moduleName =scanner("模块名");
        String moduleName="ydpub";
        String tables="YD_RESOURCES";

        String outputDir = projectPath + "/src/main/java" ;
        String parentPackage = "com.hpi.modules";

        String outputFileXml = projectPath + "/src/main/java/com/hpi/modules/"+moduleName+"/mapper/xml";;
        FastAutoGenerator.create(url, username, password)
        .globalConfig(builder -> {
            builder.author(author) // 设置作者
                    //.fileOverride() // 覆盖已生成文件
                    //.enableSwagger() // 开启 swagger 模式
                    .outputDir(outputDir); // 指定输出目录
        })
                .packageConfig(builder -> {
                    builder.parent(parentPackage) // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .entity("entity") // pojo 实体类包名,其它包名同理
                            .pathInfo(Collections.singletonMap(OutputFile.xml, outputFileXml)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)// 设置需要生成的表名
                            .entityBuilder()//
                            .enableLombok()// 是否使用lombok注解;
                            .idType(IdType.ASSIGN_UUID)
                            .enableTableFieldAnnotation()// 生成的实体类字段上是否加注解 @TableField("数据库字段名称")
                            .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略：下划线转驼峰命
                            .columnNaming(NamingStrategy.underline_to_camel);    //数据库表字段映射到实体的命名策略：下划线转驼峰命
//                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                }).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
        System.out.println("代码生成成功！");
    }
}
