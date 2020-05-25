package com.leader.ren;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.leader.ren.mapper")
@EnableSwagger2
@EnableAsync
@EnableScheduling
public class RenApplication {

    public static void main(String[] args) {
        SpringApplication.run(RenApplication.class, args);
    }

}
