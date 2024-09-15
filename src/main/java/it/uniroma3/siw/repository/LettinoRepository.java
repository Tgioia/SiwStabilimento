package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Lettino;

public interface LettinoRepository extends CrudRepository<Lettino, Long> {
}