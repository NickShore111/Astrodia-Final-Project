package com.perscholas.astrodia.services;

import com.perscholas.astrodia.models.Pad;
import com.perscholas.astrodia.repositories.PadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PadService {
    private final PadRepository padRepository;

    @Autowired
    public PadService(PadRepository padRepository) {
        this.padRepository = padRepository;
    }

    List<Pad> findAll() { return padRepository.findAll(); }

    public void saveOrUpdate(Pad liner) {
        padRepository.save(liner);
    }
}
