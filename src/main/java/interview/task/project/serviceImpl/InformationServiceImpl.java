package interview.task.project.serviceImpl;

import interview.task.project.dto.DataDTO;
import interview.task.project.service.HotelInformationService;
import interview.task.project.service.IFtpService;
import interview.task.project.service.IWeatherInformationService;
import interview.task.project.service.InformationService;
import interview.task.project.util.CsvParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class InformationServiceImpl implements InformationService {

    @Autowired
    IFtpService ftpService;

    @Autowired
    HotelInformationService hotelInformationService;

    @Autowired
    IWeatherInformationService weatherService;

    @Override
    public void generateInformationFromCSV() throws Exception {
        InputStream inputStream = ftpService.downloadFile("data.csv");
        CsvParser csvParser = new CsvParser();
        List<DataDTO> dataDTOList = csvParser.parseCsvData(inputStream);
        for (DataDTO dataDTO : dataDTOList) {
            try {
                JSONObject hotelInformation = hotelInformationService.getHotelInformationByCity(dataDTO.getCity());
                dataDTO.setHotelInformation(hotelInformation);
            } catch (Exception e) {

            }
            try {
                JSONObject weatherInformation = weatherService.getWeatherInformationByCity(dataDTO.getCity());
                dataDTO.setWeatherInformation(weatherInformation);
            } catch (Exception e) {

            }
            ftpService.uploadXmlFile(dataDTO);
        }

    }


    @Scheduled(cron = "0 0 6 * * *")
    public void generateInformationAtSpecificTime() throws Exception {
       generateInformationFromCSV();
    }

    @Scheduled(cron = "0 0 */6 * * *")
    public void generateInformationEverySixHour() throws Exception {
        generateInformationFromCSV();
    }

}
