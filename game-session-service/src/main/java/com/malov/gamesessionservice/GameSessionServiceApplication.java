package com.malov.gamesessionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GameSessionServiceApplication {

	@Bean
	@LoadBalanced
	public RestTemplate loadBalanced() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(GameSessionServiceApplication.class, args);
	}

}
