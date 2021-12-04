package com.example.viewmodelcounter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ExampleInstrumentedTest.class,
        LiveScoreDatabaseTest.class,
        ScoreDatabaseTest.class
})
public class RunAllTests {

}
