package com.fss.roo.pizzashop.domain;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Transactional
@RooIntegrationTest(entity = Topping.class)
public class ToppingIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private ToppingDataOnDemand dod;

	@Test
    public void testCountToppings() {
        Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", dod.getRandomTopping());
        long count = Topping.countToppings();
        Assert.assertTrue("Counter for 'Topping' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindTopping() {
        Topping obj = dod.getRandomTopping();
        Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Topping' failed to provide an identifier", id);
        obj = Topping.findTopping(id);
        Assert.assertNotNull("Find method for 'Topping' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Topping' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllToppings() {
        Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", dod.getRandomTopping());
        long count = Topping.countToppings();
        Assert.assertTrue("Too expensive to perform a find all test for 'Topping', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Topping> result = Topping.findAllToppings();
        Assert.assertNotNull("Find all method for 'Topping' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Topping' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindToppingEntries() {
        Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", dod.getRandomTopping());
        long count = Topping.countToppings();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Topping> result = Topping.findToppingEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'Topping' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Topping' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Topping obj = dod.getRandomTopping();
        Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Topping' failed to provide an identifier", id);
        obj = Topping.findTopping(id);
        Assert.assertNotNull("Find method for 'Topping' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyTopping(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'Topping' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        Topping obj = dod.getRandomTopping();
        Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Topping' failed to provide an identifier", id);
        obj = Topping.findTopping(id);
        boolean modified =  dod.modifyTopping(obj);
        Integer currentVersion = obj.getVersion();
        Topping merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Topping' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", dod.getRandomTopping());
        Topping obj = dod.getNewTransientTopping(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Topping' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Topping' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'Topping' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        Topping obj = dod.getRandomTopping();
        Assert.assertNotNull("Data on demand for 'Topping' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Topping' failed to provide an identifier", id);
        obj = Topping.findTopping(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'Topping' with identifier '" + id + "'", Topping.findTopping(id));
    }
}
