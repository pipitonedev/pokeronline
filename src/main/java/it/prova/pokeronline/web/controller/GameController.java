package it.prova.pokeronline.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.tavolo.TavoloService;
import it.prova.pokeronline.service.utente.UtenteService;

@Controller
@RequestMapping(value = "/gioca")
public class GameController {
	
	
	@Autowired
	private TavoloService tavoloService;

	@Autowired
	private UtenteService utenteService;

	@GetMapping("/show/{idTavolo}")
	public String show(@PathVariable(required = true) Long idTavolo, Model model) {
		Tavolo tavolo = tavoloService.caricaSingoloElemento(idTavolo);
		model.addAttribute("show_tavolo_attr", tavolo);
		return "gioca/show";
	}

	@PostMapping("/avviaGioco/{idTavolo}")
	public String avviaGioco(@PathVariable(required = true) Long idTavolo, Model model, HttpServletRequest request) {
		
		Utente utenteInSessione = utenteService.findByUsername(request.getUserPrincipal().getName());
		
		utenteInSessione = utenteService.caricaSingoloUtente(utenteInSessione.getId());
		Tavolo tavoloPerGiocare = tavoloService.caricaSingoloElemento(idTavolo);

		model.addAttribute("show_tavolo_attr", tavoloPerGiocare);
		if (utenteInSessione.getTavolo() != tavoloPerGiocare && utenteInSessione.getTavolo() != null) {
			model.addAttribute("errorMessage", "Sei già presente in una partita.");
			return "gioca/show";
		}
		if (utenteInSessione.getCreditoAccumulato() < tavoloPerGiocare.getCifraMin()) {
			model.addAttribute("errorMessage", "Credito insufficiente per giocare.");
			return "gioca/show";
		}
		if (utenteInSessione.getEsperienzaAccumulata() < tavoloPerGiocare.getEsperienzaMin()) {
			model.addAttribute("errorMessage", "Esperienza insufficiente per giocare.");
			return "gioca/show";
		}
		
		model.addAttribute("successMessage", "Sei In Partita, gioca e tenta la fortuna!");
		return "gioca/partita";
	}
	
	@PostMapping("/partita/{idTavolo}")
	public String partita(@PathVariable(required = true) Long idTavolo, Model model,
			 HttpServletRequest request) {

		Utente utenteInSessione = utenteService.findByUsername(request.getUserPrincipal().getName());
		
		utenteInSessione = utenteService.caricaSingoloUtente(utenteInSessione.getId());
		Tavolo tavoloPerGiocare = tavoloService.caricaSingoloElemento(idTavolo);
		utenteInSessione.setEsperienzaAccumulata(utenteInSessione.getEsperienzaAccumulata()+1);
		utenteInSessione.setCreditoAccumulato(utenteInSessione.getCreditoAccumulato()+10);
		utenteInSessione.setTavolo(tavoloPerGiocare);
		
		utenteService.aggiorna(utenteInSessione);
		tavoloPerGiocare.getGiocatori().add(utenteInSessione);
		tavoloService.aggiorna(tavoloPerGiocare);
		
		model.addAttribute("show_tavolo_attr", tavoloPerGiocare);
		model.addAttribute("successMessage", "Hai Vinto!");
		return "gioca/partita";
	}
	
	@PostMapping("/exit/{idTavolo}")
	public String exitPartita(@PathVariable(required = true) Long idTavolo, Model model,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		Utente utenteInSessione = utenteService.findByUsername(request.getUserPrincipal().getName());
		utenteInSessione = utenteService.caricaSingoloUtente(utenteInSessione.getId());
		Tavolo tavoloPerGiocare = tavoloService.caricaSingoloElemento(idTavolo);
		
		utenteInSessione.setTavolo(null);
		utenteService.aggiorna(utenteInSessione);
		
		tavoloPerGiocare.getGiocatori().remove(utenteInSessione);
		tavoloService.aggiorna(tavoloPerGiocare);
		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/home";
	}

}
