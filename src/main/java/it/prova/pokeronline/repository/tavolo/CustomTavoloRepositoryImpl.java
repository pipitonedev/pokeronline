package it.prova.pokeronline.repository.tavolo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public class CustomTavoloRepositoryImpl implements CustomTavoloRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Tavolo> findByExample(Tavolo example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();
		String gioc = "";

		StringBuilder queryBuilder = new StringBuilder(
				"select distinct t from Tavolo t join fetch t.utenteCreatore u join fetch t.giocatori g where t.id = t.id");

		if (StringUtils.isNotEmpty(example.getDenominazione())) {
			whereClauses.add(" t.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getDateCreated() != null) {
			whereClauses.add(" t.dateCreated >= :dateCreated ");
			paramaterMap.put("dateCreated", example.getDateCreated());
		}
		if (example.getEsperienzaMin() > 0) {
			whereClauses.add(" t.esperienzaMin >= :esperienzaMin ");
			paramaterMap.put("esperienzaMin", "%" + example.getEsperienzaMin() + "%");
		}
		if (example.getCifraMin() > 0) {
			whereClauses.add(" t.cifraMin >= :cifraMin ");
			paramaterMap.put("cifraMin", example.getCifraMin());
		}
		if (example.getUtenteCreatore() != null) {
			whereClauses.add(" u.id = :idUtenteCreatore ");
			paramaterMap.put("idUtenteCreatore", example.getUtenteCreatore().getId());
		}

		if (example.getGiocatori() != null && example.getGiocatori().size() > 0) {
			int i = 0;
			for (Utente giocatore : example.getGiocatori()) {
				if (i == 0)
					gioc += " g.id = " + giocatore.getId();
				else
					gioc += " or g.id = " + giocatore.getId();
				i++;
			}
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		if (example.getGiocatori() != null)
			queryBuilder.append(" and " + gioc);
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}

}
