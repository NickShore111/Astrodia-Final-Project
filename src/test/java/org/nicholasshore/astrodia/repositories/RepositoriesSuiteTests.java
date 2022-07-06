package org.nicholasshore.astrodia.repositories;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.springframework.context.annotation.PropertySource;

@Suite
@SelectClasses({
        ShuttleRepositoryTests.class,
        SpacelinerRepositoryTests.class,
        RegionRepositoryTests.class,
        PortRepositoryTests.class,
        PadRepositoryTests.class})
public class RepositoriesSuiteTests {
}
