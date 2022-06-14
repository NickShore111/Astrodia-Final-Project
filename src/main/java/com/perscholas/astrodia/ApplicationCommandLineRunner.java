package com.perscholas.astrodia;

import com.perscholas.astrodia.Util.FlightCreator;
import com.perscholas.astrodia.models.*;
import com.perscholas.astrodia.services.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Component @Slf4j
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class ApplicationCommandLineRunner implements CommandLineRunner {

    FlightService flightService;
    PadService padService;
    PortService portService;
    RegionService regionService;
    ShuttleService shuttleService;
    SpacelinerService spacelinerService;
    Spaceliner SPX = new Spaceliner("SPX", "SpaceX");
    Spaceliner VGN = new Spaceliner("VGN", "Virgin Galactic");
    Spaceliner BLO =  new Spaceliner("BLO", "Blue Origin");

    Region NA = new Region("NA", "North America");
    Region AS = new Region("AS", "Asia");
    Region EO = new Region("EO", "Earth Orbit");
    Region MO = new Region("MO", "Moon");
    Region MA = new Region("MA", "Mars");

    Port stb = new Port(1,  "Starbase", "Boca Chica,TX",NA);
    Port jfk = new Port(2, "J.F.K. Space Center", "Cape Canaveral,FL", NA);
    Port tane = new Port(3,"Tanegashima Space Center","Tanegashima Island, JP",  AS);
    Port axiom = new Port(4,"Axiom Staton", "Geostionanary Earth Orbit", EO);
    Port lgw = new Port(5, "Lunar Gateway", "Low Earth Orbit",EO);
    Port dcb = new Port(6,"Deep Crater Base","Peary Crater",  MO);
    Port mpb = new Port(7, "Malapert Peak Base", "Malapert Mountain",MA);
    Port mvs = new Port(8, "M.V. Spaceport", "Mawrth Vallis",MA);

    private List<Spaceliner> spaceliners = new ArrayList<Spaceliner>(Arrays.asList(
            SPX, VGN, BLO
    ));

    private List<Shuttle> shuttles = new ArrayList<Shuttle>(Arrays.asList(
            new Shuttle( "DR", "Dragon", 8, SPX),
            new Shuttle("RD", "Red Dragon", 12, SPX),
            new Shuttle("DX","Dragon XL", 16, SPX),
            new Shuttle("VU", "VSS Unity", 6, VGN),
            new Shuttle("VM", "VSS Imagine", 12, VGN),
            new Shuttle("VN","VSS Inspire", 14, VGN),
            new Shuttle("NS", "New Shepard", 6, BLO),
            new Shuttle("NG", "New Glenn", 8, BLO),
            new Shuttle("NA", "New Armstrong", 10, BLO),
            new Shuttle("BM", "Blue Moon", 12, BLO)
    ));


    private List<Region> regions = new ArrayList<Region>(Arrays.asList(
            NA, AS, EO, MO, MA
    ));


    List<Port> ports = new ArrayList<Port>(Arrays.asList(
            stb, jfk, tane, axiom, lgw, dcb, mpb, mvs
    ));

    private List<Pad> pads = new ArrayList<Pad>(Arrays.asList(
            new Pad("X1", stb),
            new Pad("X2", stb),
            new Pad("C1", jfk),
            new Pad("C2", jfk),
            new Pad("T1", tane),
            new Pad("T2", tane),
            new Pad("G1", axiom),
            new Pad("G2", axiom),
            new Pad("L1", lgw),
            new Pad("L2", lgw),
            new Pad("P1", dcb),
            new Pad("P2", dcb),
            new Pad("M1", mpb),
            new Pad("M2", mpb),
            new Pad("V1", mvs),
            new Pad("V2", mvs)
    ));

    @Autowired
    public ApplicationCommandLineRunner(
            FlightService flightService,
            PadService padService,
            PortService portService,
            RegionService regionService,
            ShuttleService shuttleService,
            SpacelinerService spacelinerService)
    {
        this.flightService = flightService;
        this.padService = padService;
        this.portService = portService;
        this.regionService = regionService;
        this.shuttleService = shuttleService;
        this.spacelinerService = spacelinerService;
    }
    @PostConstruct
    public void postConstruct(){
        log.warn("================== Application CommandLine Runner ==================");
    }

    @Override
    public void run(String... args) throws Exception {
        FlightCreator flightCreator = new FlightCreator(shuttles, pads);
        List<Flight> flights = flightCreator.getListOfFlights(10);

        for (Region r : regions) {
            regionService.saveOrUpdate(r);
        }
        for (Spaceliner s : spaceliners) {
            spacelinerService.saveOrUpdate(s);
        }
        for (Shuttle s : shuttles) {
            shuttleService.saveOrUpdate(s);
        }
        for (Port p : ports) {
            portService.saveOrUpdate(p);
        }
        for (Pad p : pads) {
            padService.saveOrUpdate(p);
        }
        for (Flight f : flights) {
            flightService.saveOrUpdate(f);
        }

    }
}
