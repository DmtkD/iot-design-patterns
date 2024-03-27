package io.mtk.doc_and_design_pattern.lab2.service.impl;

import io.mtk.doc_and_design_pattern.lab2.data.ManagerDataGenerator;
import io.mtk.doc_and_design_pattern.lab2.data.TypeData;
import io.mtk.doc_and_design_pattern.lab2.exception.AppNotFoundException;
import io.mtk.doc_and_design_pattern.lab2.exception.UpdateStatusNotFoundException;
import io.mtk.doc_and_design_pattern.lab2.model.App;
import io.mtk.doc_and_design_pattern.lab2.model.Type;
import io.mtk.doc_and_design_pattern.lab2.model.UpdateStatus;
import io.mtk.doc_and_design_pattern.lab2.repository.AppRepository;
import io.mtk.doc_and_design_pattern.lab2.repository.UpdateStatusRepository;
import io.mtk.doc_and_design_pattern.lab2.service.AppService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("AppServiceImpl")
@DependsOn("UpdateStatusServiceImpl")
public class AppServiceImpl implements AppService {
    private final AppRepository appRepository;
    private final UpdateStatusRepository updateStatusRepository;

    @Autowired
    public AppServiceImpl(AppRepository appRepository, UpdateStatusRepository updateStatusRepository) {
        this.appRepository = appRepository;
        this.updateStatusRepository = updateStatusRepository;
    }

    @Override
    public List<App> findAll() {
        return appRepository.findAll();
    }

    @Override
    public App findById(Integer id) {
        return appRepository.findById(id)
                .orElseThrow(() -> new AppNotFoundException(id));
    }

    @Override
    public App create(App app) {
        appRepository.save(app);
        return app;
    }

    @Override
    @Transactional
    public void update(Integer id, App uApp) {
        App app = appRepository.findById(id)
                .orElseThrow(() -> new AppNotFoundException(id));

        app.setName(uApp.getName());
        app.setType(uApp.getType());
        app.setRating(uApp.getRating());
        app.setVersion(uApp.getVersion());
        app.setLatestUpdateLog(uApp.getLatestUpdateLog());

        appRepository.save(app);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        App app = appRepository.findById(id)
                .orElseThrow(() -> new AppNotFoundException(id));
        appRepository.delete(app);
    }

    @PostConstruct
    @Async(value = "writeAppToDataBase")
    @Transactional
    protected void writeGeneratedDataToDataBase() {
        if (appRepository.findById(1).isEmpty()) {
            List<List<String>> data = ManagerDataGenerator.getGeneratedDataByType(TypeData.APP);
            Set<Integer> usedUpdateStatusIdList = new HashSet<>();
            UpdateStatus updateStatusLog;

            for (List<String> entity : data) {
                Integer updateStatusId = Integer.parseInt(entity.get(5));

                if (!usedUpdateStatusIdList.contains(updateStatusId)) {
                    updateStatusLog = updateStatusRepository.findById(updateStatusId)
                            .orElseThrow(() -> new UpdateStatusNotFoundException(updateStatusId));
                } else {
                    updateStatusLog = null;
                }

                appRepository.save(App.builder()
                        .id(Integer.parseInt(entity.get(0)))
                        .name(entity.get(1))
                        .type(Type.valueOf(entity.get(2)))
                        .rating(Float.parseFloat(entity.get(3)))
                        .version(entity.get(4))
                        .latestUpdateLog(updateStatusLog)
                        .build());

                usedUpdateStatusIdList.add(updateStatusId);
            }
        }
    }


    @PostConstruct
    @Async
    @Transactional
    @DependsOn({"UpdateStatusServiceImpl", "writeAppToDataBase", "writeDeveloperListAppToDataBase"})
    protected void writeDockTable() {
        List<App> appsList = findAll();
        List<UpdateStatus> updateStatusList = updateStatusRepository.findAll();

        List<List<String>> data = ManagerDataGenerator.getGeneratedDataByType(TypeData.UPDATE_STATUS_HISTORY);

        for (List<String> entity : data) {
            Integer appId = Integer.parseInt(entity.get(0));
            Integer UpdateStatusId = Integer.parseInt(entity.get(1));

            App currentApp = appsList
                    .stream()
                    .filter((o) -> o.getId().equals(appId))
                    .toList()
                    .getFirst();

            UpdateStatus currentUpdateStatus = updateStatusList
                    .stream()
                    .filter((o) -> o.getId().equals(UpdateStatusId))
                    .toList()
                    .getFirst();

            currentApp.getUpdateStatusHistory().add(currentUpdateStatus);
        }
        appRepository.saveAll(appsList);
    }
}