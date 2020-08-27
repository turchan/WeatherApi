package Oleksandr.Turchanovskyi.mvcTests;

import Oleksandr.Turchanovskyi.model.WeatherData;
import Oleksandr.Turchanovskyi.service.WeatherDataService;
import Oleksandr.Turchanovskyi.serviceImpl.WeatherDataServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MvcTest {

    private final static String URI = "/";

    @MockBean
    private WeatherDataServiceImpl weatherDataService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeAll
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    void testFindAll() throws Exception {
        List<WeatherData> list = new ArrayList<>();
        WeatherData weatherData1 = new WeatherData.Builder()
                .setCurrentWeather("Clouds")
                .setCurrentTemperature(36.6)
                .setTimestamp(7200)
                .setSunset("2020-08-27 04:48:20")
                .setSunrise("2020-08-27 18:35:39")
                .build();

        list.add(weatherData1);

        given(weatherDataService.findAll()).willReturn(list);

        mockMvc.perform(get(URI)).andExpect(status().isOk());
    }
}
