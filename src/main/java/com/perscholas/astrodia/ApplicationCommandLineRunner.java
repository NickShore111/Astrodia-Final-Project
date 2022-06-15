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
import java.util.ArrayList;
import java.util.Arrays;
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

    Region NorthAmerica = new Region("NA", "North America");
    Region Asia = new Region("Asia", "Asia");
    Region EarthOrbit = new Region("EarthOrbit", "Earth Orbit");
    Region Moon = new Region("Moon", "Moon");
    Region Mars = new Region("Mars", "Mars");

    Port STB = new Port(1,  "Starbase", "STB", "Boca Chica,TX", NorthAmerica);
    Port JFK = new Port(2, "J.F.K. Space Center", "JFK", "Cape Canaveral,FL", NorthAmerica);
    Port TSP = new Port(3,"Tanegashima Space Center", "TSP", "Tanegashima Island, JP",  Asia);
    Port AEO = new Port(4,"Axiom Station", "AEO", "Geostionanary Earth Orbit", EarthOrbit);
    Port LGW = new Port(5, "Lunar Gateway", "LGW", "Low Earth Orbit",EarthOrbit);
    Port DCB = new Port(6,"Deep Crater Base", "DCB", "Peary Crater",  Moon);
    Port MPB = new Port(7, "Malapert Peak Base", "MPB", "Malapert Mountain",Mars);
    Port MVS = new Port(8, "M.V. Spaceport", "MVS", "Mawrth Vallis",Mars);

    private List<Spaceliner> spaceliners = new ArrayList<Spaceliner>(Arrays.asList(
            SPX, VGN, BLO
    ));

    private List<Shuttle> shuttles = new ArrayList<Shuttle>(Arrays.asList(
            new Shuttle( "DR", "Dragon", 8, SPX),
            new Shuttle("RD", "Red Dragon", 12, SPX),
            new Shuttle("DX","Dragon XL", 16, SPX),
            new Shuttle("VU", "VSS Unity", 12, VGN),
            new Shuttle("VM", "VSS Imagine", 12, VGN),
            new Shuttle("VN","VSS Inspire", 14, VGN),
            new Shuttle("NS", "New Shepard", 6, BLO),
            new Shuttle("NG", "New Glenn", 8, BLO),
            new Shuttle("NA", "New Armstrong", 10, BLO),
            new Shuttle("BM", "Blue Moon", 12, BLO)
    ));

    private List<Region> regions = new ArrayList<Region>(Arrays.asList(
            NorthAmerica, Asia, EarthOrbit, Moon, Mars
    ));

    List<Port> ports = new ArrayList<Port>(Arrays.asList(
            STB, JFK, TSP, AEO, LGW, DCB, MPB, MVS
    ));

    private List<Pad> pads = new ArrayList<Pad>(Arrays.asList(
            new Pad("X1", STB),
            new Pad("X2", STB),
            new Pad("C1", JFK),
            new Pad("C2", JFK),
            new Pad("T1", TSP),
            new Pad("T2", TSP),
            new Pad("G1", AEO),
            new Pad("G2", AEO),
            new Pad("L1", LGW),
            new Pad("L2", LGW),
            new Pad("P1", DCB),
            new Pad("P2", DCB),
            new Pad("M1", MPB),
            new Pad("M2", MPB),
            new Pad("V1", MVS),
            new Pad("V2", MVS)
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
        FlightCreator flightCreator = new FlightCreator(shuttles, pads, 7);
        List<Flight> flights = flightCreator.getListOfFlights(20);

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
