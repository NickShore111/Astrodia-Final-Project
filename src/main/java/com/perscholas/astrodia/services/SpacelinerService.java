package com.perscholas.astrodia.services;

import com.perscholas.astrodia.models.Spaceliner;
import com.perscholas.astrodia.repositories.SpacelinerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpacelinerService {

    private final SpacelinerRepository spacelinerRepository;

    @Autowired
    public SpacelinerService(SpacelinerRepository spacelinerRepository) {
        this.spacelinerRepository = spacelinerRepository;
    }

    List<Spaceliner> findAll() { return spacelinerRepository.findAll(); }

    public void saveOrUpdate(Spaceliner liner) {
        spacelinerRepository.save(liner);
    }
}
