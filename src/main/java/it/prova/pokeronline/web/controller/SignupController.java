package it.prova.pokeronline.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.service.utente.UtenteService;
import it.prova.pokeronline.validation.ValidationNoPassword;
import it.prova.pokeronline.validation.ValidationWithPassword;

@Controller
@RequestMapping(value = "/signup")
public class SignupController {

	@Autowired
	private UtenteService utenteService;

	@GetMapping
	public String registrazione(Model model) {
		model.addAttribute("insert_utente_attr", new UtenteDTO());
		return "signup/registrazione";
	}

	@PostMapping("/registra")
	public String signup(
			@Validated({ ValidationWithPassword.class,
					ValidationNoPassword.class }) @ModelAttribute("insert_utente_attr") UtenteDTO utenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {

		if (!utenteService.usernameExist(utenteDTO.getUsername())) {
			redirectAttrs.addFlashAttribute("errorMessage", "Account già presente con questo username.");
			return "redirect:/login";
		}
		if (!result.hasFieldErrors("password") && !utenteDTO.getPassword().equals(utenteDTO.getConfermaPassword()))
			result.rejectValue("confermaPassword", "password.diverse");
		if (result.hasErrors()) {
			return "signup/registrazione";
		}
		utenteService.signUp(utenteDTO.buildUtenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Registrazione eseguita correttamente");
		return "redirect:/login";

	}
}