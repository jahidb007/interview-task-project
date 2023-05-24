package interview.task.project.service;


import org.json.JSONObject;

public interface IWeatherInformationService {
    JSONObject getWeatherInformationByCity(String city);
}
