package it.uniroma3.siw.model;

import jakarta.persistence.*;

@Entity
public class Ombrellone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int numero;

    @ManyToOne
    private Stabilimento stabilimento;

    public Ombrellone(Stabilimento stab) {
		this.stabilimento=stab;
	}

    public Ombrellone() {
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Stabilimento getStabilimento() {
        return stabilimento;
    }

    public void setStabilimento(Stabilimento stabilimento) {
        this.stabilimento = stabilimento;
    }
}
