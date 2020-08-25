package Oleksandr.Turchanovskyi.controllers;

import Oleksandr.Turchanovskyi.model.Weather;
import Oleksandr.Turchanovskyi.model.WeatherURL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;


@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final RestTemplate restTemplate;
    private final WeatherURL weatherURL;


    public WeatherController(RestTemplate restTemplate, WeatherURL weatherURL) {
        this.restTemplate = restTemplate;
        this.weatherURL = weatherURL;
    }

    @PostMapping
    public ResponseEntity<Weather> getWeather() throws JsonProcessingException {

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


        return ResponseEntity.ok().body(weather);
    }
}
