package com.perscholas.astrodia.repositories;

import com.perscholas.astrodia.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    List<Flight> findAll();

    @Query(value = "SELECT flights.* FROM flights"
            + " JOIN shuttle_pads ON shuttle_pads.id = flights.launch_pad_id"
            + " JOIN shuttle_ports ON shuttle_ports.id = shuttle_pads.shuttle_port_id"
            + " JOIN regions ON regions.id = shuttle_ports.region_id"
            + " WHERE regions.name = ?1", nativeQuery = true)
    List<Flight> findFlightsByRegionDeparture(String region);
}
