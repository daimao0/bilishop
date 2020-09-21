package com.damao.bilibilishop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.damao.bilibilishop.dao")
public class BilibilishopApplication {

    public static void main(String[] args) {
        SpringApplication.run(BilibilishopApplication.class, args);
    }

}
