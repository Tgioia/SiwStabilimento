package it.uniroma3.siw.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.PrenotazioneRepository;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public Iterable<Prenotazione> getAllPrenotazioni(){
    	return this.prenotazioneRepository.findAll();
    }
    public Iterable<Prenotazione> getPrenotazioneByUser(User user){
    	return this.getPrenotazioneByUser(user);
    }
    
    public Prenotazione savePrenotazione(Prenotazione prenotazione) {
        return this.prenotazioneRepository.save(prenotazione);
    }

    public Optional<Prenotazione> getPrenotazione(Long id) {
        return this.prenotazioneRepository.findById(id);
    }

    public void deletePrenotazione(Prenotazione prenotazione) {
        this.prenotazioneRepository.delete(prenotazione);
    }
	public Iterable<Prenotazione> findByUser(User user) {
		return this.prenotazioneRepository.findAllByUser(user);
	}
	public Optional<Prenotazione> findById(Long id) {
		return this.prenotazioneRepository.findById(id);
	}
}
