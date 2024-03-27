package io.mtk.doc_and_design_pattern.lab2.controller;

import io.mtk.doc_and_design_pattern.lab2.model.UpdateStatus;
import io.mtk.doc_and_design_pattern.lab2.service.UpdateStatusService;
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
@RequestMapping("/api/update-status")
public class UpdateStatusController {
    private final UpdateStatusService updateStatusService;
    
    @Autowired
    public UpdateStatusController(UpdateStatusService updateStatusService) {
        this.updateStatusService = updateStatusService;
    }

    @GetMapping(value = "/{updateStatusId}")
    public ResponseEntity<UpdateStatus> getUpdateStatusById(@PathVariable("updateStatusId") Integer updateStatusId) {
        UpdateStatus updateStatus = updateStatusService.findById(updateStatusId);
        return new ResponseEntity<>(UpdateStatus.builder()
                .id(updateStatus.getId())
                .updateLog(updateStatus.getUpdateLog())
                .fixVersion(updateStatus.getFixVersion())
                .date(updateStatus.getDate())
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<UpdateStatus>> getAllUpdateStatuses() {
        List<UpdateStatus> updateStatuses = updateStatusService.findAll();
        updateStatuses.forEach((updateStatus) -> {
            UpdateStatus.builder()
                    .id(updateStatus.getId())
                    .updateLog(updateStatus.getUpdateLog())
                    .fixVersion(updateStatus.getFixVersion())
                    .date(updateStatus.getDate())
                    .build();
        });

        return new ResponseEntity<>(updateStatuses, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<UpdateStatus> addUpdateStatus(@RequestBody UpdateStatus updateStatus) {
        UpdateStatus newUpdateStatus = updateStatusService.create(updateStatus);
        return new ResponseEntity<>(UpdateStatus.builder()
                .id(newUpdateStatus.getId())
                .updateLog(newUpdateStatus.getUpdateLog())
                .fixVersion(newUpdateStatus.getFixVersion())
                .date(newUpdateStatus.getDate())
                .build(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{updateStatusId}")
    public ResponseEntity<?> updateUpdateStatus(@RequestBody UpdateStatus uUpdateStatus,
                                                @PathVariable("updateStatusId") Integer updateStatusId) {
        updateStatusService.update(updateStatusId, uUpdateStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{updateStatusId}")
    public ResponseEntity<?> deleteUpdateStatus(@PathVariable("updateStatusId") Integer updateStatusId) {
        updateStatusService.delete(updateStatusId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}