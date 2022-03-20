package ru.pronin.study.vkr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigurationProp.class)
public class VkrApplication {

	public static void main(String[] args) {
		SpringApplication.run(VkrApplication.class, args);
	}

}
