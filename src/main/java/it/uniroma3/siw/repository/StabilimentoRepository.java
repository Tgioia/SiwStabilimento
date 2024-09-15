package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.uniroma3.siw.model.Proprietario;
import it.uniroma3.siw.model.Stabilimento;
@Repository
public interface StabilimentoRepository extends CrudRepository<Stabilimento, Long> {

	Iterable<Stabilimento> findAllByProprietario(Proprietario proprietario);
}
