package it.prova.pokeronline.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public class TavoloDTO {

	private Long id;

	@NotNull(message = "{dateCreated.notnull}")
	private Date dateCreated;

	@NotBlank(message = "{denominazione.notblank}")
	private String denominazione;

	@NotNull(message = "{esperienzaMinima.notblank}")
	@Min(0)
	private Integer esperienzaMin;

	@NotNull(message = "{cifraMin.notnull}")
	@Min(0)
	private Integer cifraMin;

	private UtenteDTO utenteCreatore;

	private Set<Utente> giocatori = new HashSet<Utente>();

	private UtenteDTO giocatoreCercato;

	public TavoloDTO() {
		super();
	}

	public TavoloDTO(Long id, String denominazione, Date dateCreated, Integer esperienzaMin, Integer cifraMin,
			UtenteDTO utenteCreatore, Set<Utente> giocatori) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
		this.utenteCreatore = utenteCreatore;
		this.giocatori = giocatori;
	}

	public TavoloDTO(Long id, String denominazione, Date dateCreated, Integer esperienzaMin, Integer cifraMin) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
	}

	public TavoloDTO(String denominazione, Date dateCreated, Integer esperienzaMin, Integer cifraMin) {
		super();
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Integer getEsperienzaMin() {
		return esperienzaMin;
	}

	public void setEsperienzaMin(Integer esperienzaMin) {
		this.esperienzaMin = esperienzaMin;
	}

	public Integer getCifraMin() {
		return cifraMin;
	}

	public void setCifraMin(Integer cifraMin) {
		this.cifraMin = cifraMin;
	}

	public UtenteDTO getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(UtenteDTO utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public Set<Utente> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(Set<Utente> giocatori) {
		this.giocatori = giocatori;
	}

	public UtenteDTO getGiocatoreCercato() {
		return giocatoreCercato;
	}

	public void setGiocatoreCercato(UtenteDTO giocatoreCercato) {
		this.giocatoreCercato = giocatoreCercato;
	}

	public static TavoloDTO buildTavoloDTOFromModel(Tavolo tavolo) {
		return new TavoloDTO(tavolo.getId(), tavolo.getDenominazione(), tavolo.getDateCreated(),
				tavolo.getEsperienzaMin(), tavolo.getCifraMin(),
				UtenteDTO.buildUtenteDTOFromModel(tavolo.getUtenteCreatore()), tavolo.getGiocatori());
	}

	public static List<TavoloDTO> createTavoloDTOListFromModelList(List<Tavolo> modelListInput) {
		return modelListInput.stream().map(registaEntity -> {
			return TavoloDTO.buildTavoloDTOFromModel(registaEntity);
		}).collect(Collectors.toList());
	}

	public Tavolo buildTavoloModel() {
		return new Tavolo(this.id, this.denominazione, this.dateCreated, this.esperienzaMin, this.cifraMin,
				this.utenteCreatore.buildUtenteModel(false), this.giocatori);
	}

}