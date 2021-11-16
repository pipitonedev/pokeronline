package it.prova.pokeronline.service.tavolo;

import java.util.List;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public interface TavoloService {
	
	public List<Tavolo> listAllElements();

	public Tavolo caricaSingoloElemento(Long id);

	public Tavolo aggiorna(Tavolo tavoloInstance);

	public Tavolo inserisciNuovo(Tavolo tavoloInstance);

	public void rimuovi(Tavolo tavoloInstance);

	public List<Tavolo> findByExample(TavoloDTO tavolo, String username);
	
	public List<Tavolo> listAllMieiTavoli(Utente user);
	
	public Tavolo caricaSingoloTavoloConGiocatori(Long id);
	
	public void rimuoviById(Long id);
	
	public List<Tavolo> findByExampleGenerico(TavoloDTO example);

}
