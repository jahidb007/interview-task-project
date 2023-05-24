package interview.task.project.serviceImpl;

import interview.task.project.service.HotelInformationService;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class HotelInformationServiceImpl implements HotelInformationService {
    @Autowired
    private ProducerTemplate producerTemplate;

    public JSONObject getHotelInformationByCity(String city) throws IOException {
        String response =   producerTemplate.requestBodyAndHeader("direct:hotelData", null, "cityName",city, String.class);
        JSONObject jsonObject = new JSONObject(response);
        jsonObject = extractThreeHotelInformation(jsonObject);
        return jsonObject;

    }

    private JSONObject extractThreeHotelInformation(JSONObject jsonObject) {
        int extractedHotelInfo = 0;
        JSONObject result = new JSONObject();
        JSONArray hotelInfoArray = new JSONArray();
        JSONArray resultArray = jsonObject.getJSONArray("sr");
        for(Object obj : resultArray){
            if(extractedHotelInfo==3) break;
            JSONObject data = (JSONObject) obj;
            if(data.get("type").equals("HOTEL")){
                extractedHotelInfo++;
                hotelInfoArray.put(data);
            }
        }
        result.put("hotels",hotelInfoArray);
        return result;
    }


}
