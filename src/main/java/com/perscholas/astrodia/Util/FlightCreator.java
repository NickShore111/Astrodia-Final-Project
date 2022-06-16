package com.perscholas.astrodia.Util;

import com.perscholas.astrodia.models.Flight;
import com.perscholas.astrodia.models.Pad;
import com.perscholas.astrodia.models.Shuttle;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.*;

@Slf4j
public class FlightCreator {
    private final List<Shuttle> shuttles;
    private final List<Pad> pads;
    private final Random rand = new Random();
    private int departureWindow = 30;

    private List<Flight> flightsList = new ArrayList<>();

    public FlightCreator(List<Shuttle> shuttles, List<Pad> pads) {
        this.shuttles = shuttles;
        this.pads = pads;
    }
    public FlightCreator(List<Shuttle> shuttles, List<Pad> pads, int departureWindow) {
        this.shuttles = shuttles;
        this.pads = pads;
        this.departureWindow = departureWindow;
    }

    public List<Flight> getListOfFlights(int numberOfFlights) {
        for (int i = 0; i < numberOfFlights; i++) {
            Flight f = this.createNewFlight();
            flightsList.add(f);
        }
        return flightsList;
    }
    private String createFlightCode(
            String shuttleSpacelinerId,
            String launchPadId,
            String arrivalPadId) {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_YEAR);
        return String.format("%s%d %s-%s", shuttleSpacelinerId, day, launchPadId, arrivalPadId);
    }

    private Flight createNewFlight() {
        // add random days, minute, hour to departure timestamp
        int randDay = rand.nextInt(departureWindow);
        int randMinute = rand.nextInt(60);
        int randHour = rand.nextInt(24);
        // create new timestamp object with current time
        Timestamp timestamp = new Timestamp(new Date().getTime());
        // create cal object instance
        Calendar cal = Calendar.getInstance();
        // set calendar object to now
        cal.setTimeInMillis(timestamp.getTime());
        // add random values to cal object
        cal.add(Calendar.DAY_OF_MONTH, randDay);
        cal.add(Calendar.MINUTE, randMinute);
        cal.add(Calendar.HOUR, randHour);
        // set timestamp object to new timestamp using future cal object
        Timestamp futureDeparture = new Timestamp(cal.getTime().getTime());
        int randomShuttleIdx = rand.nextInt(shuttles.size());
        int launchPadIdx = rand.nextInt(pads.size());
        int arrivalPadIdx = this.getArrivalPadIdx(launchPadIdx);
        Pad launchPad = pads.get(launchPadIdx);
        Pad arrivalPad = pads.get(arrivalPadIdx);
        // get future arrival time relative to location traveling to
        Timestamp futureArrival = this.getFutureArrival(futureDeparture, launchPad, arrivalPad);
        Shuttle shuttle = shuttles.get(randomShuttleIdx);
        String flightCode = this.createFlightCode(shuttle.getSpaceliner().getId(), launchPad.getId(), arrivalPad.getId());

        Flight newFlight = new Flight();
        newFlight.setFlightCode(flightCode);
        newFlight.setDeparting(futureDeparture);
        newFlight.setArriving(futureArrival);
        newFlight.setLaunchPad(launchPad);
        newFlight.setArrivalPad(arrivalPad);
        newFlight.setShuttle(shuttle);
        newFlight.setSeatsAvailable(shuttle.getPassengerCapacity());

        return newFlight;
    }

    private Timestamp getFutureArrival(Timestamp departureTimestamp, Pad launchPad, Pad arrivalPad) {
        String fromRegion = launchPad.getPort().getRegion().getId();
        String toRegion = arrivalPad.getPort().getRegion().getId();
        int randHour = 0;
        // Earth surface <to> Earth Orbit
        if (fromRegion.equals("ES") && toRegion.equals("EO") ||
                fromRegion.equals("EO") && toRegion.equals("ES")) {
            randHour = rand.nextInt(66) + 6; // 6 hours to 3 days
        } // Earth surface <to> Moon
        if (fromRegion.equals("ES") && toRegion.equals("MO") ||
                fromRegion.equals("MO") && toRegion.equals("ES")) {
            randHour = rand.nextInt(12) + 66; // 3 days on average, 12 hour window
        } // Earth surface <to> Mars
        if (fromRegion.equals("ES") && toRegion.equals("MA") ||
                fromRegion.equals("MA") && toRegion.equals("ES")) {
            randHour = rand.nextInt(593) + 93; // from 93 hours to 686 hours(farthest approach)
        }// Earth orbit <to> Moon
        if (fromRegion.equals("EO") && toRegion.equals("MO") ||
                fromRegion.equals("MO") && toRegion.equals("EO")) {
            randHour = rand.nextInt(24) + 24; // 1 to 2 days
        }// Earth orbit <to> Mars
        if (fromRegion.equals("EO") && toRegion.equals("MA") ||
                fromRegion.equals("MA") && toRegion.equals("EO")) {
            randHour = rand.nextInt(545) + 87; // 6hours - 48hours shorter travel
        }// Moon <to> Mars
        if (fromRegion.equals("MO") && toRegion.equals("MA") ||
                fromRegion.equals("MA") && toRegion.equals("MO")) {
            randHour = rand.nextInt(545) + 80; //
        }
        int randMinute = rand.nextInt(60);
        Calendar cal = Calendar.getInstance();
        // set calendar object to now
        cal.setTimeInMillis(departureTimestamp.getTime());
        // add random time to cal object
        cal.add(Calendar.MINUTE, randMinute);
        cal.add(Calendar.HOUR, randHour);
        // set timestamp object to new timestamp using future cal object
        return new Timestamp(cal.getTime().getTime());
    }

    private int getArrivalPadIdx(int launchPadIdx) {
        int randomIdx = rand.nextInt(pads.size());
        // avoid creating flights travelling to and from same region
        while (pads.get(randomIdx).getPort().getRegion() ==
                pads.get(launchPadIdx).getPort().getRegion()) {
            randomIdx = rand.nextInt(pads.size());
        }
        return randomIdx;
    }
}
