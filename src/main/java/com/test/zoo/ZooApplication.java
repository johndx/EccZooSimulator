package com.test.zoo;

import com.test.zoo.utility.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZooApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZooApplication.class, args);
	}

	private final Logger log = LoggerFactory.getLogger(ZooApplication.class);

	@Autowired
	private DataLoader dataLoader;

	/**
	 * Command line runner to execute initialisation configuration at
	 * App startup, could also be accomplished via "@PostConstryct"
	 * annotated method.
	 * @param ctx
	 * @return
	 */
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			log.info("Cmd Line Runner - performing app initialisation");
			dataLoader.loadBaseData();
		};
	}

}
