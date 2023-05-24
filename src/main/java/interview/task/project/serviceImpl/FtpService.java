package interview.task.project.serviceImpl;

import interview.task.project.dto.DataDTO;
import interview.task.project.service.IFtpService;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FtpService implements IFtpService {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Override
    public InputStream downloadFile(String fileName) throws Exception {
        return producerTemplate.requestBodyAndHeader("direct:retrieveFile", null, "fileName", fileName, InputStream.class);
    }

    @Override
    public void uploadXmlFile(DataDTO dataDTO) {
        String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("_ddMMyyyy_HH_mm"));
        producerTemplate.requestBodyAndHeader("direct:saveFile", dataDTO, "fileName", dataDTO.getCountry().concat(formattedDateTime).concat(".xml"), String.class);
    }

}
