package it.uniroma3.siw.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Disponibilita;
import it.uniroma3.siw.model.Stabilimento;
import it.uniroma3.siw.repository.DisponibilitaRepository;

@Service
public class DisponibilitaService {
	@Autowired
	private DisponibilitaRepository disponibilitaRepository;
	
	public List<Disponibilita> findByStabilimentoAndData(Stabilimento stabilimento, LocalDate data){
		return this.disponibilitaRepository.findByStabilimentoAndData(stabilimento, data);
	}
	
	public void save(Disponibilita disponibilita) {
		this.disponibilitaRepository.save(disponibilita);		
	}
    public List<Disponibilita> getDisponibilitaByStabilimentoOggi(Stabilimento stabilimento) {
        return disponibilitaRepository.findByStabilimentoAndData(stabilimento, LocalDate.now());
    }

    public void inizializzaDisponibilita(Stabilimento stabilimento, int lettini, int ombrelloni) {
        for (int i = 0; i < 30; i++) {
            LocalDate data = LocalDate.now().plusDays(i);
            Disponibilita disponibilita = new Disponibilita(stabilimento, data, lettini, ombrelloni);
            disponibilitaRepository.save(disponibilita);
        }
    }

}
