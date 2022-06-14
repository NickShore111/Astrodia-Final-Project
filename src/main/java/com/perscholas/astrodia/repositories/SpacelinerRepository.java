package com.perscholas.astrodia.repositories;

import com.perscholas.astrodia.models.Spaceliner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpacelinerRepository extends JpaRepository<Spaceliner, Integer> {

    List<Spaceliner> findAll();

    Optional<Spaceliner> findById(String id);

    Optional<Spaceliner> findByName(String name);
}
