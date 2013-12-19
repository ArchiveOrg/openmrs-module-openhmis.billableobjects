package org.openmrs.module.openhmis.billableobjects.api.impl;


import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.DrugOrder;
import org.openmrs.Encounter;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObjectDataServiceTest;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObjectsService;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataServiceTest;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerService;
import org.openmrs.module.openhmis.billableobjects.api.TestConstants;
import org.openmrs.module.openhmis.billableobjects.api.model.IBillableObject;
import org.openmrs.module.openhmis.billableobjects.api.type.BillableEncounter;
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
	
	/**
	 * @see IBillableObjectsService#getBillableObjectClassForClassName(String)
	 * @verifies return the IBillableObject class for a class name
	 */
	@Test
	public void getBillableObjectClassForClassName_shouldReturnTheIBillableObjectClassForAClassName() throws Exception {
		Class<? extends IBillableObject> cls = billableObjectsService.getBillableObjectClassForClassName(Encounter.class.getName());
		Assert.assertEquals(BillableEncounter.class, cls);
	}
	
	/**
	 * @see IBillableObjectsService#getBillableClassNames()
	 * @verifies return classes for included IBillableObjects
	 */
	@Test
	public void getBillableClassNames_shouldReturnClassesForIncludedIBillableObjects() throws Exception {
		Set<String> names = billableObjectsService.getBillableClassNames();
		Assert.assertTrue(names.contains(Encounter.class.getName()));
		Assert.assertTrue(names.contains(DrugOrder.class.getName()));
	}
}