package org.openmrs.module.openhmis.billableobjects.api.util;


import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObjectDataServiceTest;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataServiceTest;
import org.openmrs.module.openhmis.billableobjects.api.TestConstants;
import org.openmrs.module.openhmis.billableobjects.api.model.DrugOrderHandler;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;


public class BillingHandlerHelperTest extends BaseModuleWebContextSensitiveTest {
	private IBillingHandlerDataService billingHandlerService;
	
	@Before
	public void before() throws Exception {
		billingHandlerService = Context.getService(IBillingHandlerDataService.class);
		
		executeDataSet(TestConstants.CORE_DATASET);
		executeDataSet(IBillableObjectDataServiceTest.ENCOUNTER_DATASET);
		executeDataSet(IBillingHandlerDataServiceTest.HANDLERS_DATASET);
	}
	
	/**
	 * @see BillingHandlerHelper#getHandlerTypeNames()
	 * @verifies return all names in alphabetical order
	 */
	@Test
	public void getHandlerTypeNames_shouldReturnAllNamesInAlphabeticalOrder() throws Exception {
		List<String> names = BillingHandlerHelper.getHandlerTypeNames();
		Assert.assertNotNull(names);
		Assert.assertTrue(names.size() == 2);
		Assert.assertEquals(DrugOrderHandler.class.getSimpleName(), names.get(0));
	}

	/**
	 * @see BillingHandlerHelper#getActivelyHandledClasses()
	 * @verifies return currently handled classes
	 */
	@Test
	public void getActivelyHandledClasses_shouldReturnCurrentlyHandledClasses() throws Exception {
		Set<Class<?>> classes = BillingHandlerHelper.getActivelyHandledClasses();
		Assert.assertNotNull(classes);
		Assert.assertEquals(2, classes.size());
		Assert.assertTrue(classes.contains(Encounter.class));
		
		billingHandlerService.purge(billingHandlerService.getById(1));
		
		classes = BillingHandlerHelper.getActivelyHandledClasses();
		Assert.assertEquals(1, classes.size());
		Assert.assertFalse(classes.contains(DrugOrder.class));
	}
}