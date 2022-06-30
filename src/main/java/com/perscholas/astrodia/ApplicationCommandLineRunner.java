package com.perscholas.astrodia;

import com.perscholas.astrodia.repositories.AuthGroupRepository;
import com.perscholas.astrodia.util.AstrodiaData;
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
    UserService userService;
    AuthGroupRepository authRepo;
    @Autowired
    ApplicationCommandLineRunner(
            FlightService flightService,
            PadService padService,
            PortService portService,
            RegionService regionService,
            ShuttleService shuttleService,
            SpacelinerService spacelinerService,
            UserService userService,
        AuthGroupRepository authRepo)
    {
        this.flightService = flightService;
        this.padService = padService;
        this.portService = portService;
        this.regionService = regionService;
        this.shuttleService = shuttleService;
        this.spacelinerService = spacelinerService;
        this.userService = userService;
        this.authRepo = authRepo;
    }

    @PostConstruct
    public void postConstruct(){
        log.warn("================== Application CommandLine Runner ==================");
    }

    @Override
    public void run(String... args) throws Exception {
        FlightCreator flightCreator = new FlightCreator(AstrodiaData.SHUTTLES, AstrodiaData.PADS, 3);
        List<Flight> flights = flightCreator.getListOfFlights(100);

        for (Spaceliner s : AstrodiaData.SPACELINERS) {
            spacelinerService.saveOrUpdate(s);
        }
        for (Shuttle s : AstrodiaData.SHUTTLES) {
            shuttleService.saveOrUpdate(s);
        }
        for (Region r : AstrodiaData.REGIONS) {
            regionService.saveOrUpdate(r);
        }
        for (Port p : AstrodiaData.PORTS) {
            portService.saveOrUpdate(p);
        }
        for (Pad p : AstrodiaData.PADS) {
            padService.saveOrUpdate(p);
        }
        for (Flight f : flights) {
            flightService.saveOrUpdate(f);
        }
        for (User u : AstrodiaData.USERS) {
            userService.saveOrUpdate(u);
        }
        for (AuthGroup g : AstrodiaData.AUTH_GROUPS) {
            authRepo.save(g);
        }
    }
}
