package com.qgq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName : Application
 * @Author : QiaoGuangqing
 * @Date : 2020-06-08
 * @Description :
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.qgq.common.dao"})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
