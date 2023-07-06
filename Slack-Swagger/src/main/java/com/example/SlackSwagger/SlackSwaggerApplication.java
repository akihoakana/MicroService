package com.example.SlackSwagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SlackSwaggerApplication {

	@Autowired
	private static SlackConfig slackConfig;

	public static void main(String[] args) {
//		System.out.println("Slack connect");
//		String string = " Hello";
//		System.out.println("string = " + string);
//		slackConfig.sendMessageToSlack(string);
		SpringApplication.run(SlackSwaggerApplication.class, args);
	}

}
