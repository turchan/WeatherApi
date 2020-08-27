package Oleksandr.Turchanovskyi;

import Oleksandr.Turchanovskyi.controllers.WeatherController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TurchanovskyiApplicationTests {

	@Autowired
	private WeatherController weatherController;

	@Test
	void contextLoads() throws Exception {
        Assertions.assertNotNull(weatherController);
	}

}
