package interview.task.project.serviceImpl;

import interview.task.project.service.IWeatherInformationService;
import org.apache.camel.ProducerTemplate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherInformationService implements IWeatherInformationService {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Override
    public JSONObject getWeatherInformationByCity(String city) {
        String response = producerTemplate.requestBodyAndHeader("direct:weatherData", null, "cityName", city, String.class);
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject;
    }
}
