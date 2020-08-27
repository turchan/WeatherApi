package Oleksandr.Turchanovskyi.controllers;

import Oleksandr.Turchanovskyi.model.WeatherData;
import Oleksandr.Turchanovskyi.serviceImpl.WeatherDataServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Slf4j
@Controller
@RequestMapping("/")
public class WeatherController {

    private final WeatherDataServiceImpl weatherDataService;

    public WeatherController(WeatherDataServiceImpl weatherDataService) {
        this.weatherDataService = weatherDataService;
    }

    @GetMapping
    public String fetchWeather(Model model) {

        Iterable<WeatherData> weatherData = weatherDataService.findAll();

        model.addAttribute("weatherData", weatherData);

        return "weather";
    }

    @Scheduled(cron = "0 0 12 * * *", zone = "Europe/Warsaw")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void getWeather() throws JsonProcessingException {

        weatherDataService.save();

        log.info("The Weather in Krakow was updated");
    }

    @PostMapping("/send")
    public String sendJSON(Model model) throws URISyntaxException {

        String response = weatherDataService.send();

        model.addAttribute("response", response);

        return "response";
    }

    @PostMapping("/get")
    public String testGetJSON(Model model) throws JsonProcessingException {

        WeatherData request = weatherDataService.save();

        model.addAttribute("response", request);

        return "response";
    }
}
