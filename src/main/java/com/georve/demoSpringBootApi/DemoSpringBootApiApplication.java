package com.georve.demoSpringBootApi;

import com.georve.demoSpringBootApi.config.Profiles;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@ActiveProfiles(value = "development")
public class DemoSpringBootApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoSpringBootApiApplication.class, args);
	}

}
