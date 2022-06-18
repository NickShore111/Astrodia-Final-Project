package com.perscholas.astrodia.services;

import com.perscholas.astrodia.models.Spaceliner;
import com.perscholas.astrodia.repositories.SpacelinerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpacelinerService {
    private final SpacelinerRepository spacelinerRepository;
    @Autowired
    public SpacelinerService(SpacelinerRepository spacelinerRepository) {
        this.spacelinerRepository = spacelinerRepository;
    }

    public List<Spaceliner> findAll() { return spacelinerRepository.findAll(); }

    public void saveOrUpdate(Spaceliner liner) {
        spacelinerRepository.save(liner);
    }

    public Optional<Spaceliner> findById(String id) { return spacelinerRepository.findById(id); }
}
