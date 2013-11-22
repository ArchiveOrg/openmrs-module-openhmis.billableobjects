package org.openmrs.module.openhmis.billableobjects.api;

import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillAssociatorDataService;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandler;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
import org.openmrs.module.openhmis.billableobjects.api.model.EncounterHandler;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataServiceTest;

public class IBillingHandlerDataServiceTest extends IMetadataDataServiceTest<IBillingHandlerDataService, IBillingHandler> {
	public static final String HANDLERS_DATASET = TestConstants.BASE_DATASET_DIR + "HandlersTest.xml";
	public static final String INVENTORY_DATASET = TestConstants.BASE_DATASET_DIR + "InvTest.xml";

	@Override
	public void before() throws Exception{
		super.before();
		executeDataSet(TestConstants.CORE_DATASET);
		executeDataSet(HANDLERS_DATASET);
		executeDataSet(INVENTORY_DATASET);
	}

	@Override
	protected IBillingHandler createEntity(boolean valid) {
		EncounterHandler handler = new EncounterHandler();
		if (valid) handler.setName("Test Encounter Handler");
		handler.setBillAssociator(Context.getService(IBillAssociatorDataService.class).getById(0));
		handler.setEncounterType(Context.getEncounterService().getEncounterType(1));
		return handler;
	}

	@Override
	protected int getTestEntityCount() {
		return 2;
	}

	@Override
	protected void updateEntityFields(IBillingHandler entity) {
		entity.setName("Another Handler");
	}
}
