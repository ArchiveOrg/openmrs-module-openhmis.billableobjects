package org.openmrs.module.openhmis.billableobjects.api.type;

import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;

@Handler(supports={ Encounter.class })
public class BillableEncounter extends BaseBillableObject<Encounter> {

	@Override
	public Encounter getObjectByUuid(String uuid) {
		return Context.getEncounterService().getEncounterByUuid(uuid);
	}

	@Override
	public Patient getAssociatedPatient() {
		return getObject().getPatient();
	}
}
