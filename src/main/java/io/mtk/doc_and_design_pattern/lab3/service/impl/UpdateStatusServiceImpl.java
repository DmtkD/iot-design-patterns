package io.mtk.doc_and_design_pattern.lab3.service.impl;

import io.mtk.doc_and_design_pattern.lab3.data.ManagerDataGenerator;
import io.mtk.doc_and_design_pattern.lab3.data.TypeData;
import io.mtk.doc_and_design_pattern.lab3.exception.UpdateStatusNotFoundException;
import io.mtk.doc_and_design_pattern.lab3.model.UpdateStatus;
import io.mtk.doc_and_design_pattern.lab3.repository.UpdateStatusRepository;
import io.mtk.doc_and_design_pattern.lab3.service.UpdateStatusService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("UpdateStatusServiceImpl")
public class UpdateStatusServiceImpl implements UpdateStatusService {
    private final UpdateStatusRepository updateStatusRepository;

    @Autowired
    public UpdateStatusServiceImpl(UpdateStatusRepository updateStatusRepository) {
        this.updateStatusRepository = updateStatusRepository;
    }

    @Override
    public List<UpdateStatus> findAll() {
        return updateStatusRepository.findAll();
    }

    @Override
    public UpdateStatus findById(Integer id) {
        return updateStatusRepository.findById(id)
                .orElseThrow(() -> new UpdateStatusNotFoundException(id));
    }

    @Override
    @Transactional
    public UpdateStatus create(UpdateStatus updateStatus) {
        return updateStatusRepository.save(updateStatus);
    }

    @Override
    @Transactional
    public void update(Integer id, UpdateStatus uUpdateStatus) {
        UpdateStatus updateStatus = updateStatusRepository.findById(id)
                .orElseThrow(() -> new UpdateStatusNotFoundException(id));

        updateStatus.setUpdateLog(uUpdateStatus.getUpdateLog());
        updateStatus.setDate(uUpdateStatus.getDate());
        updateStatus.setFixVersion(uUpdateStatus.getFixVersion());

        updateStatusRepository.save(updateStatus);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        UpdateStatus updateStatus = updateStatusRepository.findById(id)
                .orElseThrow(() -> new UpdateStatusNotFoundException(id));
        updateStatusRepository.delete(updateStatus);
    }

    @PostConstruct
    @Async
    @Transactional
    protected void writeGeneratedDataToDataBase() {
        if (updateStatusRepository.findById(1).isEmpty()) {
            List<List<String>> data = ManagerDataGenerator.getGeneratedDataByType(TypeData.UPDATE_STATUS);
            for (List<String> entity : data) {
                updateStatusRepository.save(UpdateStatus.builder()
                        .id(Integer.parseInt(entity.get(0)))
                        .date(LocalDate.parse(entity.get(1)))
                        .fixVersion(entity.get(2))
                        .updateLog(entity.get(3))
                        .build());
            }
        }
    }
}