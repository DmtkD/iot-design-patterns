package io.mtk.doc_and_design_pattern.lab2.controller;

import io.mtk.doc_and_design_pattern.lab2.model.Moderator;
import io.mtk.doc_and_design_pattern.lab2.service.ModeratorService;
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
@RequestMapping("/api/moderator")
public class ModeratorController {
    private final ModeratorService moderatorService;

    @Autowired
    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @GetMapping(value = "/{moderatorId}")
    public ResponseEntity<Moderator> getModeratorById(@PathVariable("moderatorId") Integer moderatorId) {
        Moderator moderator = moderatorService.findById(moderatorId);
        return new ResponseEntity<>(moderator, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public ResponseEntity<List<Moderator>> getAllModerators() {
        List<Moderator> moderators = moderatorService.findAll();
        return new ResponseEntity<>(moderators, HttpStatus.OK);
    }

    @PostMapping(value = "")
    public ResponseEntity<Moderator> addModerator(@RequestBody Moderator Moderator) {
        Moderator newModerator = moderatorService.create(Moderator);
        return new ResponseEntity<>(newModerator, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{moderatorId}")
    public ResponseEntity<?> updateModerator(@RequestBody Moderator uModerator,
                                             @PathVariable("moderatorId") Integer moderatorId) {
        moderatorService.update(moderatorId, uModerator);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{moderatorId}")
    public ResponseEntity<?> deleteModerator(@PathVariable("moderatorId") Integer moderatorId) {
        moderatorService.delete(moderatorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}