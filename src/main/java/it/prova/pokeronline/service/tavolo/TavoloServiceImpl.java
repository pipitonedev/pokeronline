package it.prova.pokeronline.service.tavolo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;
import it.prova.pokeronline.repository.utente.UtenteRepository;

@Service
public class TavoloServiceImpl implements TavoloService {

	@Autowired
	private TavoloRepository repository;

	@Autowired
	private UtenteRepository utenteRepository;

	@Transactional(readOnly = true)
	public List<Tavolo> listAllElements() {
		return (List<Tavolo>) repository.findAll();
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Transactional
	public Tavolo aggiorna(Tavolo tavoloInstance) {
		return repository.save(tavoloInstance);
	}

	@Transactional
	public Tavolo inserisciNuovo(Tavolo tavoloInstance) {
		return repository.save(tavoloInstance);
	}

	@Transactional
	public void rimuovi(Tavolo tavoloInstance) {
		repository.delete(tavoloInstance);

	}

	@Transactional(readOnly = true)
	public List<Tavolo> findByExample(TavoloDTO tavolo, String username) {
		Utente utente = utenteRepository.findByUsernameConRuoli(username).get();

		if (utente.isAdmin()) {
			return repository.findByExample(tavolo);
		}

		return repository.findByExampleTavoli(tavolo, utente.getId());
	}

	@Transactional(readOnly = true)
	public List<Tavolo> listAllMieiTavoli(Utente user) {
		return repository.findAllByUtenteCreatore_IdIs(user.getId());
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavoloConGiocatori(Long id) {
		return repository.findByIdConGiocatori(id).orElse(null);
	}

	@Transactional
	public void rimuoviById(Long id) {
		repository.deleteById(id);
		
	}

}
