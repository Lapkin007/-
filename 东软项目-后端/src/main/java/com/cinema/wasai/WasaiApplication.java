package com.cinema.wasai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.cinema.wasai.mapper")
@SpringBootApplication
//@ServletComponentScan
public class WasaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WasaiApplication.class, args);
    }

}
