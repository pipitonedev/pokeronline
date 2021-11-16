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

		if (example.getEsperienzaMin() == null)
			example.setEsperienzaMin(0);

		if (example.getCifraMin() == null)
			example.setCifraMin(0);

		StringBuilder queryBuilder = new StringBuilder(
				"select r from Tavolo r join fetch r.utenteCreatore uc where r.id = r.id");

		if (StringUtils.isNotEmpty(example.getDenominazione())) {
			whereClauses.add(" r.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getDateCreated() != null) {
			whereClauses.add(" r.dateCreated >= :dateCreated ");
			paramaterMap.put("dateCreated", example.getDateCreated());
		}
		if (example.getEsperienzaMin() >= 0) {
			whereClauses.add(" r.esperienzaMin >= :esperienzaMin ");
			paramaterMap.put("esperienzaMin", example.getEsperienzaMin());
		}
		if (example.getCifraMin() >= 0) {
			whereClauses.add(" r.cifraMin >= :cifraMin ");
			paramaterMap.put("cifraMin", example.getCifraMin());
		}
		if (example.getUtenteCreatore() != null && example.getUtenteCreatore().getId() != null) {
			whereClauses.add(" uc.id = :idUtenteCreatore ");
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

		if (example.getEsperienzaMin() == null)
			example.setEsperienzaMin(0);

		if (example.getCifraMin() == null)
			example.setCifraMin(0);

		StringBuilder queryBuilder = new StringBuilder(
				"select distinct r from Tavolo r join fetch r.utenteCreatore uc where r.id = r.id");

		if (StringUtils.isNotEmpty(example.getDenominazione())) {
			whereClauses.add(" r.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getDateCreated() != null) {
			whereClauses.add(" r.dateCreated >= :dateCreated ");
			paramaterMap.put("dateCreated", example.getDateCreated());
		}
		if (example.getEsperienzaMin() >= 0) {
			whereClauses.add(" r.esperienzaMin >= :esperienzaMin ");
			paramaterMap.put("esperienzaMin", example.getEsperienzaMin());
		}
		if (example.getCifraMin() >= 0) {
			whereClauses.add(" r.cifraMin >= :cifraMin ");
			paramaterMap.put("cifraMin", example.getCifraMin());
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		queryBuilder.append(" and uc.id = " + id);
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}

	@Override
	public List<Tavolo> findByExampleGenerico(TavoloDTO example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		if (example.getEsperienzaMin() == null)
			example.setEsperienzaMin(0);

		if (example.getCifraMin() == null)
			example.setCifraMin(0);

		StringBuilder queryBuilder = new StringBuilder(
				"select distinct r from Tavolo r join fetch r.utenteCreatore uc left join fetch r.giocatori gio where r.id = r.id ");

		if (StringUtils.isNotBlank(example.getDenominazione())) {
			whereClauses.add(" r.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getDateCreated() != null) {
			whereClauses.add(" r.dateCreated >= :dateCreated ");
			paramaterMap.put("dateCreated", example.getDateCreated());
		}
		if (example.getEsperienzaMin() >= 0) {
			whereClauses.add(" r.esperienzaMin >= :esperienzaMin ");
			paramaterMap.put("esperienzaMin", example.getEsperienzaMin());
		}
		if (example.getCifraMin() >= 0) {
			whereClauses.add(" r.cifraMin >= :cifraMin ");
			paramaterMap.put("cifraMin", example.getCifraMin());
		}
		if (example.getUtenteCreatore() != null && example.getUtenteCreatore().getId() != null) {
			whereClauses.add(" uc.id = :idUtenteCreatore ");
			paramaterMap.put("idUtenteCreatore", example.getUtenteCreatore().getId());
		}
		if (example.getGiocatoreCercato() != null && example.getGiocatoreCercato().getId() != null) {
			whereClauses.add(" gio.id = :giocatoreCercatoId ");
			paramaterMap.put("giocatoreCercatoId", example.getGiocatoreCercato().getId());
		}

		queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		System.out.println(queryBuilder);
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}
}