package com.example;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class ShutdownHookApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShutdownHookApplication.class, args);
	}
	
	private RestTemplate restTemplate = new RestTemplate();

	@Value("${shutdown.uri:${vcap.application.uris[0]:localhost:8080}}")
	private String thisHost;

	private String listenerUri() {
		return "http://listener-for-" + thisHost;
	}
	
	@PreDestroy
	public void shutdown() {
		restTemplate.getForObject(listenerUri() + "/shutdown_was_called.json", Object.class);
	}

	@RequestMapping("/")
	public Object test() {
		return restTemplate.getForObject(listenerUri() + "/testing.json", Object.class);
	}
}
