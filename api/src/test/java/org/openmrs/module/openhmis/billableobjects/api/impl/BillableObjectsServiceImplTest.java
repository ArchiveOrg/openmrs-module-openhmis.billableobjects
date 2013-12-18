package org.openmrs.module.openhmis.billableobjects.api.impl;


import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObjectDataServiceTest;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObjectsService;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataServiceTest;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerService;
import org.openmrs.module.openhmis.billableobjects.api.TestConstants;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

public class BillableObjectsServiceImplTest extends BaseModuleWebContextSensitiveTest{
	IBillableObjectsService billableObjectsService;
	IBillingHandlerService billingHandlerService;
	
	@Before
	public void before() throws Exception {
		billableObjectsService = Context.getService(IBillableObjectsService.class);
		billingHandlerService = Context.getService(IBillingHandlerService.class);
		
		executeDataSet(TestConstants.CORE_DATASET);
		executeDataSet(IBillingHandlerDataServiceTest.HANDLERS_DATASET);
		executeDataSet(IBillableObjectDataServiceTest.ENCOUNTER_DATASET);
	}
}