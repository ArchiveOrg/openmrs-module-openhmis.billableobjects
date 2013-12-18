package org.openmrs.module.openhmis.billableobjects.api.impl;


import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObjectDataServiceTest;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObjectsService;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataServiceTest;
import org.openmrs.module.openhmis.billableobjects.api.TestConstants;
import org.openmrs.module.openhmis.billableobjects.api.model.DrugOrderHandler;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

import static org.mockito.Mockito.*;

public class BillingHandlerDataServiceImplTest extends BaseModuleWebContextSensitiveTest {
	private IBillingHandlerDataService billingHandlerDataService;
	private IBillableObjectsService mockedObjectsService;
	
	@Before
	public void before() throws Exception {
		billingHandlerDataService = Context.getService(IBillingHandlerDataService.class);
		mockedObjectsService = mock(BillableObjectsServiceImpl.class);
		
		executeDataSet(TestConstants.CORE_DATASET);
		executeDataSet(IBillingHandlerDataServiceTest.HANDLERS_DATASET);
		executeDataSet(IBillableObjectDataServiceTest.ENCOUNTER_DATASET);
	}
		
	/**
	 * @see BillingHandlerDataServiceImpl#save(BaseBillingHandler)
	 * @verifies rebind listener if object was new
	 */
	@Test
	public void save_shouldRebindListenerIfObjectWasNew() throws Exception {
		billingHandlerDataService.setBillableObjectsService(mockedObjectsService);
		DrugOrderHandler newHandler = new DrugOrderHandler();
		newHandler.setName("Test");
		billingHandlerDataService.save(newHandler);
		verify(mockedObjectsService).rebindListenerForAllHandlers();
	}
	
	/**
	 * @see BillingHandlerDataServiceImpl#save(BaseBillingHandler)
	 * @verifies not rebind listener when updating
	 */
	@Test
	public void save_shouldNotRebindListenerWhenUpdating() throws Exception {
		billingHandlerDataService.setBillableObjectsService(mockedObjectsService);
		DrugOrderHandler existingHandler = (DrugOrderHandler) billingHandlerDataService.getById(1);
		existingHandler.setName("Test");
		billingHandlerDataService.save(existingHandler);
		verify(mockedObjectsService, never()).rebindListenerForAllHandlers();
	}

	/**
	 * @see BillingHandlerDataServiceImpl#retire(BaseBillingHandler,String)
	 * @verifies rebind listener
	 */
	@Test
	public void retire_shouldRebindListener() throws Exception {
		billingHandlerDataService.setBillableObjectsService(mockedObjectsService);
		DrugOrderHandler existingHandler = (DrugOrderHandler) billingHandlerDataService.getById(1);
		billingHandlerDataService.retire(existingHandler, "No reason");
		verify(mockedObjectsService).rebindListenerForAllHandlers();
	}

	/**
	 * @see BillingHandlerDataServiceImpl#unretire(BaseBillingHandler)
	 * @verifies rebind listener
	 */
	@Test
	public void unretire_shouldRebindListener() throws Exception {
		DrugOrderHandler existingHandler = (DrugOrderHandler) billingHandlerDataService.getById(1);
		billingHandlerDataService.retire(existingHandler, "No reason");
		billingHandlerDataService.setBillableObjectsService(mockedObjectsService);
		billingHandlerDataService.unretire(existingHandler);

		verify(mockedObjectsService).rebindListenerForAllHandlers();
	}

}