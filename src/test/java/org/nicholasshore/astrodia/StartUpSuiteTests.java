package org.nicholasshore.astrodia;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.nicholasshore.astrodia.controllers.RestControllerTest;

@Suite
@SelectClasses({
        SmokeTest.class,
        RestControllerTest.class
})
public class StartUpSuiteTests {
}
