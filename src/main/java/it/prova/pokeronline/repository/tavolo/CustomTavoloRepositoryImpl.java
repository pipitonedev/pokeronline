package it.prova.pokeronline.repository.tavolo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public class CustomTavoloRepositoryImpl implements CustomTavoloRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Tavolo> findByExample(TavoloDTO example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();
		
		if(example.getEsperienzaMin() == null)
			example.setEsperienzaMin(1);
		
		if(example.getCifraMin() == null)
			example.setCifraMin(1);

		StringBuilder queryBuilder = new StringBuilder(
				"select t from Tavolo t join fetch t.utenteCreatore u where t.id = t.id");

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
			paramaterMap.put("esperienzaMin", example.getEsperienzaMin());
		}
		if (example.getCifraMin() > 0) {
			whereClauses.add(" t.cifraMin >= :cifraMin ");
			paramaterMap.put("cifraMin", example.getCifraMin());
		}
		if (example.getUtenteCreatore() != null && example.getUtenteCreatore().getId() != null) {
			whereClauses.add(" u.id = :idUtenteCreatore ");
			paramaterMap.put("idUtenteCreatore", example.getUtenteCreatore().getId());
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}

	@Override
	public List<Tavolo> findByExampleTavoli(TavoloDTO example, Long id) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();
		
		if(example.getEsperienzaMin() == null)
			example.setEsperienzaMin(1);
		
		if(example.getCifraMin() == null)
			example.setCifraMin(1);

		StringBuilder queryBuilder = new StringBuilder("select distinct t from Tavolo t join fetch t.utenteCreatore u where t.id = t.id");

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
			paramaterMap.put("esperienzaMin", example.getEsperienzaMin());
		}
		if (example.getCifraMin() > 0) {
			whereClauses.add(" t.cifraMin >= :cifraMin ");
			paramaterMap.put("cifraMin", example.getCifraMin());
		}
		
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		queryBuilder.append(" and u.id = " + id);
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}


}
