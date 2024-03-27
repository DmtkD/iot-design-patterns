package io.mtk.doc_and_design_pattern.lab2.service.impl;

import io.mtk.doc_and_design_pattern.lab2.data.ManagerDataGenerator;
import io.mtk.doc_and_design_pattern.lab2.data.TypeData;
import io.mtk.doc_and_design_pattern.lab2.exception.UserNotFoundException;
import io.mtk.doc_and_design_pattern.lab2.model.App;
import io.mtk.doc_and_design_pattern.lab2.model.User;
import io.mtk.doc_and_design_pattern.lab2.repository.AppRepository;
import io.mtk.doc_and_design_pattern.lab2.repository.UserRepository;
import io.mtk.doc_and_design_pattern.lab2.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "UserServiceImpl")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AppRepository appRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AppRepository appRepository) {
        this.userRepository = userRepository;
        this.appRepository = appRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void update(Integer id, User uUser) {
        User User = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        User.setName(uUser.getName());
        User.setSurname(uUser.getSurname());
        User.setUsername(uUser.getUsername());
        User.setEmail(uUser.getEmail());
        User.setPassword(uUser.getPassword());

        userRepository.save(User);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.getWishedApps().clear();
        user.getInstallApps().clear();
        userRepository.delete(user);
    }

    @PostConstruct
    @Async(value = "writeUserToDataBase")
    @Transactional
    protected void writeGeneratedDataToDataBase() {
        if (userRepository.findById(1).isEmpty()) {
            List<List<String>> data = ManagerDataGenerator.getGeneratedDataByType(TypeData.USER);
            for (List<String> entity : data) {
                userRepository.save(User.builder()
                        .id(Integer.parseInt(entity.get(0)))
                        .name(entity.get(1))
                        .surname(entity.get(2))
                        .username(entity.get(3))
                        .email(entity.get(4))
                        .password(entity.get(5))
                        .build());
            }
        }
    }

    private void addAppToSomeList(List<List<String>> generatedData,
                                  List<User> usersList,
                                  List<App> appsList,
                                  TypeData typeData) {
        for (List<String> entity : generatedData) {
            Integer userId = Integer.parseInt(entity.get(0));
            Integer appId = Integer.parseInt(entity.get(1));

            User currentUser = usersList
                    .stream()
                    .filter((o) -> o.getId().equals(userId))
                    .toList()
                    .getFirst();

            App currentApp = appsList
                    .stream()
                    .filter((o) -> o.getId().equals(appId))
                    .toList()
                    .getFirst();

            switch (typeData) {
                case INSTALL_APPS -> currentUser.getInstallApps().add(currentApp);
                case WISHED_APPS -> currentUser.getWishedApps().add(currentApp);
            }
        }
    }

    @PostConstruct
    @Async(value = "writeUserListAppToDataBase")
    @DependsOn({"AppServiceImpl", "writeUserToDataBase"})
    protected void writeDockTable() {
        List<User> usersList = findAll();
        List<App> appsList = appRepository.findAll();

        List<List<String>> installAppsData = ManagerDataGenerator.getGeneratedDataByType(TypeData.INSTALL_APPS);
        List<List<String>> wishedAppsData = ManagerDataGenerator.getGeneratedDataByType(TypeData.WISHED_APPS);

        addAppToSomeList(installAppsData, usersList, appsList, TypeData.INSTALL_APPS);
        addAppToSomeList(wishedAppsData, usersList, appsList, TypeData.WISHED_APPS);

        userRepository.saveAll(usersList);
    }
}