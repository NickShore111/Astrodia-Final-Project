package com.perscholas.astrodia.repositories;

import com.perscholas.astrodia.models.Port;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortRepository extends JpaRepository<Port, String> {
    List<Port> findAll();
    Optional<Port> findById(String id);
    Optional<Port> findByName(String name);
    Optional<Port> findByLocation(String location);

}
