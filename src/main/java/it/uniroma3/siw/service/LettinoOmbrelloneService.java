package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Lettino;
import it.uniroma3.siw.model.Ombrellone;
import it.uniroma3.siw.repository.LettinoRepository;
import it.uniroma3.siw.repository.OmbrelloneRepository;

@Service
public class LettinoOmbrelloneService {

    @Autowired
    private LettinoRepository lettinoRepository;

    @Autowired
    private OmbrelloneRepository ombrelloneRepository;

    public Lettino saveLettino(Lettino lettino) {
        return this.lettinoRepository.save(lettino);
    }

    public Ombrellone saveOmbrellone(Ombrellone ombrellone) {
        return this.ombrelloneRepository.save(ombrellone);
    }

    public void deleteLettino(Lettino lettino) {
        this.lettinoRepository.delete(lettino);
    }

    public void deleteOmbrellone(Ombrellone ombrellone) {
        this.ombrelloneRepository.delete(ombrellone);
    }
}
