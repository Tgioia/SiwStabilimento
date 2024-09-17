package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Disponibilita;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.Proprietario;
import it.uniroma3.siw.model.Stabilimento;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.PrenotazioneRepository;
import it.uniroma3.siw.repository.StabilimentoRepository;
import it.uniroma3.siw.repository.UserRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.DisponibilitaService;
import it.uniroma3.siw.service.ProprietarioService;
import it.uniroma3.siw.service.StabilimentoService;
import it.uniroma3.siw.service.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

@Controller
public class StabilimentoController {
	private static String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @Autowired
    private StabilimentoService stabilimentoService;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private UserService userService;
    @Autowired
    private DisponibilitaService disponibilitaService;
    @Autowired
    PrenotazioneRepository prenotazioneRepository;

    @Autowired
	private StabilimentoRepository stabilimentoRepository;

    @GetMapping(value="/stabilimenti")
    public String listStabilimenti(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
        System.out.println(" "+ credentials.getRole());
    	if(credentials.getRole().equals(Credentials.OWNER_ROLE)) {
    		Proprietario proprietario = credentials.getProprietario();
    		model.addAttribute("stabilimenti", stabilimentoService.findAllByProprietario(proprietario));
    		return "/owner/stabilimenti";
    	} else if(credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
    		model.addAttribute("stabilimenti", stabilimentoService.findAll());
    		return "admin/stabilimenti";
    	} else {    	
        model.addAttribute("stabilimenti", stabilimentoService.findAll());
        return "/stabilimenti";
    	}
    }
    


    @GetMapping("/stabilimento/{id}")
    public String viewStabilimento(@PathVariable("id") Long id, Model model) {
        Stabilimento stabilimento = stabilimentoService.findById(id).get();
        if (stabilimento != null) {
            Collections.sort(stabilimento.getDisponibilitaPerGiorno(), new Comparator<Disponibilita>() {
                @Override
                public int compare(Disponibilita d1, Disponibilita d2) {
                    return d1.getData().compareTo(d2.getData()); 
                }
            });
            model.addAttribute("stabilimento", stabilimento);
            return "stabilimento";
        }
        return "redirect:/stabilimenti";
    }

    
    @GetMapping("/admin/stabilimento/delete/{id}")
    public String deleteStabilimento(@PathVariable("id") Long id) {
        Stabilimento stabilimento = this.stabilimentoRepository.findById(id).get();
        for(Prenotazione prenotazione : prenotazioneRepository.findAllByStabilimento(stabilimento)) {
        	prenotazioneRepository.delete(prenotazione);
        }
        this.stabilimentoRepository.delete(stabilimento);
        return "redirect:/";
    }
    
    @PostMapping("/admin/stabilimento")
    public String newStabilimento(@Valid @ModelAttribute("stabilimento") Stabilimento stabilimento, 
                                  BindingResult bindingResult,
                                  @RequestParam("immagine") MultipartFile file,
                                  Model model) {
        if (!bindingResult.hasErrors()) {
        	//try,catch
        	if(!file.isEmpty()) {
        		try {
        			String uploadDir = UPLOAD_DIR;
        			File directory = new File(uploadDir);
        			if(!directory.exists()) directory.mkdirs();
        			byte[] bytes = file.getBytes();
        			Path path= Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        			Files.write(path, bytes);
        			stabilimento.setImagePath("/uploads/" + file.getOriginalFilename());
        			
        		} catch(IOException e) {
        			e.printStackTrace();
        		}
        	}
        	
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
    
    @GetMapping("/owner/formUpdateStabilimento/{id}")
    public String formUpdateStabilimento(@PathVariable("id") Long id, Model model) {
        Stabilimento stabilimento = stabilimentoRepository.findById(id).get();
        model.addAttribute("stabilimento", stabilimento);
        return "owner/formUpdateStabilimento.html";
    }
    @PostMapping("/owner/stabilimento/update")
    public String updateStabilimento(@Valid @ModelAttribute("stabilimento") Stabilimento stabilimento, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            this.stabilimentoRepository.save(stabilimento);
            return "redirect:/owner/stabilimenti";
        } else {
            return "owner/formUpdateStabilimento.html"; 
        }
    }


}
