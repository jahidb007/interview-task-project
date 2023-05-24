package interview.task.project.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.json.JSONObject;

import java.util.Date;

@XStreamAlias("data")
public class DataDTO {
    private String country;
    @XStreamOmitField
    private String city;
    @XStreamOmitField
    private Date date;
    private JSONObject hotelInformation;
    private JSONObject weatherInformation;


    public DataDTO() {
    }

    public DataDTO(String country, String city, Date date) {
        this.country = country;
        this.city = city;
        this.date = date;
    }


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public JSONObject getHotelInformation() {
        return hotelInformation;
    }

    public void setHotelInformation(JSONObject hotelInformation) {
        this.hotelInformation = hotelInformation;
    }

    public JSONObject getWeatherInformation() {
        return weatherInformation;
    }

    public void setWeatherInformation(JSONObject weatherInformation) {
        this.weatherInformation = weatherInformation;
    }
}
