package com.perscholas.astrodia.repositories;

import com.perscholas.astrodia.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    List<Flight> findAll();
    List<Flight> findByOrderByDeparting();


    @Query(value = "SELECT flights.* FROM flights"
            + " JOIN shuttle_pads ON shuttle_pads.id = flights.launch_pad_id"
            + " JOIN shuttle_ports ON shuttle_ports.id = shuttle_pads.shuttle_port_id"
            + " JOIN regions ON regions.id = shuttle_ports.region_id"
            + " WHERE regions.name = ?1", nativeQuery = true)
    List<Flight> findFlightsByRegionDeparture(String region);

    @Query(value = "SELECT * FROM flights WHERE DATE_FORMAT(DATE(flights.departing), '%m/%d/%Y') = ?1", nativeQuery = true)
    List<Flight> findFlightsByDeparture(String date);

    @Query(value = "SELECT * FROM flights f WHERE f.launchPort = ?1 AND f.arrivalPort = ?2 AND DATE_FORMAT(DATE(f.departing), '%m/%d/%Y') = ?3", nativeQuery = true)
    List<Flight> findFlightsByPortsAndDepartureDate(String fromPort, String toPort, String departureDate);

    @Query(value = "SELECT * FROM flights f WHERE f.launchPort = ?1 AND f.arrivalPort = ?2 AND DATE_FORMAT(DATE(f.arriving), '%m/%d/%Y') = ?3", nativeQuery = true)
    List<Flight> findFlightsByPortsAndArrivalDate(String fromPort, String toPort, String arrivalDate);
}