package Oleksandr.Turchanovskyi.repository;

import Oleksandr.Turchanovskyi.model.WeatherData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherDataRepository extends MongoRepository<WeatherData, String> {
    Optional<WeatherData> findByTimestamp(int timestamp);
}
