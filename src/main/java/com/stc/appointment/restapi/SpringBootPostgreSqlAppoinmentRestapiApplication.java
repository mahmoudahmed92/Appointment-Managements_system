package com.stc.appointment.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
@SpringBootApplication
@EnableSwagger2
public class SpringBootPostgreSqlAppoinmentRestapiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootPostgreSqlAppoinmentRestapiApplication.class, args);
		System.err.println("::: SpringBootPostgreSqlAppoinmentRestapiApplication Started ::: ");
	}

}
