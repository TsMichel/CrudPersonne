package com.example.demo.controller;
import com.example.demo.model.Personne;
import com.example.demo.service.PersonneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonneController {
    @Autowired
    private PersonneService personneService;

    @GetMapping("/")
    public String listPersonnes(Model model, @RequestParam(required = false) String search,
                                @RequestParam(required = false) String sort) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("personnes", personneService.searchByNom(search));
        } else if (sort != null) {
            if (sort.equals("nom")) {
                model.addAttribute("personnes", personneService.sortByNom());
            } else if (sort.equals("salaire")) {
                model.addAttribute("personnes", personneService.sortBySalaire());
            }
        } else {
            model.addAttribute("personnes", personneService.getAllPersonnes());
        }
        model.addAttribute("personne", new Personne());
        model.addAttribute("isEdit", false);
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String editPersonne(@PathVariable Long id, Model model) {
        model.addAttribute("personne", personneService.getPersonneById(id));
        model.addAttribute("personnes", personneService.getAllPersonnes());
        model.addAttribute("isEdit", true);
        return "index";
    }

    @PostMapping("/save")
    public String savePersonne(@ModelAttribute Personne personne) {
        personneService.savePersonne(personne);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePersonne(@PathVariable Long id) {
        personneService.deletePersonne(id);
        return "redirect:/";
    }
}