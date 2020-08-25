package Oleksandr.Turchanovskyi.config;

import Oleksandr.Turchanovskyi.model.WeatherURL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan
public class WeatherConfigurations {

    @Value("${weather.url}")
    private String url;

    @Value("${weather.apikey}")
    private String apikey;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setIgnoreUnresolvablePlaceholders(true);
        return c;
    }


    @Bean
    public WeatherURL weatherUrl() {
        WeatherURL weatherUrl = new WeatherURL();
        weatherUrl.setUrl(url);
        weatherUrl.setApiKey(apikey);
        return weatherUrl;
    }
}
