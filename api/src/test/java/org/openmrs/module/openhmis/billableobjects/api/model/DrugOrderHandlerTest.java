package org.openmrs.module.openhmis.billableobjects.api.model;


import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Drug;
import org.openmrs.DrugOrder;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataServiceTest;
import org.openmrs.module.openhmis.billableobjects.api.TestConstants;
import org.openmrs.module.openhmis.billableobjects.api.util.BillingHandlerRecoverableException;
import org.openmrs.module.openhmis.cashier.api.model.BillLineItem;
import org.openmrs.module.openhmis.inventory.api.IItemDataService;
import org.openmrs.module.openhmis.inventory.api.model.Item;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;

public class DrugOrderHandlerTest extends BaseModuleWebContextSensitiveTest {
	DrugOrderHandler handler;
	
	@Before
	public void before() {
		handler = new DrugOrderHandler();
	}
	
	/**
	 * @see DrugOrderHandler#handleObject(DrugOrder)
	 * @verifies throw APIException if drugOrder is null
	 */
	@Test(expected=APIException.class)
	public void handleObject_shouldThrowAPIExceptionIfDrugOrderIsNull() throws Exception {
		handler.handleObject(null);
	}

	/**
	 * @see DrugOrderHandler#handleObject(DrugOrder)
	 * @verifies generate line items for an associated item
	 */
	@Test
	public void handleObject_shouldGenerateLineItemsForAnAssociatedItem() throws Exception {
		executeDataSet(TestConstants.DRUGORDER_DATASET);
		executeDataSet(IBillingHandlerDataServiceTest.INVENTORY_DATASET);
		
		DrugOrder drugOrder = Context.getOrderService().getOrder(6, DrugOrder.class);
		List<BillLineItem> lineItems = handler.handleObject(drugOrder);
		
		Assert.assertNotNull(lineItems);
		Assert.assertEquals(1, lineItems.size());
	}

	/**
	 * @see DrugOrderHandler#handleObject(DrugOrder)
	 * @verifies throw exception if no item found
	 */
	@Test(expected=BillingHandlerRecoverableException.class)
	public void handleObject_shouldThrowExceptionIfNoItemFound() throws Exception {
		executeDataSet(TestConstants.DRUGORDER_DATASET);
		DrugOrder drugOrder = Context.getOrderService().getOrder(6, DrugOrder.class);
		List<BillLineItem> lineItems = handler.handleObject(drugOrder);
	}

	/**
	 * @see DrugOrderHandler#handleObject(DrugOrder)
	 * @verifies throw exception if multiple items found
	 */
	@Test(expected=BillingHandlerRecoverableException.class)
	public void handleObject_shouldThrowExceptionIfMultipleItemsFound() throws Exception {
		executeDataSet(TestConstants.DRUGORDER_DATASET);
		executeDataSet(IBillingHandlerDataServiceTest.INVENTORY_DATASET);
		IItemDataService itemService = Context.getService(IItemDataService.class); 
		Item item = itemService.getById(1);
		item.setDrug(new Drug(3));
		itemService.save(item);
		
		DrugOrder drugOrder = Context.getOrderService().getOrder(6, DrugOrder.class);
		List<BillLineItem> lineItems = handler.handleObject(drugOrder);
	}
}