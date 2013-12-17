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
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerService;
import org.openmrs.module.openhmis.billableobjects.api.TestConstants;
import org.openmrs.module.openhmis.billableobjects.api.impl.BillingHandlerServiceImpl;
import org.openmrs.module.openhmis.billableobjects.api.model.DrugOrderHandler;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;


public class BillingHandlerHelperTest extends BaseModuleWebContextSensitiveTest {
	private IBillingHandlerService billingHandlerService;
	private IBillingHandlerDataService billingHandlerDataService;
	
	@Before
	public void before() throws Exception {
		billingHandlerService = Context.getService(IBillingHandlerService.class);
		billingHandlerDataService = Context.getService(IBillingHandlerDataService.class);
		
		executeDataSet(TestConstants.CORE_DATASET);
		executeDataSet(IBillableObjectDataServiceTest.ENCOUNTER_DATASET);
		executeDataSet(IBillingHandlerDataServiceTest.HANDLERS_DATASET);
	}
	
	/**
	 * @see BillingHandlerServiceImpl#getHandlerTypeNames()
	 * @verifies return all names in alphabetical order
	 */
	@Test
	public void getHandlerTypeNames_shouldReturnAllNamesInAlphabeticalOrder() throws Exception {
		List<String> names = billingHandlerService.getHandlerTypeNames();
		Assert.assertNotNull(names);
		Assert.assertTrue(names.size() == 2);
		Assert.assertEquals(DrugOrderHandler.class.getSimpleName(), names.get(0));
	}

	/**
	 * @see BillingHandlerServiceImpl#getActivelyHandledClasses()
	 * @verifies return currently handled classes
	 */
	@Test
	public void getActivelyHandledClasses_shouldReturnCurrentlyHandledClasses() throws Exception {
		Set<Class<?>> classes = billingHandlerService.getActivelyHandledClasses();
		Assert.assertNotNull(classes);
		Assert.assertEquals(2, classes.size());
		Assert.assertTrue(classes.contains(Encounter.class));
		
		billingHandlerDataService.purge(billingHandlerDataService.getById(1));
		
		classes = billingHandlerService.getActivelyHandledClasses();
		Assert.assertEquals(1, classes.size());
		Assert.assertFalse(classes.contains(DrugOrder.class));
	}
}