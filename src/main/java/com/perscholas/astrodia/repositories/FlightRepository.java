package com.perscholas.astrodia.repositories;

import com.perscholas.astrodia.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    List<Flight> findAll();
    List<Flight> findByOrderByDeparting();


    @Query(value = """
            SELECT * FROM flights
            JOIN pads AS departurePad ON flights.launch_pad_id = departurePad.id
            JOIN ports AS departurePort ON departurePad.port_id = departurePort.id
            JOIN pads AS arrivalPad ON flights.arrival_pad_id = arrivalPad.id
            JOIN ports AS arrivalPort ON arrivalPad.port_id = arrivalPort.id
            JOIN regions AS departureRegion ON departurePort.region_id = departureRegion.id
            JOIN regions AS arrivalRegion ON arrivalPort.region_id = arrivalRegion.id
            WHERE departureRegion.id = :departureRegion
            AND arrivalRegion.id = :arrivalRegion
            AND DATE_FORMAT(DATE(flights.departing), '%m/%d/%Y') >= :departureDate
            ORDER BY flights.departing asc
            """, nativeQuery = true)
    List<Flight> findFlightsByRegionsAndDepartureDate(
            @Param("departureRegion") String departureRegion,
            @Param("arrivalRegion") String arrivalRegion,
            @Param("departureDate") String departureDate);

    @Query(value = """
            SELECT * FROM flights
            JOIN pads AS departurePad ON flights.launch_pad_id = departurePad.id
            JOIN ports AS departurePort ON departurePad.port_id = departurePort.id
            JOIN pads AS arrivalPad ON flights.arrival_pad_id = arrivalPad.id
            JOIN ports AS arrivalPort ON arrivalPad.port_id = arrivalPort.id
            JOIN regions AS departureRegion ON departurePort.region_id = departureRegion.id
            JOIN regions AS arrivalRegion ON arrivalPort.region_id = arrivalRegion.id
            WHERE departureRegion.id = ?1
            AND arrivalRegion.id = ?2
            AND DATE_FORMAT(DATE(flights.arriving), '%m/%d/%Y') <= ?3
            ORDER BY flights.arriving desc
            """, nativeQuery = true)
    List<Flight> findFlightsByRegionsAndArrivalDate(String departing, String arriving, String departureDate);

//TODO: NEED TO UPDATE QUERY FOR DEPARTURE RANGE
    @Query(value = """
            SELECT * FROM flights
            JOIN pads AS departurePad ON flights.launch_pad_id = departurePad.id
            JOIN ports AS departurePort ON departurePad.port_id = departurePort.id
            JOIN pads AS arrivalPad ON flights.arrival_pad_id = arrivalPad.id
            JOIN ports AS arrivalPort ON arrivalPad.port_id = arrivalPort.id
            JOIN regions AS departureRegion ON departurePort.region_id = departureRegion.id
            JOIN regions AS arrivalRegion ON arrivalPort.region_id = arrivalRegion.id
            WHERE departureRegion.id = ?1
            AND arrivalRegion.id = ?2
            AND DATE_FORMAT(DATE(flights.departing), '%m/%d/%Y') = ?3
            """, nativeQuery = true)
    List<Flight> findFlightsByRegionsAndDepartureRange(String departing, String arriving, String departureDate);


    @Query(value = """
            SELECT flights.* FROM flights
            JOIN shuttle_pads ON shuttle_pads.id = flights.launch_pad_id
            JOIN shuttle_ports ON shuttle_ports.id = shuttle_pads.shuttle_port_id
            JOIN regions ON regions.id = shuttle_ports.region_id
            WHERE regions.name = ?1
            """, nativeQuery = true)
    List<Flight> findFlightsByRegionDeparture(String region);

    @Query(value = "SELECT * FROM flights WHERE DATE_FORMAT(DATE(flights.departing), '%m/%d/%Y') = ?1", nativeQuery = true)
    List<Flight> findFlightsByDeparture(String date);


    @Query(value = """
            SELECT * FROM flights
            JOIN pads AS departurePad ON flights.launch_pad_id = departurePad.id
            JOIN ports AS departurePort ON departurePad.port_id = departurePort.id
            JOIN pads AS arrivalPad ON flights.arrival_pad_id = arrivalPad.id
            JOIN ports AS arrivalPort ON arrivalPad.port_id = arrivalPort.id
            WHERE departurePort.id = ?1
            AND arrivalPort.id = ?2
            AND DATE_FORMAT(DATE(flights.departing), '%m/%d/%Y') BETWEEN ?3 AND ?4;
            """, nativeQuery = true)
    List<Flight> findFlightsByPortsAndDateRange(String fromPort, String toPort, String departureDate, String arrivalDate);

}