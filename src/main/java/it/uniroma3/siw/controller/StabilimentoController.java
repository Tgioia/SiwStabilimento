package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Lettino;
import it.uniroma3.siw.model.Ombrellone;
import it.uniroma3.siw.model.Proprietario;
import it.uniroma3.siw.model.Stabilimento;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.LettinoRepository;
import it.uniroma3.siw.repository.OmbrelloneRepository;
import it.uniroma3.siw.repository.StabilimentoRepository;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.DisponibilitaService;
import it.uniroma3.siw.service.ProprietarioService;
import it.uniroma3.siw.service.StabilimentoService;
import it.uniroma3.siw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class StabilimentoController {

    @Autowired
    private StabilimentoService stabilimentoService;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private UserService userService;
    @Autowired
    private DisponibilitaService disponibilitaService;

    @Autowired
	private StabilimentoRepository stabilimentoRepository;
    @Autowired
	private LettinoRepository lettinoRepository;
    @Autowired
	private OmbrelloneRepository ombrelloneRepository;
    
    @GetMapping(value="/stabilimenti")
    public String listStabilimenti(Model model) {
        model.addAttribute("stabilimenti", stabilimentoService.findAll());
        return "stabilimenti";
    }
    @GetMapping(value="owner/stabilimenti")
    public String listStabilimentiOwner(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());

        Proprietario proprietario = credentials.getProprietario();
        model.addAttribute("stabilimenti", stabilimentoService.findAllByProprietario(proprietario));
        return "stabilimenti";
    }
    @GetMapping("/admin/stabilimenti")
    public String listStabilimentiForAdmin(Model model) {
        model.addAttribute("stabilimenti", stabilimentoService.findAll());
        return "admin/stabilimenti";
    }


    @GetMapping("/stabilimento/{id}")
    public String viewStabilimento(@PathVariable("id") Long id, Model model) {
        Stabilimento stabilimento = stabilimentoService.findById(id).get();
        if (stabilimento != null) {
            model.addAttribute("stabilimento", stabilimento);
            return "stabilimento";
        }
        return "redirect:/stabilimenti";
    }

    
    @GetMapping("/admin/stabilimento/delete/{id}")
    public String deleteStabilimento(@PathVariable("id") Long id) {
        Stabilimento stabilimento = this.stabilimentoRepository.findById(id).get();
        this.stabilimentoRepository.delete(stabilimento);
        return "redirect:/";
    }
    
    @PostMapping("/admin/stabilimento")
    public String newStabilimento(@Valid @ModelAttribute("stabilimento") Stabilimento stabilimento, 
                                  BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
        	System.out.print("1");
            this.stabilimentoRepository.save(stabilimento);
        	System.out.print("2");
        	disponibilitaService.inizializzaDisponibilita(stabilimento, stabilimento.getNumeroLettini(), stabilimento.getNumeroOmbrelloni());
            return "redirect:/";
        } else {
            return "admin/formNewStabilimento.html";
        }
    }


    @GetMapping("/admin/formNewStabilimento")
    public String formNewStabilimento(Model model) {
    	Stabilimento stabilimento = new Stabilimento();
    	model.addAttribute("proprietari", userService.findProprietari());
        model.addAttribute("stabilimento", stabilimento);
        
        return "admin/formNewStabilimento.html";
    }
}
