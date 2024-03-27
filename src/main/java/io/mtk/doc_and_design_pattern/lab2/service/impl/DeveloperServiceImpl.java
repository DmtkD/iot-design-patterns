package io.mtk.doc_and_design_pattern.lab2.service.impl;

import io.mtk.doc_and_design_pattern.lab2.data.ManagerDataGenerator;
import io.mtk.doc_and_design_pattern.lab2.data.TypeData;
import io.mtk.doc_and_design_pattern.lab2.exception.DeveloperNotFoundException;
import io.mtk.doc_and_design_pattern.lab2.model.App;
import io.mtk.doc_and_design_pattern.lab2.model.Developer;
import io.mtk.doc_and_design_pattern.lab2.repository.AppRepository;
import io.mtk.doc_and_design_pattern.lab2.repository.DeveloperRepository;
import io.mtk.doc_and_design_pattern.lab2.service.DeveloperService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperServiceImpl implements DeveloperService {
    private final DeveloperRepository developerRepository;
    private final AppRepository appRepository;

    @Autowired
    public DeveloperServiceImpl(DeveloperRepository developerRepository, AppRepository appRepository) {
        this.developerRepository = developerRepository;
        this.appRepository = appRepository;
    }

    @Override
    public List<Developer> findAll() {
        return developerRepository.findAll();
    }

    @Override
    public Developer findById(Integer id) {
        return developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException(id));
    }

    @Override
    @Transactional
    public Developer create(Developer developer) {
        return developerRepository.save(developer);
    }

    @Override
    @Transactional
    public void update(Integer id, Developer uDeveloper) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException(id));

        developer.setName(uDeveloper.getName());
        developer.setSurname(uDeveloper.getSurname());
        developer.setBankCard(uDeveloper.getBankCard());
        developer.setNameOfCompany(uDeveloper.getNameOfCompany());
        developer.setEmail(uDeveloper.getEmail());
        developer.setPassword(uDeveloper.getPassword());

        developerRepository.save(developer);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new DeveloperNotFoundException(id));
        developer.getPublishedApps().clear();
        developerRepository.save(developer);
    }

    @PostConstruct
    @Transactional
    @Async(value = "writeDeveloperToDataBase")
    protected void writeGeneratedDataToDataBase() {
        if (developerRepository.findById(1).isEmpty()) {
            List<List<String>> data = ManagerDataGenerator.getGeneratedDataByType(TypeData.DEVELOPER);
            for (List<String> entity : data) {
                developerRepository.save(Developer.builder()
                        .id(Integer.parseInt(entity.get(0)))
                        .name(entity.get(1))
                        .surname(entity.get(2))
                        .bankCard(entity.get(3))
                        .nameOfCompany(entity.get(4))
                        .email(entity.get(5))
                        .password(entity.get(6))
                        .build());
            }
        }
    }

    @PostConstruct
    @Async(value = "writeDeveloperListAppToDataBase")
    @Transactional
    @DependsOn({"AppServiceImpl", "writeDeveloperToDataBase", "writeUserListAppToDataBase"})
    protected void writeDockTable() {
        List<Developer> developersList = findAll();
        List<App> appsList = appRepository.findAll();

        List<List<String>> data = ManagerDataGenerator.getGeneratedDataByType(TypeData.PUBLISHED_APPS);

        for (List<String> entity : data) {
            Integer developerId = Integer.parseInt(entity.get(0));
            Integer appId = Integer.parseInt(entity.get(1));

            Developer currentDeveloper = developersList
                    .stream()
                    .filter((o) -> o.getId().equals(developerId))
                    .toList()
                    .getFirst();

            App currentApp = appsList
                    .stream()
                    .filter((o) -> o.getId().equals(appId))
                    .toList()
                    .getFirst();

            currentDeveloper.getPublishedApps().add(currentApp);
        }

        developerRepository.saveAll(developersList);
    }
}