package io.mtk.doc_and_design_pattern.lab2.controller;

import io.mtk.doc_and_design_pattern.lab2.model.Developer;
import io.mtk.doc_and_design_pattern.lab2.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/developer")
public class DeveloperController {
    private final DeveloperService developerService;

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping(value = "/{developerId}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable("developerId") Integer developerId) {
        Developer developer = developerService.findById(developerId);
        return new ResponseEntity<>(Developer.builder()
                .id(developer.getId())
                .name(developer.getName())
                .surname(developer.getSurname())
                .bankCard(developer.getBankCard())
                .nameOfCompany(developer.getNameOfCompany())
                .email(developer.getEmail())
                .password(developer.getPassword())
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Developer>> getAllDevelopers() {
        List<Developer> developers = developerService.findAll();
        developers.forEach((developer) -> {
            Developer.builder()
                    .id(developer.getId())
                    .name(developer.getName())
                    .surname(developer.getSurname())
                    .bankCard(developer.getBankCard())
                    .nameOfCompany(developer.getNameOfCompany())
                    .email(developer.getEmail())
                    .password(developer.getPassword())
                    .build();
        });

        return new ResponseEntity<>(developers, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<Developer> addDeveloper(@RequestBody Developer developer) {
        Developer newDeveloper = developerService.create(developer);
        return new ResponseEntity<>(Developer.builder()
                .id(newDeveloper.getId())
                .name(newDeveloper.getName())
                .surname(newDeveloper.getSurname())
                .bankCard(newDeveloper.getBankCard())
                .nameOfCompany(newDeveloper.getNameOfCompany())
                .email(newDeveloper.getEmail())
                .password(newDeveloper.getPassword())
                .build(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{developerId}")
    public ResponseEntity<?> updateDeveloper(@RequestBody Developer uDeveloper,
                                             @PathVariable("developerId") Integer developerId) {
        developerService.update(developerId, uDeveloper);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{developerId}")
    public ResponseEntity<?> deleteDeveloper(@PathVariable("developerId") Integer developerId) {
        developerService.delete(developerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}