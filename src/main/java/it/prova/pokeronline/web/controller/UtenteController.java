package it.prova.pokeronline.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.ruolo.RuoloService;
import it.prova.pokeronline.service.utente.UtenteService;
import it.prova.pokeronline.dto.RuoloDTO;
import it.prova.pokeronline.utility.UtilityForm;
import it.prova.pokeronline.validation.ValidationNoPassword;
import it.prova.pokeronline.validation.ValidationWithPassword;

@Controller
@RequestMapping(value = "/utente")
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private RuoloService ruoloService;

	@GetMapping
	public ModelAndView listAllUtenti() {
		ModelAndView mv = new ModelAndView();
		List<Utente> utenti = utenteService.listAllUtenti();
		mv.addObject("utente_list_attribute", utenti);
		mv.setViewName("utente/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchUtente(ModelMap model) {
		model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm
				.buildCheckedRolesForPages(RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), null));
		return "utente/search";
	}

	@PostMapping("/list")
	public String listUtenti(Utente utenteExample, ModelMap model, HttpServletRequest request) {
		String[] ruoli = request.getParameterValues("ruoliIds");
		List<Utente> utenti = utenteService.findByExample(utenteExample, ruoli);
		model.addAttribute("utente_list_attribute", utenti);
		return "utente/list";
	}

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm
				.buildCheckedRolesForPages(RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), null));
		model.addAttribute("insert_utente_attr", new UtenteDTO());
		return "utente/insert";
	}

	@PostMapping("/save")
	public String save(
			@Validated({ ValidationWithPassword.class,
					ValidationNoPassword.class }) @ModelAttribute("insert_utente_attr") UtenteDTO utenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {

		if (!result.hasFieldErrors("password") && !utenteDTO.getPassword().equals(utenteDTO.getConfermaPassword()))
			result.rejectValue("confermaPassword", "password.diverse");

		if (result.hasErrors()) {
			model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm.buildCheckedRolesForPages(
					RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), utenteDTO.getRuoliIds()));
			return "utente/insert";
		}
		utenteService.inserisciNuovo(utenteDTO.buildUtenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";
	}

	@GetMapping("/edit/{idUtente}")
	public String edit(@PathVariable(required = true) Long idUtente, Model model) {
		Utente utenteModel = utenteService.caricaSingoloUtenteConRuoli(idUtente);
		model.addAttribute("edit_utente_attr", UtenteDTO.buildUtenteDTOFromModel(utenteModel));
		model.addAttribute("mappaRuoliConSelezionati_attr",
				UtilityForm.buildCheckedRolesFromRolesAlreadyInUtente(
						RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()),
						RuoloDTO.createRuoloDTOListFromModelSet(utenteModel.getRuoli())));
		return "utente/edit";
	}

	@PostMapping("/update")
	public String update(@Validated(ValidationNoPassword.class) @ModelAttribute("edit_utente_attr") UtenteDTO utenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm.buildCheckedRolesForPages(
					RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), utenteDTO.getRuoliIds()));
			return "utente/edit";
		}
		utenteService.aggiorna(utenteDTO.buildUtenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";
	}

	@PostMapping("/cambiaStato")
	public String cambiaStato(@RequestParam(name = "idUtenteForChangingStato", required = true) Long idUtente) {
		utenteService.changeUserAbilitation(idUtente);
		return "redirect:/utente";
	}

	@GetMapping("/show/{idUtente}")
	public String showFilm(@PathVariable(required = true) Long idUtente, Model model) {
		model.addAttribute("show_utente_attr", utenteService.caricaSingoloUtente(idUtente));
		return "utente/show";
	}

	@PostMapping("/resetPassword")
	public String resetPassword(@RequestParam(name = "idUtenteForResetPassword", required = true) Long idUtente,
			Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		Utente utente = utenteService.caricaSingoloUtente(idUtente);
		utente.setPassword("Password@1");
		utenteService.aggiorna(utente);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";
	}

	@GetMapping("/cambioPassword")
	public String cambioPassword() {
		return "utente/resetpassword";
	}

	@PostMapping("/saveCambioPassword")
	public String saveCambioPassword(HttpServletRequest request, RedirectAttributes redirectAttrs) {

		String passwordVecchia = request.getParameter("vecchiaPassword");
		String passwordNuova = request.getParameter("nuovaPassword");
		String passwordRep = request.getParameter("nuovaPasswordRep");
		String user = request.getUserPrincipal().getName();

		switch (utenteService.cambioPassword(passwordVecchia, passwordNuova, passwordRep, user)) {
		case -1:
			redirectAttrs.addFlashAttribute("errorMessage", "La password inserita non corrisponde a quella vecchia");
			return "redirect:/utente/resetpassword";
		case -2:
			redirectAttrs.addFlashAttribute("errorMessage", "Le password non coincidono");
			return "redirect:/utente/resetpassword";
		}

		return "redirect:/logout";
	}

	@GetMapping("/ricarica")
	public String ricaricaCredito(Model model) {
		return "utente/aggiungicredito";
	}

	@PostMapping("/addcredito")
	public String addCredito(Model model, HttpServletRequest request) {
		int creditoDaAggiungere = Integer.parseInt(request.getParameter("ricarica"));
		Utente utenteInSessione = (Utente) request.getSession().getAttribute("userInfo");

		utenteService.aggiungiCredito(utenteInSessione, creditoDaAggiungere);
		return "index";
	}

}
