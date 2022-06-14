package com.perscholas.astrodia.Util;

import com.perscholas.astrodia.models.Flight;
import com.perscholas.astrodia.models.Pad;
import com.perscholas.astrodia.models.Shuttle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Slf4j
public class FlightCreator {
    private final List<Shuttle> shuttles;
    private final List<Pad> pads;
    private final Random rand = new Random();
    private final Calendar cal = Calendar.getInstance();
    private List<Flight> flightsList = new ArrayList<>();

    public FlightCreator(List<Shuttle> shuttles, List<Pad> pads) {
        this.shuttles = shuttles;
        this.pads = pads;
    }

    private Flight createNewFlight() {
        // add 30 random days to launch timestamp
        int randFuture = rand.nextInt(31);
        // create new timestamp object with current time
        Timestamp timestamp = new Timestamp(new Date().getTime());
        // set calendar object to now
        cal.setTimeInMillis(timestamp.getTime());
        // add 30 days to cal object
        cal.add(Calendar.DAY_OF_MONTH, randFuture);
        // set timestamp object to new timestamp using future cal object
        Timestamp futureDeparture = new Timestamp(cal.getTime().getTime());
        int randomShuttleIdx = rand.nextInt(shuttles.size());
        int launchPadIdx = rand.nextInt(pads.size());
        int arrivalPadIdx = this.getArrivalPadIdx(launchPadIdx);

        return new Flight(
                futureDeparture,
                this.getFutureArrival(futureDeparture),
                pads.get(launchPadIdx),
                pads.get(arrivalPadIdx),
                shuttles.get(randomShuttleIdx));
    }
    private Timestamp getFutureArrival(Timestamp timestamp) {
        // add 30 random days to arrival timestamp with min of 3 difference
        int randFuture = rand.nextInt(27) + 3;
        // set calendar object to now
        cal.setTimeInMillis(timestamp.getTime());
        // add 30 days to cal object
        cal.add(Calendar.DAY_OF_MONTH, randFuture);
        // set timestamp object to new timestamp using future cal object
        return new Timestamp(cal.getTime().getTime());
    }

    public List<Flight> getListOfFlights(int numberOfFlights) {
        for (int i = 0; i < numberOfFlights; i++) {
            Flight f = this.createNewFlight();
             flightsList.add(f);
        }
        return flightsList;
    }
    // Avoid launch pad and arrival pad being at the same Port
    private int getArrivalPadIdx(int launchPadIdx) {
        int randomIdx = rand.nextInt(pads.size());
        while ((Math.abs(randomIdx - launchPadIdx) <=2)) {
            randomIdx = rand.nextInt(pads.size());
        }
        return randomIdx;
    }
}
