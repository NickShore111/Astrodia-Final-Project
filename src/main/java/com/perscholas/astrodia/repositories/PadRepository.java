package com.perscholas.astrodia.repositories;

import com.perscholas.astrodia.models.Pad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PadRepository extends JpaRepository<Pad, Integer> {
    List<Pad> findAll();

    Optional<Pad> findById(String id);
}
