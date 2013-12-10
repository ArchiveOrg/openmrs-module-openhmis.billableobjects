package org.openmrs.module.openhmis.billableobjects.api.type;

import org.openmrs.DrugOrder;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;

public class BillableDrugOrder extends BaseBillableObject<DrugOrder> {

	@Override
	public DrugOrder getObjectByUuid(String uuid) {
		return (DrugOrder) Context.getOrderService().getOrderByUuid(uuid);
	}

	@Override
	public Patient getAssociatedPatient() {
		return getObject().getPatient();
	}

}
