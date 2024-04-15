package io.mtk.doc_and_design_pattern.lab3.controller;

import io.mtk.doc_and_design_pattern.lab3.model.Moderator;
import io.mtk.doc_and_design_pattern.lab3.service.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/moderator")
public class ModeratorController {
    private final ModeratorService moderatorService;

    @Autowired
    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @GetMapping(value = "")
    public String getAllModerators(Model model) {
        model.addAttribute("moderators", moderatorService.findAll());
        return "index_moderator";
    }

    // ADD NEW MODERATOR
    @GetMapping(value = "/showNewModeratorForm")
    public String showNewModeratorForm(Model model) {
        model.addAttribute("moderator", new Moderator());
        return "new_moderator";
    }

    @PostMapping(value = "")
    public String addModerator(@RequestBody @ModelAttribute("moderator") Moderator moderator) {
        moderatorService.create(moderator);
        return "redirect:/api/moderator";
    }

    @GetMapping(value = "/showFormForUpdate/{moderatorId}")
    public String showFormForUpdate(Model model, @PathVariable("moderatorId") Integer moderatorId) {
        Moderator moderator = moderatorService.findById(moderatorId);
        model.addAttribute("moderator", moderator);
        return "update_moderator";
    }

    @PutMapping(value = "")
    public String updateModerator(@RequestBody @ModelAttribute("moderator") Moderator moderator) {
        moderatorService.update(moderator.getId(), moderator);
        return "redirect:/api/moderator";
    }

    @DeleteMapping(value = "/{moderatorId}")
    public String deleteModerator(@PathVariable("moderatorId") Integer moderatorId) {
        moderatorService.delete(moderatorId);
        return "redirect:/api/moderator";
    }
}