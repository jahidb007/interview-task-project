package interview.task.project.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import interview.task.project.dto.DataDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CsvParser {
    public List<DataDTO> parseCsvData(InputStream inputStream) throws IOException, IOException, CsvException, ParseException {

        List<DataDTO> dataDTOList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "Windows-1252"));
             CSVReader csvReader = new CSVReader(reader)) {

            String[] headers = csvReader.readNext(); // Skip header if needed
            String[] line;

            while ((line = csvReader.readNext()) != null) {
                // Assuming the CSV structure is: country, city, date
                try {
                    String country = line[0];
                    String city = line[1];
                    String csvDate = line[2];
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
                    Date date = dateFormat.parse(csvDate);
                    DataDTO dataDTO = new DataDTO(country, city, date);
                    dataDTOList.add(dataDTO);
                } catch (Exception e) {

                }
            }
        }
        return dataDTOList;
    }
}
