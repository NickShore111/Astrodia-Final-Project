package com.perscholas.astrodia.services;

import com.perscholas.astrodia.models.*;
import com.perscholas.astrodia.repositories.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service @Slf4j
public class FlightService {

    @PersistenceContext
    private EntityManager em;

    public List<Flight> findFlightsBySelectionCriteria(
            String[] spacelinerList,
            String[] shuttleList,
            String departing,
            String[] departureRegionList,
            String[] departurePortList,
            String arriving,
            String[] arrivalRegionList,
            String[] arrivalPortList
    ) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Flight> c = cb.createQuery(Flight.class);
        Root<Flight> flight = c.from(Flight.class);
        c.select(flight);
//        c.distinct(true);

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

//TODO: Dates arriving and departing selectino added to criteria builder
//        if (arriving != null) {
//            ParameterExpression<String> p =
//                    cb.parameter(String.class, "arriving");
//            criteria.add(cb.equal(arriving.get("arriving"),p));
//        }

        if (spacelinerList != null) {
            for (int i = 0; i < spacelinerList.length; i++){
                ParameterExpression<String> p =
                    cb.parameter(String.class, "spaceliner" + i);
            criteria.add(cb.equal(spaceliner.get("id"), p));
            }
        }
        if (shuttleList != null) {
            for (int i = 0; i < shuttleList.length; i++){
                ParameterExpression<String> p =
                        cb.parameter(String.class, "shuttle" + i);
                criteria.add(cb.equal(shuttle.get("id"), p));
            }
        }
        if (departurePortList != null) {
            for (int i = 0; i < departurePortList.length; i++){
                ParameterExpression<String> p =
                        cb.parameter(String.class, "departurePort" + i);
                criteria.add(cb.equal(departurePort.get("id"), p));
            }
        }
        if (departureRegionList != null) {
            for (int i = 0; i < departureRegionList.length; i++){
                ParameterExpression<String> p =
                        cb.parameter(String.class, "departureRegion" + i);
                criteria.add(cb.equal(departureRegion.get("id"), p));
            }
        }
        if (arrivalPortList != null) {
            for (int i = 0; i < arrivalPortList.length; i++) {
                ParameterExpression<String> p =
                        cb.parameter(String.class, "arrivalPort" + i);
                criteria.add(cb.equal(arrivalPort.get("id"), p));
            }
        }
        if (arrivalRegionList != null) {
            for (int i = 0; i < arrivalRegionList.length; i++){
                ParameterExpression<String> p =
                        cb.parameter(String.class, "arrivalRegion" + i);
                criteria.add(cb.equal(arrivalRegion.get("id"), p));
            }
        }

        if (criteria.size() == 0) {
            return this.findAll();
        } else if (criteria.size() == 1) {
            c.where(criteria.get(0));
        } else {
            c.where(cb.or(criteria.toArray(new Predicate[0])));
        }

        TypedQuery<Flight> q = em.createQuery(c);
        if (spacelinerList != null) {
            int id = 0;
            for (String s : spacelinerList) {
                q.setParameter("spaceliner" + id++, s);
            }
        }
        if (shuttleList != null) {
            int id = 0;
            for (String s : shuttleList) {
                q.setParameter("shuttle" + id++, s);
            }
        }
        if (departureRegionList != null) {
            int id = 0;
            for (String s : departureRegionList) {
                q.setParameter("departureRegion" + id++, s);
            }
        }
        if (departurePortList != null) {
            int id = 0;
            for (String s : departurePortList) {
                q.setParameter("departurePort" + id++, s);
            }
        }
        if (arrivalRegionList != null) {
            int id = 0;
            for (String s : arrivalRegionList) {
                q.setParameter("arrivalRegion" + id++, s);
            }
        }
        if (arrivalPortList != null) {
            int id = 0;
            for (String s : arrivalPortList) {
                q.setParameter("arrivalPort" + id++, s);
            }
        }

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

    public List<Flight> findFlightsByPortsAndDepartureDate(String departurePort, String arrivalPort, String departureDate, String arrivalDate) {
        return flightRepository.findFlightsByPortsAndDateRange(departurePort, arrivalPort, departureDate, arrivalDate);
    }

    public List<Flight> findFlightByRegionsAndDeparture(String departing, String arriving, String departureDateStr) {
        return flightRepository.findFlightByRegionsAndDeparture(departing, arriving, departureDateStr);
    }
}
