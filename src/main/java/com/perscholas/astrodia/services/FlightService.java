package com.perscholas.astrodia.services;

import com.perscholas.astrodia.models.*;
import com.perscholas.astrodia.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    SpacelinerService spacelinerService;

    public List<Flight> findFlightsBySelection(String[] spacelinerList, String[] regionList, String[] portList, String departing, String arriving) {
//        Optional<Spaceliner> liner = spacelinerService.findById(spacelinerList[0]);
//        if (liner.isPresent()) {
//            sl = liner.get();
//        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Flight> c = cb.createQuery(Flight.class);
        Root<Flight> flight = c.from(Flight.class);
        c.select(flight);
        c.distinct(true);

        Join<Flight, Shuttle> shuttle =
                flight.join("shuttle", JoinType.LEFT);
        Join<Shuttle, Spaceliner> spaceliner =
                shuttle.join("spaceliner", JoinType.LEFT);
        Join<Flight, Pad> launchPad =
                flight.join("launchPad", JoinType.LEFT);
        Join<Flight, Pad> arrivalPad =
                flight.join("arrivalPad", JoinType.LEFT);
        Join<Pad, Port> departurePort =
                launchPad.join("port", JoinType.LEFT);
        Join<Pad, Port> arrivalPort =
                arrivalPad.join("port", JoinType.LEFT);
        Join<Port, Region> departureRegion =
                departurePort.join("region", JoinType.LEFT);
        Join<Port, Region> arrivalRegion =
                arrivalPort.join("region", JoinType.LEFT);


        List<Predicate> criteria = new ArrayList<Predicate>();

        if (spacelinerList != null) {
            ParameterExpression<String> p =
                    cb.parameter(String.class, "spaceliner");
            criteria.add(cb.equal(spaceliner.get("id"), p));
        }
        if (portList != null) {
            ParameterExpression<String> p =
                    cb.parameter(String.class, "port");
            criteria.add(cb.equal(departurePort.get("id"), p));
        }
        if (regionList != null) {
            ParameterExpression<String> p =
                    cb.parameter(String.class, "region");
            criteria.add(cb.equal(departureRegion.get("id"), p));
        }

        if (criteria.size() == 0) {
            return this.findAll();

        } else if (criteria.size() == 1) {
            c.where(criteria.get(0));
        } else {
            c.where(cb.and(criteria.toArray(new Predicate[0])));
        }

        TypedQuery<Flight> q = em.createQuery(c);
        if (spacelinerList != null) { q.setParameter("spaceliner", spacelinerList[0]); }
        if (regionList != null) { q.setParameter("region", regionList[0]); }
        if (portList != null) { q.setParameter("project", portList); }
//        if (city != null) { q.setParameter("city", city); }

        return q.getResultList();

    }

    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    public void saveOrUpdate(Flight flight) {
        flightRepository.save(flight);
    }

    public List<Flight> findFlightsByDeparture(String date) {
        return flightRepository.findFlightsByDeparture(date);
    }

    public List<Flight> findByOrderBy() { return flightRepository.findByOrderByDeparting(); }

    public List<Flight> findFlightsByPortsAndDepartureDate(String departurePort, String arrivalPort, String departureDate) {
        return flightRepository.findFlightsByPortsAndDepartureDate(departurePort, arrivalPort, departureDate);
    }

    public List<Flight> findFLightsByPortsAndArrivalDate(String departurePort, String arrivalPort, String arrivalDate) {
        return flightRepository.findFlightsByPortsAndArrivalDate(departurePort, arrivalPort, arrivalDate);
    }

}
