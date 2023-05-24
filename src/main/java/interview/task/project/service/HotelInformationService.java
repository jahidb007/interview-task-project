package interview.task.project.service;

import org.json.JSONObject;

import java.io.IOException;

public interface HotelInformationService {
    JSONObject getHotelInformationByCity(String city)throws IOException;
}
