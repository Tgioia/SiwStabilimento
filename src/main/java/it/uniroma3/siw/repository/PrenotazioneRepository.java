package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Prenotazione;
import it.uniroma3.siw.model.User;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {
	public Iterable<Prenotazione> getPrenotazioneByUser(User user);

	public Iterable<Prenotazione> findAllByUser(User user);
}
