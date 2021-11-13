package it.prova.pokeronline.service.tavolo;

import java.util.List;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloService {
	
	public List<Tavolo> listAllElements();

	public Tavolo caricaSingoloElemento(Long id);

	public Tavolo aggiorna(Tavolo tavoloInstance);

	public Tavolo inserisciNuovo(Tavolo tavoloInstance);

	public void rimuovi(Tavolo tavoloInstance);

	public List<Tavolo> findByExample(Tavolo example);

}
