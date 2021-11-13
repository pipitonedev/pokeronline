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

public class CustomTavoloRepositoryImpl implements CustomTavoloRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Tavolo> findByExample(Tavolo example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder(
				"select distinct t from Tavolo t join fetch t.giocatori g where t.id = t.id ");

		if (example.getEsperienzaMin() != null) {
			whereClauses.add(" t.esperienzaMin  <= :esperienzaMin ");
			paramaterMap.put("esperienzaMin", example.getEsperienzaMin());
		}
		if (example.getCifraMin() != null) {
			whereClauses.add(" t.cifraMin  <= :cifraMin ");
			paramaterMap.put("cifraMin", example.getCifraMin());
		}
		if (StringUtils.isNotEmpty(example.getDenominazione())) {
			whereClauses.add(" t.denominazione like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getDateCreated() != null) {
			whereClauses.add("t.dateCreated >= :dateCreated ");
			paramaterMap.put("dateCreated", example.getDateCreated());
		}
		if (example.getGiocatori() != null && !example.getGiocatori().isEmpty()) {
			whereClauses.add(" u in :giocatori ");
			paramaterMap.put("giocatori", example.getGiocatori());
		}
		if (example.getUtenteCreatore() != null) {
			whereClauses.add(" t.utenteCreatore =:utenteCreatore ");
			paramaterMap.put("utenteCreatore", example.getUtenteCreatore());
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}
		return typedQuery.getResultList();
	}

}
