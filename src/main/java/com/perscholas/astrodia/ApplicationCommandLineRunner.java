package com.perscholas.astrodia;

import com.perscholas.astrodia.util.FlightCreator;
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
    @Autowired
    ApplicationCommandLineRunner(
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
    Spaceliner SPX = new Spaceliner("SPX", "SpaceX");
    Spaceliner VGN = new Spaceliner("VGN", "Virgin Galactic");
    Spaceliner BLO =  new Spaceliner("BLO", "Blue Origin");

    Region EarthSurface = new Region("ES", "Earth Surface");
    Region EarthOrbit = new Region("EO", "Earth Orbit");
    Region Moon = new Region("MO", "Moon");
    Region Mars = new Region("MA", "Mars");

    Port STB = new Port("STB", "Starbase", "Boca Chica,TX", EarthSurface);
    Port TSP = new Port("TSP","Tanegashima Space Center", "Tanegashima Island, JP",  EarthSurface);
    Port AEO = new Port("AEO", "Axiom Station", "Lower Orbit", EarthOrbit);
    Port GGU = new Port("GGU", "Gargantua-1 Station", "Upper Orbit", EarthOrbit);
    Port DCB = new Port("DCB","Deep Crater Base", "Peary Crater",  Moon);
    Port MPB = new Port("MPB", "Malapert Peak Base", "Malapert Mountain",Mars);
    Port MVS = new Port("MVS", "M.V. Spaceport", "Mawrth Vallis",Mars);

    List<Spaceliner> spaceliners = new ArrayList<Spaceliner>(Arrays.asList(
            SPX, VGN, BLO
    ));

    List<Shuttle> shuttles = new ArrayList<Shuttle>(Arrays.asList(
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

    List<Region> regions = new ArrayList<Region>(Arrays.asList(
            EarthSurface, EarthOrbit, Moon, Mars
    ));

    List<Port> ports = new ArrayList<Port>(Arrays.asList(
            STB, GGU, TSP, AEO, DCB, MPB, MVS
    ));

    List<Pad> pads = new ArrayList<Pad>(Arrays.asList(
            new Pad("X1", STB),
            new Pad("X2", STB),
            new Pad("T1", TSP),
            new Pad("T2", TSP),
            new Pad("G1", AEO),
            new Pad("G2", AEO),
            new Pad("J1", GGU),
            new Pad("J2", GGU),
            new Pad("P1", DCB),
            new Pad("P2", DCB),
            new Pad("M1", MPB),
            new Pad("M2", MPB),
            new Pad("V1", MVS),
            new Pad("V2", MVS)
    ));

    @PostConstruct
    public void postConstruct(){
        log.warn("================== Application CommandLine Runner ==================");
    }

    @Override
    public void run(String... args) throws Exception {
        FlightCreator flightCreator = new FlightCreator(shuttles, pads, 7);
        List<Flight> flights = flightCreator.getListOfFlights(50);

        for (Spaceliner s : spaceliners) {
            spacelinerService.saveOrUpdate(s);
        }
        for (Shuttle s : shuttles) {
            shuttleService.saveOrUpdate(s);
        }
        for (Region r : regions) {
            regionService.saveOrUpdate(r);
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
