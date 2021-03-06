package org.nicholasshore.astrodia.services;

import org.nicholasshore.astrodia.models.Shuttle;
import org.nicholasshore.astrodia.repositories.ShuttleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShuttleService {
    private final ShuttleRepository shuttleRepository;

    @Autowired
    public ShuttleService(ShuttleRepository shuttleRepository) {
        this.shuttleRepository = shuttleRepository;
    }

    public List<Shuttle> findAll() { return shuttleRepository.findAll(); }

    public void saveOrUpdate(Shuttle s) {
        shuttleRepository.save(s);
    }

    public Optional<Shuttle> findById(String id) { return shuttleRepository.findById(id); }
}
