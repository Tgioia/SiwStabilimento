package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Proprietario;
@Repository
public interface ProprietarioRepository extends CrudRepository<Proprietario, Long> {

	Optional<Proprietario> findByName(String name);
}
