package interview.task.project.service;

import interview.task.project.dto.DataDTO;

import java.io.InputStream;

public interface IFtpService {
    InputStream downloadFile(String fileName) throws Exception;
    void uploadXmlFile(DataDTO dataDTO);
}
