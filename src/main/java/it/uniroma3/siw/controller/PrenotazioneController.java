package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Disponibilita;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.DisponibilitaService;
import it.uniroma3.siw.service.PrenotazioneService;
import it.uniroma3.siw.service.StabilimentoService;
import jakarta.validation.Valid;

@Controller
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private StabilimentoService stabilimentoService;
    @Autowired
    private DisponibilitaService disponibilitaService;
    
    @GetMapping("/prenotazione/new")
    public String showCreatePrenotazioneForm(Model model) {
        model.addAttribute("prenotazione", new Prenotazione());
        model.addAttribute("stabilimenti", stabilimentoService.findAll());
        return "/formNewPrenotazione";
    }

    @PostMapping("/prenotazione/new")
    public String createPrenotazione(@Valid @ModelAttribute("prenotazione") Prenotazione prenotazione,
                                     BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
        	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        	UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());

        	User user = credentials.getUser();
        	prenotazione.setUser(user);
        	
        	Disponibilita disponibilita = disponibilitaService.findByStabilimentoAndData(prenotazione.getStabilimento(),prenotazione.getData()).get(0);

        	if (disponibilita.getLettiniDisponibili() >= prenotazione.getnumeroLettini() && disponibilita.getOmbrelloniDisponibili() >= prenotazione.getnumeroOmbrelloni()) {
        		disponibilita.setLettiniDisponibili(disponibilita.getLettiniDisponibili() - prenotazione.getnumeroLettini());
        		disponibilita.setOmbrelloniDisponibili(disponibilita.getOmbrelloniDisponibili() - prenotazione.getnumeroOmbrelloni());
        		prenotazioneService.savePrenotazione(prenotazione);
        		disponibilitaService.save(disponibilita);
        	} else return "/nonDisponibili";
    	}else return "formNewPrenotazione";
    	
        return "redirect:/prenotazione/" + prenotazione.getId();
    }
    @GetMapping("/prenotazioni")
    public String getAllPrenotazioni(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());

        User user = credentials.getUser();
        Iterable<Prenotazione> prenotazioni = prenotazioneService.findByUser(user);
        model.addAttribute("prenotazioni", prenotazioni);
        return "prenotazioni";
    }
    @GetMapping("/prenotazione/{id}")
    public String getPrenotazione(@PathVariable("id") Long id, Model model) {
        Prenotazione prenotazione = prenotazioneService.findById(id).get();
        
        if (prenotazione != null) {
            model.addAttribute("prenotazione", prenotazione);
            return "prenotazione";
        } else {
            return "redirect:/prenotazioni";
        }
    }
    
}
