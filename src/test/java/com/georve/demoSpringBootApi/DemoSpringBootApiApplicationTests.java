package com.georve.demoSpringBootApi;

import com.georve.demoSpringBootApi.config.Profiles;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(value = "test")
class DemoSpringBootApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
