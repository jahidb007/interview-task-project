package interview.task.project.controller;

import interview.task.project.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InformationController {
    @Autowired
    InformationService informationService;

    @GetMapping("/generate-information")
    public ResponseEntity<?> generateInformation() throws Exception {
        informationService.generateInformationFromCSV();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
