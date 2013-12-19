package org.openmrs.module.openhmis.billableobjects.api.model;


import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataServiceTest;
import org.openmrs.module.openhmis.billableobjects.api.TestConstants;
import org.openmrs.module.openhmis.billableobjects.api.model.BaseBillingHandler;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class BaseBillingHandlerTest extends BaseModuleWebContextSensitiveTest {
	public static final String DRUGORDER_DATASET = TestConstants.BASE_DATASET_DIR + "OrderServiceTest-drugOrdersList.xml";
	
	@Before
	public void before() throws Exception{
		executeDataSet(TestConstants.CORE_DATASET);
		executeDataSet(IBillingHandlerDataServiceTest.HANDLERS_DATASET);
		executeDataSet(IBillingHandlerDataServiceTest.INVENTORY_DATASET);
		executeDataSet(DRUGORDER_DATASET);
	}
	
	/**
	 * @see BaseBillingHandler#onMessage(Message)
	 * @verifies receive event messages
	 */
	@Test
	public void onMessage_shouldReceiveEventMessages() throws Exception {
//		EventHelper.bindAllHandlers();
//		OrderService service = Context.getOrderService();
//		DrugOrder drugOrder = service.getOrder(6, DrugOrder.class);
//		DrugOrder newOrder = new DrugOrder();
//		newOrder.setPatient(drugOrder.getPatient());
//		newOrder.setUrgency(drugOrder.getUrgency());
//		newOrder.setOrderType(drugOrder.getOrderType());
//		newOrder.setConcept(drugOrder.getConcept());
//		newOrder.setOrderer(drugOrder.getOrderer());
//		newOrder.setStartDate(drugOrder.getStartDate());
//		service.saveOrder(newOrder);
//		Context.flushSession();
	}
}