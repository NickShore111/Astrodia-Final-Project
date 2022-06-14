package com.perscholas.astrodia.services;

import com.perscholas.astrodia.models.Shuttle;
import com.perscholas.astrodia.repositories.ShuttleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShuttleService {
    private final ShuttleRepository shuttleRepository;

    @Autowired
    public ShuttleService(ShuttleRepository shuttleRepository) {
        this.shuttleRepository = shuttleRepository;
    }

    List<Shuttle> findAll() { return shuttleRepository.findAll(); }

    public void saveOrUpdate(Shuttle s) {
        shuttleRepository.save(s);
    }
}
