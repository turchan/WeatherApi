package Oleksandr.Turchanovskyi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    private String currentWeather;
    private double currentTemperature;
    @JsonProperty("timezone")
    private int timestamp;
    @JsonProperty("sunset")
    private String sunset;
    @JsonProperty("sunrise")
    private String sunrise;

    @JsonProperty("weather")
    public void setWeather(List<Map<String, Object>> weatherEntries) {
        Map<String, Object> weather = weatherEntries.get(0);
        setCurrentWeather((String) weather.get("main"));
    }

    @JsonProperty("main")
    public void setTemperature(Map<String, Object> main) {
        setCurrentTemperature((Double) main.get("temp"));
    }

    public void setSunset(int sunset) {
        Date date = new Date(sunset*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));

        this.sunset = sdf.format(date);
    }

    public void setSunrise(int sunrise) {
        Date date = new Date(sunrise*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));

        this.sunrise = sdf.format(date);
    }

    @JsonProperty("sys")
    public void setSys(Map<String, Object> sys) {
        setSunrise((Integer) sys.get("sunrise"));
        setSunset((Integer) sys.get("sunset"));
    }
}
