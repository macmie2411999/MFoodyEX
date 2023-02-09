package com.macmie.mfoodyex;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@SpringBootApplication
@EnableSwagger2
//@EnableWebMvc
public class MFoodyExApplication {

    public static void main(String[] args) {
        SpringApplication.run(MFoodyExApplication.class, args);
    }

}
