package com.khaoula.service_voiture;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "service.voiture.init-data=false")
@ActiveProfiles("test")
class ServiceVoitureApplicationTests {

	@Test
	void contextLoads() {
	}

}
