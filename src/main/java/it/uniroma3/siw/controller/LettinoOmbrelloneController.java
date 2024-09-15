package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Lettino;
import it.uniroma3.siw.model.Ombrellone;
import it.uniroma3.siw.service.LettinoOmbrelloneService;
import jakarta.validation.Valid;

@Controller
public class LettinoOmbrelloneController {

    @Autowired
    private LettinoOmbrelloneService lettinoOmbrelloneService;

    @GetMapping("/lettino/new")
    public String showCreateLettinoForm(Model model) {
        model.addAttribute("lettino", new Lettino());
        return "lettino/formCreateLettino";
    }

    @PostMapping("/lettino/new")
    public String createLettino(@Valid @ModelAttribute("lettino") Lettino lettino,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "lettino/formCreateLettino";
        }
        lettinoOmbrelloneService.saveLettino(lettino);
        return "lettino/success";
    }

    @GetMapping("/ombrellone/new")
    public String showCreateOmbrelloneForm(Model model) {
        model.addAttribute("ombrellone", new Ombrellone());
        return "ombrellone/formCreateOmbrellone";
    }

    @PostMapping("/ombrellone/new")
    public String createOmbrellone(@Valid @ModelAttribute("ombrellone") Ombrellone ombrellone,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "ombrellone/formCreateOmbrellone";
        }
        lettinoOmbrelloneService.saveOmbrellone(ombrellone);
        return "ombrellone/success";
    }

    // Metodi per cancellare i lettini e ombrelloni

    @PostMapping("/lettino/delete")
    public String deleteLettino(@ModelAttribute("lettino") Lettino lettino) {
        lettinoOmbrelloneService.deleteLettino(lettino);
        return "lettino/deleted";
    }

    @PostMapping("/ombrellone/delete")
    public String deleteOmbrellone(@ModelAttribute("ombrellone") Ombrellone ombrellone) {
        lettinoOmbrelloneService.deleteOmbrellone(ombrellone);
        return "ombrellone/deleted";
    }
}
