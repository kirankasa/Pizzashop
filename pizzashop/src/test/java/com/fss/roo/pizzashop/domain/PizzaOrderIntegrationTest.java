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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@Transactional
@Configurable
@RooIntegrationTest(entity = PizzaOrder.class)
public class PizzaOrderIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

	@Autowired
    private PizzaOrderDataOnDemand dod;

	@Test
    public void testCountPizzaOrders() {
        Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", dod.getRandomPizzaOrder());
        long count = PizzaOrder.countPizzaOrders();
        Assert.assertTrue("Counter for 'PizzaOrder' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFindPizzaOrder() {
        PizzaOrder obj = dod.getRandomPizzaOrder();
        Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to provide an identifier", id);
        obj = PizzaOrder.findPizzaOrder(id);
        Assert.assertNotNull("Find method for 'PizzaOrder' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'PizzaOrder' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAllPizzaOrders() {
        Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", dod.getRandomPizzaOrder());
        long count = PizzaOrder.countPizzaOrders();
        Assert.assertTrue("Too expensive to perform a find all test for 'PizzaOrder', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<PizzaOrder> result = PizzaOrder.findAllPizzaOrders();
        Assert.assertNotNull("Find all method for 'PizzaOrder' illegally returned null", result);
        Assert.assertTrue("Find all method for 'PizzaOrder' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindPizzaOrderEntries() {
        Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", dod.getRandomPizzaOrder());
        long count = PizzaOrder.countPizzaOrders();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<PizzaOrder> result = PizzaOrder.findPizzaOrderEntries(firstResult, maxResults);
        Assert.assertNotNull("Find entries method for 'PizzaOrder' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'PizzaOrder' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        PizzaOrder obj = dod.getRandomPizzaOrder();
        Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to provide an identifier", id);
        obj = PizzaOrder.findPizzaOrder(id);
        Assert.assertNotNull("Find method for 'PizzaOrder' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPizzaOrder(obj);
        Integer currentVersion = obj.getVersion();
        obj.flush();
        Assert.assertTrue("Version for 'PizzaOrder' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testMergeUpdate() {
        PizzaOrder obj = dod.getRandomPizzaOrder();
        Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to provide an identifier", id);
        obj = PizzaOrder.findPizzaOrder(id);
        boolean modified =  dod.modifyPizzaOrder(obj);
        Integer currentVersion = obj.getVersion();
        PizzaOrder merged = obj.merge();
        obj.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'PizzaOrder' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testPersist() {
        Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", dod.getRandomPizzaOrder());
        PizzaOrder obj = dod.getNewTransientPizzaOrder(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'PizzaOrder' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        Assert.assertNotNull("Expected 'PizzaOrder' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testRemove() {
        PizzaOrder obj = dod.getRandomPizzaOrder();
        Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'PizzaOrder' failed to provide an identifier", id);
        obj = PizzaOrder.findPizzaOrder(id);
        obj.remove();
        obj.flush();
        Assert.assertNull("Failed to remove 'PizzaOrder' with identifier '" + id + "'", PizzaOrder.findPizzaOrder(id));
    }
}
