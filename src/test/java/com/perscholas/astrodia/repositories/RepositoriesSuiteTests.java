package com.perscholas.astrodia.repositories;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        ShuttleRepositoryTests.class,
        SpacelinerRepositoryTests.class,
        RegionRepositoryTests.class,
        PortRepositoryTests.class,
        PadRepository.class})
public class RepositoriesSuiteTests {
}
