package com.perscholas.astrodia.repositories;

import com.perscholas.astrodia.models.Shuttle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShuttleRepository extends JpaRepository<Shuttle, Integer> {
    List<Shuttle> findAll();

    Optional<Shuttle> findById(String id);

    Optional<Shuttle> findByName(String name);
}
