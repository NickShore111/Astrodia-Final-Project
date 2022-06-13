package com.perscholas.astrodia.repositories;

import com.perscholas.astrodia.models.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {
    List<Region> findAll();

    Optional<Region> findByName(String name);
}
