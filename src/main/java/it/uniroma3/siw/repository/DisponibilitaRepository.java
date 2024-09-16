package it.uniroma3.siw.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Disponibilita;
import it.uniroma3.siw.model.Stabilimento;

public interface DisponibilitaRepository extends CrudRepository<Disponibilita, Long> {
	@Query("SELECT d FROM Disponibilita d WHERE d.stabilimento = :stabilimento AND d.data = :data")
    List<Disponibilita> findByStabilimentoAndData(Stabilimento stabilimento, LocalDate data);
	List<Disponibilita> findAllByOrderByDataAsc();
}

