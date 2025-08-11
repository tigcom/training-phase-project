package com.example.user_service;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.tomcat.util.net.WriteBuffer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDubbo
@EnableAspectJAutoProxy
@EnableJpaAuditing
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
