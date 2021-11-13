package it.prova.pokeronline.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.prova.pokeronline.model.Utente;

public class TavoloDTO {

	private Long id;

	@NotNull(message = "{esperienzaMin.notnull}")
	@Column(name = "esperienzaMin")
	private Integer esperienzaMin;

	@NotNull(message = "{cifraMinima.notnull}")
	@Column(name = "cifraMin")
	private Integer cifraMin;

	@NotBlank(message = "{denominazione.notblank}")
	@Column(name = "denominazione")
	private String denominazione;

	@NotNull(message = "{dateCreated.notnull}")
	@Column(name = "dateCreated")
	private Date dateCreated;
	
	@NotNull(message = "{utentecreatore.notnull}")
	private UtenteDTO utenteCreatore;

	private Set<Utente> giocatori = new HashSet<Utente>();

	public TavoloDTO() {
	}

	public TavoloDTO(Long id, Integer esperienzaMin, Integer cifraMin, String denominazione, Date dateCreated,
			UtenteDTO utenteCreatore) {
		super();
		this.id = id;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.utenteCreatore = utenteCreatore;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
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

}
