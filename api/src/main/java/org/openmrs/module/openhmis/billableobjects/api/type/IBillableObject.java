package org.openmrs.module.openhmis.billableobjects.api.type;

import org.openmrs.OpenmrsObject;
import org.openmrs.Patient;
import org.openmrs.module.openhmis.cashier.api.model.Bill;

public interface IBillableObject<T extends OpenmrsObject> extends OpenmrsObject {
	public Integer getId();
	public void setId(Integer id);
	public T getObject();
	public T getObjectByUuid(String uuid);
	public void setObject(T object);
	public Bill getBill();
	public void setBill(Bill bill);
	public Patient getAssociatedPatient();
}
