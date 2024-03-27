package io.mtk.doc_and_design_pattern.lab2.controller;

import io.mtk.doc_and_design_pattern.lab2.model.App;
import io.mtk.doc_and_design_pattern.lab2.service.AppService;
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
@RequestMapping("/api/app")
public class AppController {
    private final AppService appService;

    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping(value = "/{appId}")
    public ResponseEntity<App> getAppById(@PathVariable("appId") Integer appId) {
        App app = appService.findById(appId);
        return new ResponseEntity<>(App.builder()
                .id(app.getId())
                .name(app.getName())
                .type(app.getType())
                .version(app.getVersion())
                .rating(app.getRating())
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<App>> getAllApps() {
        List<App> apps = appService.findAll();
        apps.forEach((app) -> {
            App.builder()
                    .id(app.getId())
                    .name(app.getName())
                    .type(app.getType())
                    .version(app.getVersion())
                    .rating(app.getRating())
                    .build();
        });

        return new ResponseEntity<>(apps, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<App> addApp(@RequestBody App app) {
        App newApp = appService.create(app);
        return new ResponseEntity<>(App.builder()
                .id(newApp.getId())
                .name(newApp.getName())
                .type(newApp.getType())
                .version(newApp.getVersion())
                .rating(newApp.getRating())
                .build(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{appId}")
    public ResponseEntity<?> updateApp(@RequestBody App uApp, @PathVariable("appId") Integer appId) {
        appService.update(appId, uApp);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{appId}")
    public ResponseEntity<?> deleteApp(@PathVariable("appId") Integer appId) {
        appService.delete(appId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}