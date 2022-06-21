package com.perscholas.astrodia.services;

import com.perscholas.astrodia.models.*;
import com.perscholas.astrodia.repositories.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

//  TODO: Using dates for parameters in dynamic query
//        if (arriving != null) {
//            ParameterExpression<Date> p =
//                    cb.parameter(Date.class, "arriving");
//                criteria.add(cb.lessThanOrEqual(flight.get("arriving"),p));
//        }
//        if (departing != null) {
//            ParameterExpression<Date> p =
//                    cb.parameter(Date.class, "departing");
//                criteria.add(cb.equal(flight.get("departing"),p));
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
            return this.findByOrderByDeparting();
        } else if (criteria.size() == 1) {
            c.where(criteria.get(0));
        } else {
            c.where(cb.or(criteria.toArray(new Predicate[0])));
        }
        log.warn("criteria size: " + criteria.size());

        TypedQuery<Flight> q = em.createQuery(c);

//  TODO: Departure and Arrival Date Selection Criteria
//        DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
//        if (arriving != null) {
//            String[] a = arriving.split("/");
//            String m = a[0], d = a[1], y = a[2];
//            String afs = String.format("%s-%s-%s",y, m, d);
//            q.setParameter("arriving", {ts afs});
//        } // MM/dd/yyyy
//        if (departing != null) {
//            DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
//            String currDate = departing.concat(" 00:00:00");
//            LocalDateTime localDateTime = LocalDateTime.from(formatDateTime.parse(currDate));
//            Timestamp ts = Timestamp.valueOf(localDateTime);
//  TODO:
//            String[] d = departing.split("/");
//            String month = d[0], day = d[1], year = d[2];
//            String depStr = String.format("%s-%s-%s",year, month, day);
//            Date date = new Date();
//            q.setParameter("departing", date, TemporalType.DATE);
//            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//            try {
//                Date date = dateFormat.parse(departing);
//                q.setParameter("departing", date, TemporalType.DATE);
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//        }
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

    public Flight findById(int id) { return flightRepository.findById(id); }

    public List<Flight> findByOrderByDeparting() { return flightRepository.findByOrderByDeparting(); }

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


    public List<Flight> findFlightsByPortsAndDateRange(String departurePort, String arrivalPort, String departureDate, String arrivalDate) {
        return flightRepository.findFlightsByPortsAndDepartureDateRange(departurePort, arrivalPort, departureDate, arrivalDate);
    }

    public List<Flight> findFlightsByRegionsAndDepartureDate(String departing, String arriving, String departureDate) {
        return flightRepository.findFlightsByRegionsAndDepartureDate(departing, arriving, departureDate);
    }

    public List<Flight> findFlightsByRegionsAndArrivalDate(String departing, String arriving, String arrivalDate) {
        return flightRepository.findFlightsByRegionsAndArrivalDate(departing, arriving, arrivalDate);
    }
}
