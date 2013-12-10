package org.openmrs.module.openhmis.billableobjects.api;

import org.openmrs.api.context.Context;
import org.openmrs.module.DaemonToken;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
import org.openmrs.module.openhmis.billableobjects.api.model.BaseBillingHandler;
import org.openmrs.module.openhmis.billableobjects.api.model.EncounterHandler;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataServiceTest;

public class IBillingHandlerDataServiceTest extends IMetadataDataServiceTest<IBillingHandlerDataService, BaseBillingHandler> {
	public static final String HANDLERS_DATASET = TestConstants.BASE_DATASET_DIR + "HandlersTest.xml";
	public static final String INVENTORY_DATASET = TestConstants.BASE_DATASET_DIR + "InvTest.xml";

	@Override
	public void before() throws Exception{
		super.before();
		BillableObjectEventListenerFactory.setDaemonToken(new DaemonToken("Fake?"));
		executeDataSet(TestConstants.CORE_DATASET);
		executeDataSet(HANDLERS_DATASET);
		executeDataSet(INVENTORY_DATASET);
	}

	@Override
	protected BaseBillingHandler createEntity(boolean valid) {
		EncounterHandler handler = new EncounterHandler();
		if (valid) handler.setName("Test Encounter Handler");
		handler.setEncounterType(Context.getEncounterService().getEncounterType(1));
		return handler;
	}

	@Override
	protected int getTestEntityCount() {
		return 2;
	}

	@Override
	protected void updateEntityFields(BaseBillingHandler entity) {
		entity.setName("Another Handler");
	}
}
