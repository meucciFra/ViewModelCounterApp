package com.example.viewmodelcounter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        ExampleUnitTest.class,
        ScoreUnitTest.class,
        ScoreWebServiceTest.class
})
public class RunAllTests {

}
