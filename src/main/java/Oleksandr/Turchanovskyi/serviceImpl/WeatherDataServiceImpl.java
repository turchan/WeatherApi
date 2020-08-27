package Oleksandr.Turchanovskyi.serviceImpl;

import Oleksandr.Turchanovskyi.model.Weather;
import Oleksandr.Turchanovskyi.model.WeatherData;
import Oleksandr.Turchanovskyi.model.WeatherURL;
import Oleksandr.Turchanovskyi.repository.WeatherDataRepository;
import Oleksandr.Turchanovskyi.service.WeatherDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

@Service
public class WeatherDataServiceImpl implements WeatherDataService {

    private final RestTemplate restTemplate;
    private final WeatherURL weatherURL;
    private final WeatherDataRepository weatherDataRepository;

    public WeatherDataServiceImpl(RestTemplate restTemplate, WeatherURL weatherURL, WeatherDataRepository weatherDataRepository) {
        this.restTemplate = restTemplate;
        this.weatherURL = weatherURL;
        this.weatherDataRepository = weatherDataRepository;
    }

    @Override
    public Iterable<WeatherData> findAll() {
        return weatherDataRepository.findAll();
    }

    @Override
    public Optional<WeatherData> findByTimeStamp(int timestamp) {
        return weatherDataRepository.findByTimestamp(timestamp);
    }

    @Override
    public WeatherData save() throws JsonProcessingException {

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
            WeatherData wd = new WeatherData.Builder()
                    .setId(optionalWeatherData.get().getId())
                    .setCurrentWeather(weather.getCurrentWeather())
                    .setCurrentTemperature(weather.getCurrentTemperature())
                    .setTimestamp(weather.getTimestamp())
                    .setSunset(weather.getSunset())
                    .setSunrise(weather.getSunrise())
                    .build();

            return weatherDataRepository.save(wd);

        } else {
            WeatherData wd = new WeatherData.Builder()
                    .setId(null)
                    .setCurrentWeather(weather.getCurrentWeather())
                    .setCurrentTemperature(weather.getCurrentTemperature())
                    .setTimestamp(weather.getTimestamp())
                    .setSunset(weather.getSunset())
                    .setSunrise(weather.getSunrise())
                    .build();

           return weatherDataRepository.save(wd);
        }
    }

    @Override
    public String send() throws URISyntaxException {

        final String baseUrl = "https://us-central1-brep-playground.cloudfunctions.net/talent";
        //final String baseUrl = "http://localhost:8080/get";
        URI uri = new URI(baseUrl);

        Iterable<WeatherData> weatherData = weatherDataRepository.findAll();

        HttpEntity<String> request = new HttpEntity<>(weatherData.toString());

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri, request, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return "Request Successful " + responseEntity.getStatusCode();
        } else {
            return "Request Failed " + responseEntity.getStatusCode();
        }
    }
}
