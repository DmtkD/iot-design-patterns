package io.mtk.doc_and_design_pattern.lab2.service.impl;

import io.mtk.doc_and_design_pattern.lab2.data.ManagerDataGenerator;
import io.mtk.doc_and_design_pattern.lab2.data.TypeData;
import io.mtk.doc_and_design_pattern.lab2.exception.ModeratorNotFoundException;
import io.mtk.doc_and_design_pattern.lab2.model.Moderator;
import io.mtk.doc_and_design_pattern.lab2.repository.ModeratorRepository;
import io.mtk.doc_and_design_pattern.lab2.service.ModeratorService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModeratorServiceImpl implements ModeratorService {
    private final ModeratorRepository moderatorRepository;

    @Autowired
    public ModeratorServiceImpl(ModeratorRepository moderatorRepository) {
        this.moderatorRepository = moderatorRepository;
    }

    @Override
    public List<Moderator> findAll() {
        return moderatorRepository.findAll();
    }

    @Override
    public Moderator findById(Integer id) {
        return moderatorRepository.findById(id)
                .orElseThrow(() -> new ModeratorNotFoundException(id));
    }

    @Override
    @Transactional
    public Moderator create(Moderator moderator) {
        return moderatorRepository.save(moderator);
    }

    @Override
    @Transactional
    public void update(Integer id, Moderator uModerator) {
        Moderator moderator = moderatorRepository.findById(id)
                .orElseThrow(() -> new ModeratorNotFoundException(id));

        moderator.setName(uModerator.getName());
        moderator.setSurname(uModerator.getSurname());
        moderator.setModeratorName(uModerator.getModeratorName());
        moderator.setPosition(uModerator.getPosition());
        moderator.setEmail(uModerator.getEmail());
        moderator.setPassword(uModerator.getPassword());

        moderatorRepository.save(moderator);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Moderator moderator = moderatorRepository.findById(id)
                .orElseThrow(() -> new ModeratorNotFoundException(id));
        moderatorRepository.delete(moderator);
    }

    @PostConstruct
    @Async
    @Transactional
    public void writeGeneratedDataToDataBase() {
        if (moderatorRepository.findById(1).isEmpty()) {
            List<List<String>> data = ManagerDataGenerator.getGeneratedDataByType(TypeData.MODERATOR);
            for (List<String> entity : data) {
                moderatorRepository.save(Moderator.builder()
                        .id(Integer.parseInt(entity.get(0)))
                        .name(entity.get(1))
                        .surname(entity.get(2))
                        .moderatorName(entity.get(3))
                        .position(entity.get(4))
                        .email(entity.get(5))
                        .password(entity.get(6))
                        .build());
            }
        }
    }
}