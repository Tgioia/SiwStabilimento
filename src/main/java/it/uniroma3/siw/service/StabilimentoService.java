package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Proprietario;
import it.uniroma3.siw.model.Stabilimento;
import it.uniroma3.siw.repository.StabilimentoRepository;

@Service
public class StabilimentoService {

    @Autowired
    private StabilimentoRepository stabilimentoRepository;

    public Stabilimento saveStabilimento(Stabilimento stabilimento) {
        return this.stabilimentoRepository.save(stabilimento);
    }

    public Optional<Stabilimento> getStabilimento(Long id) {
        return this.stabilimentoRepository.findById(id);
    }

    public Iterable<Stabilimento> findAll() {
        return  this.stabilimentoRepository.findAll();
    }

    public void deleteStabilimento(Stabilimento stabilimento) {
        this.stabilimentoRepository.delete(stabilimento);
    }

	public Optional<Stabilimento> findById(Long id) {
		return this.stabilimentoRepository.findById(id);
	}

	public Iterable<Stabilimento> findAllByProprietario(Proprietario proprietario) {
		return this.stabilimentoRepository.findAllByProprietario(proprietario);
	}
}
