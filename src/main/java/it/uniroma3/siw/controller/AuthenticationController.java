package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Proprietario;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ProprietarioService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;
	@Autowired
	private CredentialsRepository credentialsRepository;

	@Autowired
	private ProprietarioService proprietarioService;
	
    @Autowired
	private UserService userService;
	
	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		return "formRegisterUser";
	}
	
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "formLogin";
	}

	@GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "index.html";
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				return "admin/indexAdmin.html";
			} else if(credentials.getRole().equals(credentials.OWNER_ROLE)) return "owner/indexProprietario";
		}
        return "index.html";
	}
		
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/indexAdmin";
        } else if(credentials.getRole().equals(Credentials.OWNER_ROLE)) return "owner/indexProprietario";
        return "index";
    }

	@PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute("user") User user,
                 BindingResult userBindingResult, @Valid
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {
        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            userService.saveUser(user);
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);
            return "registrationSuccessful";
        }
        return "registerUser";
    }
	
	@GetMapping(value = "/admin/formNewProprietario") 
	public String showRegisterProprietarioForm(Model model) {
	    model.addAttribute("proprietario", new Proprietario());
	    model.addAttribute("credentials", new Credentials());
	    return "admin/formNewProprietario";
	}

	
	@PostMapping(value = "/admin/newProprietario") 
	public String showRegisterPresidenteForm(@Valid @ModelAttribute("proprietario") Proprietario proprietario,
	                BindingResult proprietarioBindingResult, @Valid
	                @ModelAttribute("credentials") Credentials credentials,
	                BindingResult credentialsBindingResult,
	                Model model) {
	    
	    // Aggiungi log per stampare gli errori
	    if (proprietarioBindingResult.hasErrors()) {
	        System.out.println("Proprietario Binding Errors: " + proprietarioBindingResult.getAllErrors());
	    }
	    if (credentialsBindingResult.hasErrors()) {
	        System.out.println("Credentials Binding Errors: " + credentialsBindingResult.getAllErrors());
	    }

	    // Verifica se non ci sono errori
	    if (!proprietarioBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
	        proprietarioService.saveProprietario(proprietario);
	        credentials.setProprietario(proprietario);
	        credentials.setRole("OWNER");
	        credentialsService.saveCredentials(credentials);
	        model.addAttribute("proprietario", proprietario);
	        return "/admin/proprietarioRegistrationSuccessful";
	    }
	    return "/admin/formNewProprietario";
	}
	@GetMapping(value="/owner/formUpdateCredentials")
    public String showEditForm(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());       
        model.addAttribute("credentials", credentials);
        System.out.println("credentialsedit");
        return "/owner/formUpdateCredentials"; 
    }
	@PostMapping("/credEdit")
	public String updateCredentials(@Valid @ModelAttribute("credentials") Credentials credentials,
	                                BindingResult result,
	                                Model model) {
		System.out.println("credentialsupdate");
	    if (result.hasErrors()) {
	        // Se ci sono errori, mostra di nuovo il form di modifica
	        return "owner/formUpdateCredentials"; // Assicurati che il nome del template sia corretto
	    }
	    credentialsService.saveCredentials(credentials);
	    return "redirect:/index"; // Redirect alla pagina di successo
	}

}