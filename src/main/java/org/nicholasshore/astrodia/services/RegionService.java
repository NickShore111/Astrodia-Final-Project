package org.nicholasshore.astrodia.services;

import org.nicholasshore.astrodia.models.Region;
import org.nicholasshore.astrodia.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    private final RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> findAll() { return regionRepository.findAll(); }

    public void saveOrUpdate(Region r) {
        regionRepository.save(r);
    }

    public Optional<Region> findById(String id) { return regionRepository.findById(id); }
}
