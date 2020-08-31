package com.jun.mqttxplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@MapperScan(basePackages = "com.jun.mqttxplatform.dao")
@SpringBootApplication
public class MqttxPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqttxPlatformApplication.class, args);
	}

}
