package it.uniroma3.siw.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.uniroma3.siw.service.DisponibilitaService;

@Entity
public class Stabilimento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String indirizzo;
    private int numeroOmbrelloni;
    private int numeroLettini;

    @ManyToOne
    private Proprietario proprietario;

    @OneToMany(mappedBy = "stabilimento", cascade = CascadeType.ALL)
    private List<Ombrellone> ombrelloni;

    @OneToMany(mappedBy = "stabilimento", cascade = CascadeType.ALL)
    private List<Lettino> lettini;
    
    public List<Disponibilita> getDisponibilitaPerGiorno() {
		return disponibilitaPerGiorno;
	}
	public void setDisponibilitaPerGiorno(List<Disponibilita> disponibilitaPerGiorno) {
		this.disponibilitaPerGiorno = disponibilitaPerGiorno;
	}

	@OneToMany(mappedBy = "stabilimento", cascade = CascadeType.ALL)
    private List<Disponibilita> disponibilitaPerGiorno=new ArrayList<>();
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getNumeroOmbrelloni() {
        return numeroOmbrelloni;
    }

    public void setNumeroOmbrelloni(int numeroOmbrelloni) {
        this.numeroOmbrelloni = numeroOmbrelloni;
    }

    public int getNumeroLettini() {
        return numeroLettini;
    }

    public void setNumeroLettini(int numeroLettini) {
        this.numeroLettini = numeroLettini;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public List<Ombrellone> getOmbrelloni() {
        return ombrelloni;
    }

    public void setOmbrelloni(List<Ombrellone> ombrelloni) {
        this.ombrelloni = ombrelloni;
    }

    public List<Lettino> getLettini() {
        return lettini;
    }

    public void setLettini(List<Lettino> lettini) {
        this.lettini = lettini;
    }
}
