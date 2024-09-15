package it.uniroma3.siw.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Disponibilita {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate data;

    private int lettiniDisponibili;

    private int ombrelloniDisponibili;

    @ManyToOne
    private Stabilimento stabilimento;

	public Disponibilita(Stabilimento stab, LocalDate data2, int lettini, int ombrelloni) {
		this.data=data2;
		this.stabilimento=stab;
		this.lettiniDisponibili=lettini;
		this.ombrelloniDisponibili=ombrelloni;
	}
	public Disponibilita() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public int getLettiniDisponibili() {
		return lettiniDisponibili;
	}

	public void setLettiniDisponibili(int lettiniDisponibili) {
		this.lettiniDisponibili = lettiniDisponibili;
	}

	public int getOmbrelloniDisponibili() {
		return ombrelloniDisponibili;
	}

	public void setOmbrelloniDisponibili(int ombrelloniDisponibili) {
		this.ombrelloniDisponibili = ombrelloniDisponibili;
	}

	public Stabilimento getStabilimento() {
		return stabilimento;
	}

	public void setStabilimento(Stabilimento stabilimento) {
		this.stabilimento = stabilimento;
	}


}

