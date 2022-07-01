package org.nicholasshore.astrodia.repositories;

import org.nicholasshore.astrodia.models.Spaceliner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpacelinerRepository extends JpaRepository<Spaceliner, String> {

    List<Spaceliner> findAll();

    Optional<Spaceliner> findById(String id);

}
