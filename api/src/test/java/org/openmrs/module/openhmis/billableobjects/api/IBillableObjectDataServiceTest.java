package org.openmrs.module.openhmis.billableobjects.api;

import org.openmrs.Encounter;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObjectDataService;
import org.openmrs.module.openhmis.billableobjects.api.type.BaseBillableObject;
import org.openmrs.module.openhmis.billableobjects.api.type.BillableEncounter;
import org.openmrs.module.openhmis.commons.api.entity.IObjectDataServiceTest;

public class IBillableObjectDataServiceTest extends IObjectDataServiceTest<IBillableObjectDataService, BaseBillableObject> {
	public static final String BILLABLEOBJ_DATASET = TestConstants.BASE_DATASET_DIR + "BillableObjectTest.xml";
	public static final String ENCOUNTER_DATASET = TestConstants.BASE_DATASET_DIR + "EncounterServiceTest.xml";
	
	@Override
	public void before() throws Exception{
		super.before();
		executeDataSet(TestConstants.CORE_DATASET);
		executeDataSet(BILLABLEOBJ_DATASET);
		executeDataSet(ENCOUNTER_DATASET);
	}
	
	@Override
	protected BaseBillableObject<?> createEntity(boolean valid) {
		BaseBillableObject<Encounter> billable = new BillableEncounter();
		if (valid)
			billable.setObject(Context.getEncounterService().getEncounter(17));
		return billable;
	}

	@Override
	protected int getTestEntityCount() {
		return 1;
	}

	@Override
	protected void updateEntityFields(BaseBillableObject entity) {
		entity.setUuid("b67a9131-0111-4ad4-87ae-4d2f59ecce6e");
	}

}
