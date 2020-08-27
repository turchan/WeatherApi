package Oleksandr.Turchanovskyi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("weatherdata")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WeatherData {

    @Id
    private String id;
    private String currentWeather;
    private double currentTemperature;
    private int timestamp;
    private String sunset;
    private String sunrise;

    public WeatherData(Builder builder) {
        this.id = builder.id;
        this.currentWeather = builder.currentWeather;
        this.currentTemperature = builder.currentTemperature;
        this.timestamp = builder.timestamp;
        this.sunset = builder.sunset;
        this.sunrise = builder.sunrise;
    }

    public static class Builder {
        private String id;
        private String currentWeather;
        private double currentTemperature;
        private int timestamp;
        private String sunset;
        private String sunrise;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setCurrentWeather(String currentWeather) {
            this.currentWeather = currentWeather;
            return this;
        }

        public Builder setCurrentTemperature(double currentTemperature) {
            this.currentTemperature = currentTemperature;
            return this;
        }

        public Builder setTimestamp(int timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setSunset(String sunset) {
            this.sunset = sunset;
            return this;
        }

        public Builder setSunrise(String sunrise) {
            this.sunrise = sunrise;
            return this;
        }

        public WeatherData build() {

            return new WeatherData(this);
        }
    }
}
