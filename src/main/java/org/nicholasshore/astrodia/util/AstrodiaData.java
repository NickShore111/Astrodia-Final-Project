package org.nicholasshore.astrodia.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.nicholasshore.astrodia.models.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class AstrodiaData {

    static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(4);
    static String PASSWORD = encoder.encode("password");

    public static List<User> USERS = new ArrayList<>(Arrays.asList(
            new User("New", "User", "NewUser@mail.com", PASSWORD),
            new User("Admin", "User", "Admin@mail.com", PASSWORD),
            new User("Nick", "Shore", "Nick@mail.com", PASSWORD)
    ));
    public static List<AuthGroup> AUTH_GROUPS = new ArrayList<>(Arrays.asList(
            new AuthGroup("admin@mail.com", "ROLE_ADMIN"),
            new AuthGroup("admin@mail.com", "ROLE_USER"),
            new AuthGroup("nick@mail.com", "ROLE_USER")
    ));
    public static List<Spaceliner> SPACELINERS = new ArrayList<>(Arrays.asList(
        new Spaceliner("SPX", "SpaceX"),
        new Spaceliner("VGN", "Virgin Galactic"),
        new Spaceliner("BLO", "Blue Origin")
    ));
    static Spaceliner SPX = SPACELINERS.get(0);
    static Spaceliner VGN = SPACELINERS.get(1);
    static Spaceliner BLO = SPACELINERS.get(2);

    public static List<Shuttle> SHUTTLES = new ArrayList<>(Arrays.asList(
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

    public static List<Region> REGIONS = new ArrayList<>(Arrays.asList(
        new Region("ES", "Earth Surface"),
        new Region("EO", "Earth Orbit"),
        new Region("MO", "Moon"),
        new Region("MA", "Mars")
    ));

    static Region EARTH_SURFACE = REGIONS.get(0);
    static Region EARTH_ORBIT = REGIONS.get(1);
    static Region MOON = REGIONS.get(2);
    static Region MARS = REGIONS.get(3);

    public static List<Port> PORTS = new ArrayList<>(Arrays.asList(
            new Port("STB", "Starbase", "Boca Chica,TX", EARTH_SURFACE),
            new Port("TSP","Tanegashima Space Center", "Tanegashima Island, JP",  EARTH_SURFACE),
            new Port("AEO", "Axiom Station", "Lower Orbit", EARTH_ORBIT),
            new Port("GGU", "Gargantua-1 Station", "Upper Orbit", EARTH_ORBIT),
            new Port("DCB","Deep Crater Base", "Peary Crater",  MOON),
            new Port("MPB", "Malapert Peak Base", "Malapert Mountain",MARS),
            new Port("MVS", "M.V. Spaceport", "Mawrth Vallis",MARS)
    ));

    static Port STB = PORTS.get(0);
    static Port TSP = PORTS.get(1);
    static Port AEO = PORTS.get(2);
    static Port GGU = PORTS.get(3);
    static Port DCB = PORTS.get(4);
    static Port MPB = PORTS.get(5);
    static Port MVS = PORTS.get(6);

    public static List<Pad> PADS = new ArrayList<Pad>(Arrays.asList(
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

}
