package com.perscholas.astrodia.services;

import com.perscholas.astrodia.models.Region;
import com.perscholas.astrodia.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {
    private final RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    List<Region> findAll() { return regionRepository.findAll(); }

    public void saveOrUpdate(Region r) {
        regionRepository.save(r);
    }
}
