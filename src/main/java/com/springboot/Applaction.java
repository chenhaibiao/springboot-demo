package com.springboot;
import org.mybatis.spring.annotation.MapperScan; 
import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication; 
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ServletComponentScan
@EnableWebMvc
@EnableTransactionManagement //如果mybatis中service实现类中加入事务注解，需要此处添加该注解
@MapperScan("com.springboot.mapper") //配置扫描mapper接口的地址
public class Applaction { 
	
	public static void main(String[] args) { 
		SpringApplication.run(Applaction.class, args); 
	}
}


