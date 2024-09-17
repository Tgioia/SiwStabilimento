package it.uniroma3.siw.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Prenotazione {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "stabilimento_id", nullable = false)
    private Stabilimento stabilimento;

    @ManyToOne
    private User user;
    
	private int numeroOmbrelloni;

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

	private int numeroLettini;

    public int getnumeroOmbrelloni() {
		return numeroOmbrelloni;
	}

	public void setnumeroOmbrelloni(int n_ombrelloni) {
		this.numeroOmbrelloni = n_ombrelloni;
	}

	public int getnumeroLettini() {
		return numeroLettini;
	}

	public void setnumeroLettini(int n_lettini) {
		this.numeroLettini = n_lettini;
	}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate dataPrenotazione) {
        this.data = dataPrenotazione;
    }

    public Stabilimento getStabilimento() {
        return stabilimento;
    }

    public void setStabilimento(Stabilimento stabilimento) {
        this.stabilimento = stabilimento;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
