package com.perscholas.astrodia.repositories;

import com.perscholas.astrodia.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    List<Flight> findAll();
    List<Flight> findByOrderByDeparting();
    Flight findById(int id);

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
            WHERE departureRegion.id = :departureRegion
                AND arrivalRegion.id = :arrivalRegion
                AND DATE_FORMAT(DATE(flights.arriving), '%m/%d/%Y') <= :arrivalDate
                    ORDER BY flights.arriving desc
                    """, nativeQuery = true)
    List<Flight> findFlightsByRegionsAndArrivalDate(
            @Param("departureRegion") String departureRegion,
            @Param("arrivalRegion") String arrivalRegion,
            @Param("arrivalDate") String arrivalDate);

    @Query(value = """
            SELECT * FROM flights
            JOIN pads AS departurePad ON flights.launch_pad_id = departurePad.id
            JOIN ports AS departurePort ON departurePad.port_id = departurePort.id
            JOIN pads AS arrivalPad ON flights.arrival_pad_id = arrivalPad.id
            JOIN ports AS arrivalPort ON arrivalPad.port_id = arrivalPort.id
            WHERE departurePort.id = :departurePort
                AND arrivalPort.id = :arrivalPort
                AND DATE_FORMAT(DATE(flights.departing), '%m/%d/%Y') = :departureDate
                """, nativeQuery = true)
    List<Flight> findFlightsByPortsAndDepartureDate(
            @Param("departurePort") String departing,
            @Param("arrivalPort") String arriving,
            @Param("departureDate") String departureDate);

    @Query(value = """
            SELECT * FROM flights
            JOIN pads AS departurePad ON flights.launch_pad_id = departurePad.id
            JOIN ports AS departurePort ON departurePad.port_id = departurePort.id
            JOIN pads AS arrivalPad ON flights.arrival_pad_id = arrivalPad.id
            JOIN ports AS arrivalPort ON arrivalPad.port_id = arrivalPort.id
            WHERE departurePort.id = :departurePort
                AND arrivalPort.id = :arrivalPort
                AND DATE_FORMAT(DATE(flights.arriving), '%m/%d/%Y') = :arrivalDate
                """, nativeQuery = true)
    List<Flight> findFlightsByPortsAndArrivalDate(
            @Param("departurePort") String departing,
            @Param("arrivalPort") String arriving,
            @Param("arrivalDate") String arrivalDate);

    //TODO: UNDER CONSTRUCTION
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
    List<Flight> findFlightsByRegionsAndDepartureDateRange(String departing, String arriving, String departureDate);

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

}