package org.nicholasshore.astrodia.services;

import org.nicholasshore.astrodia.models.Pad;
import org.nicholasshore.astrodia.repositories.PadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PadService {
    private final PadRepository padRepository;

    @Autowired
    public PadService(PadRepository padRepository) {
        this.padRepository = padRepository;
    }

    public List<Pad> findAll() { return padRepository.findAll(); }

    public void saveOrUpdate(Pad liner) {
        padRepository.save(liner);
    }

    public Optional<Pad> findById(String id) {
        return padRepository.findById(id);
    }
}
