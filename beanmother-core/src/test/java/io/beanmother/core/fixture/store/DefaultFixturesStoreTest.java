package io.beanmother.core.fixture.store;

import io.beanmother.core.util.Location;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for {@link DefaultFixturesStore}
 */
public class DefaultFixturesStoreTest {

    DefaultFixturesStore fixtureStore;

    @Before
    public void setup() {
        fixtureStore = new DefaultFixturesStore();
    }

    @Test
    public void testAddLocation() throws IOException {
        String fixturePath = "fixtures/animals/pets";
        fixtureStore.addLocation(new Location(fixturePath));

        assertTrue(fixtureStore.getFixtureLocations().contains(new Location(fixturePath)));

        List<String> fileNames = new ArrayList<>();
        for(File file : fixtureStore.getFixtureFiles()) {
            fileNames.add(file.getName());
        }

        assertTrue(fileNames.contains("cat.yml"));
        assertTrue(fileNames.contains("dog.yml"));
    }

    @Test
    public void testAddMultipleLocations() throws IOException {
        fixtureStore.addLocation(new Location("fixtures/animals/pets"));
        Set<String> keys = fixtureStore.getFixtureMap().keySet();
        assertTrue(keys.contains("davi"));
        assertTrue(keys.contains("rooney"));
        assertTrue(keys.contains("ruru"));

        assertFalse(keys.contains("tiger"));  // tiger is not in pets/

        // add location and refresh
        fixtureStore.addLocation(new Location("fixtures/animals"));
        keys = fixtureStore.getFixtureMap().keySet();
        assertTrue(keys.contains("davi"));
        assertTrue(keys.contains("tiger"));
    }

    @Test
    public void testReset() throws IOException {
        fixtureStore.addLocation(new Location("fixtures/animals/pets"));

        fixtureStore.reset();
        assertTrue(fixtureStore.getFixtureLocations().isEmpty());
        assertTrue(fixtureStore.getFixtureFiles().isEmpty());
        assertTrue(fixtureStore.getFixtureMap().isEmpty());
    }

}