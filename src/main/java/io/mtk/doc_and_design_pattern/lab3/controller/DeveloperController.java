package io.mtk.doc_and_design_pattern.lab3.controller;

import io.mtk.doc_and_design_pattern.lab3.model.Developer;
import io.mtk.doc_and_design_pattern.lab3.service.DeveloperService;
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
@RequestMapping("/api/developer")
public class DeveloperController {
    private final DeveloperService developerService;

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping(value = "")
    public String getAllDevelopers(Model model) {
        model.addAttribute("developers", developerService.findAll());
        return "index_developer";
    }

    @GetMapping(value = "/showNewDeveloperForm")
    public String showNewDeveloperForm(Model model) {
        model.addAttribute("developer", new Developer());
        return "new_developer";
    }

    @PostMapping(value = "")
    public String addDeveloper(@RequestBody @ModelAttribute("developer") Developer developer) {
        developerService.create(developer);
        return "redirect:/api/developer";
    }

    @GetMapping(value = "/showFormForUpdate/{developerId}")
    public String showFormForUpdate(Model model, @PathVariable("developerId") Integer developerId) {
        Developer developer = developerService.findById(developerId);
        model.addAttribute("developer", developer);
        return "update_developer";
    }

    @PutMapping(value = "")
    public String updateDeveloper(@RequestBody @ModelAttribute("developer") Developer developer) {
        developerService.update(developer.getId(), developer);
        return "redirect:/api/developer";
    }

    @DeleteMapping(value = "/{developerId}")
    public String deleteDeveloper(@PathVariable("developerId") Integer developerId) {
        developerService.delete(developerId);
        return "redirect:/api/developer";
    }
}