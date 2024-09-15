package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Proprietario;
import it.uniroma3.siw.repository.ProprietarioRepository;

@Service
public class ProprietarioService {

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    public Proprietario saveProprietario(Proprietario proprietario) {
        return this.proprietarioRepository.save(proprietario);
    }

    public Optional<Proprietario> getProprietario(Long id) {
        return this.proprietarioRepository.findById(id);
    }

    public void deleteProprietario(Proprietario proprietario) {
        this.proprietarioRepository.delete(proprietario);
    }

	public Optional<Proprietario> findByName(String username) {
		return this.proprietarioRepository.findByName(username);
	}

	public Optional<Proprietario> findById(Long id) {
		return this.proprietarioRepository.findById(id);
	}

	public Iterable<Proprietario> findAll() {
		return this.proprietarioRepository.findAll();
	}
}

