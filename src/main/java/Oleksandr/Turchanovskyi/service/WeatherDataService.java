package Oleksandr.Turchanovskyi.service;

import Oleksandr.Turchanovskyi.model.WeatherData;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.URISyntaxException;
import java.util.Optional;

public interface WeatherDataService {
    Iterable<WeatherData> findAll();
    Optional<WeatherData> findByTimeStamp(int timestamp);
    WeatherData save() throws JsonProcessingException;
    String send() throws URISyntaxException, JsonProcessingException;
}
