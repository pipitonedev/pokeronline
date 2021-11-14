package it.prova.pokeronline.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.tavolo.TavoloService;
import it.prova.pokeronline.service.utente.UtenteService;
import it.prova.pokeronline.utility.UtilityForm;
import it.prova.pokeronline.dto.RuoloDTO;
import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;

@Controller
@RequestMapping(value = "/tavolo")
public class TavoloController {

	@Autowired
	TavoloService tavoloService;
	
	@Autowired
	UtenteService utenteService;

	@GetMapping
	public ModelAndView listAllTavoli() {
		ModelAndView mv = new ModelAndView();
		List<Tavolo> tavoli = tavoloService.listAllElements();
		mv.addObject("tavolo_list_attribute", TavoloDTO.createTavoloDTOListFromModelList(tavoli));
		mv.setViewName("tavolo/list");
		return mv;
	}

	@GetMapping("/search")
	public String searchTavolo(ModelMap model) {
		model.addAttribute("search_tavolo_attr", new TavoloDTO());
		return "tavolo/search";
	}

	@PostMapping("/list")
	public String listTavoli(TavoloDTO tavoloExample, ModelMap model) {
		System.out.println(tavoloExample);
		List<Tavolo> tavoli = tavoloService.findByExample(tavoloExample.buildTavoloModel());
		model.addAttribute("tavolo_list_attribute", TavoloDTO.createTavoloDTOListFromModelList(tavoli));
		return "tavolo/list";
	}
	
	@GetMapping("/imieitavoli")
	public String findMieiTavoli(Model model, HttpServletRequest request) {
		List<Tavolo> tavoli = tavoloService.listAllMieiTavoli(utenteService.findByUsername(request.getUserPrincipal().getName()));
		model.addAttribute("tavolo_list_attribute", tavoli);
		return "tavolo/list";
	}
	
	@GetMapping("/insert")
	public String createTavolo(Model model) {
		model.addAttribute("insert_tavolo_attr", new TavoloDTO());
		return "tavolo/insert";
	}
	
	@PostMapping("/save")
	public String saveTavolo(@Valid @ModelAttribute("insert_tavolo_attr") TavoloDTO tavoloDTO, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {


		if (result.hasErrors()) 
			return "tavolo/insert";
		
		Utente user = utenteService.findByUsername(request.getUserPrincipal().getName());
		UtenteDTO creatore = new UtenteDTO();
		creatore.setId(user.getId());
		tavoloDTO.setUtenteCreatore(creatore);
	
		tavoloService.inserisciNuovo(tavoloDTO.buildTavoloModel());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/tavolo/imieitavoli";
	}
	
	@GetMapping("/show/{idTavolo}")
	public String visualizzaTavolo(@PathVariable(required = true) Long idTavolo, Model model) {
		Tavolo tavolo = tavoloService.caricaSingoloElemento(idTavolo);
		model.addAttribute("show_tavolo_attr", tavolo);
		return "tavolo/show";
	}
	
	@GetMapping("/edit/{idTavolo}")
	public String edit(@PathVariable(required = true) Long idTavolo, Model model) {
		Tavolo tavolo = tavoloService.caricaSingoloElemento(idTavolo);
		model.addAttribute("update_tavolo_attr", tavolo);
		return "tavolo/edit";
	}
	
	@PostMapping("/saveUpdate")
	public String saveUpdate(@Valid @ModelAttribute("update_tavolo_attr") TavoloDTO tavoloDTO, BindingResult result,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {


		if (result.hasErrors()) 
			return "tavolo/edit";
		
		Tavolo tavolo = tavoloService.caricaSingoloElemento(tavoloDTO.getId());
		tavoloDTO.setGiocatori(tavolo.getGiocatori());
		tavoloDTO.setUtenteCreatore(UtenteDTO.buildUtenteDTOFromModel(tavolo.getUtenteCreatore()));
		
		tavoloService.inserisciNuovo(tavoloDTO.buildTavoloModel());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/tavolo/imieitavoli";
	}

}
