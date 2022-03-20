package ru.pronin.study.vkr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class VkrApplication {

	public static void main(String[] args) {
		SpringApplication.run(VkrApplication.class, args);
		ApplicationContext context = new AnnotationConfigApplicationContext();
		//context.getBean();
	}

}
