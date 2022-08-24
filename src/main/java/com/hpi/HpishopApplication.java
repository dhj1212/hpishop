package com.hpi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.hpi.system.filters")
@ComponentScan(basePackages
        = {
        "com.hpi.common",
        "com.hpi.modules",
        "com.hpi.system"
})
@EnableSwagger2
public class HpishopApplication {

    public static void main(String[] args) {
        SpringApplication.run(HpishopApplication.class, args);
    }

}
