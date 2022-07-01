package org.nicholasshore.astrodia.repositories;

import org.nicholasshore.astrodia.models.Shuttle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShuttleRepository extends JpaRepository<Shuttle, String> {
    List<Shuttle> findAll();
    Optional<Shuttle> findByName(String name);

}
