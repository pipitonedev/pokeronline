package it.prova.pokeronline.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tavolo")
public class Tavolo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "esperienzaMin")
	private Integer esperienzaMin;
	@Column(name = "cifraMin")
	private Integer cifraMin;
	@Column(name = "denominazione")
	private String denominazione;
	@Column(name = "dateCreated")
	private Date dateCreated;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tavolo")
	private Set<Utente> giocatori = new HashSet<Utente>(0);

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_id", nullable = false)
	private Utente utenteCreatore;

	public Tavolo() {
	}

	public Tavolo(String denominazione, Date dateCreated, int esperienzaMin, int cifraMin) {
		super();
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
	}

	public Tavolo(Long id, String denominazione, Date dateCreated, int esperienzaMin, int cifraMin,
			Utente utenteCreatore, Set<Utente> giocatori) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dateCreated = dateCreated;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
		this.utenteCreatore = utenteCreatore;
		this.giocatori = giocatori;
	}

	public Tavolo(Long id, String denominazione, Date dateCreated, int esperienzaMin, int cifraMin) {
		super();
		this.id = id;
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

	public Set<Utente> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(Set<Utente> giocatori) {
		this.giocatori = giocatori;
	}

	public Utente getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(Utente utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	@Override
	public String toString() {
		return "Tavolo [id=" + id + ", esperienzaMin=" + esperienzaMin + ", cifraMin=" + cifraMin + ", denominazione="
				+ denominazione + ", dateCreated=" + dateCreated + ", giocatori=" + giocatori + ", utenteCreatore="
				+ utenteCreatore + "]";
	}

}
