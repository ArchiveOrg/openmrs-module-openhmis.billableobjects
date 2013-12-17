package org.openmrs.module.openhmis.billableobjects.api.impl;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObjectDataServiceTest;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataServiceTest;
import org.openmrs.module.openhmis.billableobjects.api.TestConstants;
import org.openmrs.module.openhmis.billableobjects.api.model.DrugOrderHandler;
import org.openmrs.module.openhmis.billableobjects.api.util.BillableObjectsHelper;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(BillableObjectsHelper.class)
public class BillingHandlerDataServiceImplTest extends BaseModuleWebContextSensitiveTest {
	IBillingHandlerDataService billingHandlerService;
	
	@Before
	public void before() throws Exception {
		billingHandlerService = Context.getService(IBillingHandlerDataService.class);
		
		executeDataSet(TestConstants.CORE_DATASET);
		executeDataSet(IBillingHandlerDataServiceTest.HANDLERS_DATASET);
		executeDataSet(IBillableObjectDataServiceTest.ENCOUNTER_DATASET);
	}
	
	/**
	 * @see BillingHandlerDataServiceImpl#save(BaseBillingHandler)
	 * @verifies bind listener if object was new
	 */
	@Test
	public void save_shouldBindListenerIfObjectWasNew() throws Exception {
		PowerMockito.mockStatic(BillableObjectsHelper.class);
		DrugOrderHandler handler = new DrugOrderHandler();
		handler.setName("Test");
		billingHandlerService.save(handler);
		verify()
	}
}