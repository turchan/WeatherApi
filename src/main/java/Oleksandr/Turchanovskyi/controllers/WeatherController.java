package Oleksandr.Turchanovskyi.controllers;

import Oleksandr.Turchanovskyi.model.WeatherData;
import Oleksandr.Turchanovskyi.serviceImpl.WeatherDataServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Slf4j
@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherDataServiceImpl weatherDataService;

    public WeatherController(WeatherDataServiceImpl weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    @GetMapping
    public Iterable<WeatherData> fetchWeather() {
        return weatherDataService.findAll();
    }

    @Scheduled(cron = "0 0 1 * * *", zone = "Europe/Warsaw")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void getWeather() throws JsonProcessingException {

        weatherDataService.save();

        log.info("The Weather in Krakow was updated");
    }

    @PostMapping(value = "/send")
    public String sendJSON() throws URISyntaxException {
        return weatherDataService.send();
    }
}
