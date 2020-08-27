package Oleksandr.Turchanovskyi.serviceImpl;

import Oleksandr.Turchanovskyi.model.Weather;
import Oleksandr.Turchanovskyi.model.WeatherData;
import Oleksandr.Turchanovskyi.model.WeatherURL;
import Oleksandr.Turchanovskyi.repository.WeatherDataRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
class WeatherDataServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherURL weatherURL;

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @InjectMocks
    private WeatherDataServiceImpl weatherDataService;

    @Test
    void findAll() {

        List<WeatherData> list = new ArrayList<>();
        WeatherData weatherData1 = new WeatherData.Builder()
                .setId(null)
                .setCurrentWeather("Clouds")
                .setCurrentTemperature(36.6)
                .setTimestamp(7200)
                .setSunset("2020-08-27 04:48:20")
                .setSunrise("2020-08-27 18:35:39")
                .build();

        list.add(weatherData1);

        when(weatherDataRepository.findAll()).thenReturn(list);

        List<WeatherData> findList = (List<WeatherData>) weatherDataService.findAll();

        Assertions.assertSame(findList, list);

    }

    @Test
    void findByTimeStamp() {
        WeatherData weatherData = new WeatherData.Builder()
                .setId(null)
                .setCurrentWeather("Clouds")
                .setCurrentTemperature(36.6)
                .setTimestamp(7200)
                .setSunset("2020-08-27 04:48:20")
                .setSunrise("2020-08-27 18:35:39")
                .build();

        doReturn(Optional.of(weatherData)).when(weatherDataRepository).findByTimestamp(weatherData.getTimestamp());

        Optional<WeatherData> findWeatherData = weatherDataService.findByTimeStamp(weatherData.getTimestamp());

        Assertions.assertTrue(findWeatherData.isPresent());
        Assertions.assertSame(findWeatherData.get(), weatherData);
    }

    @Test
    void save() throws JsonProcessingException {

        String city = "Kraków";

        UriComponents uriComponents = UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host(weatherURL.getUrl())
                .path("")
                .query("q={keyword}&appid={appid}&units=metric")
                .buildAndExpand(city, weatherURL.getApiKey());

        String uri = uriComponents.toUriString();

        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, null, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Weather weather = objectMapper.readValue(Objects.requireNonNull(responseEntity.getBody()), Weather.class);

        Optional<WeatherData> optionalWeatherData = weatherDataRepository.findByTimestamp(weather.getTimestamp());

        if (optionalWeatherData.isPresent()) {
            WeatherData weatherData = new WeatherData.Builder()
                    .setId(null)
                    .setCurrentWeather("Clouds")
                    .setCurrentTemperature(36.6)
                    .setTimestamp(7200)
                    .setSunset("2020-08-27 04:48:20")
                    .setSunrise("2020-08-27 18:35:39")
                    .build();

            doReturn(weatherData).when(weatherDataRepository).save(any());

            WeatherData saveWeatherData = weatherDataService.save();

            Assertions.assertNotNull(saveWeatherData);
            Assertions.assertEquals(saveWeatherData.getId(), weatherData.getId());
        }
    }
}
