package com.cignititech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class EmpOpsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpOpsApplication.class, args);
	}
}
