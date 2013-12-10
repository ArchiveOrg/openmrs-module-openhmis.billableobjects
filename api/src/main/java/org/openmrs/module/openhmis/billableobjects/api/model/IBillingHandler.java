package org.openmrs.module.openhmis.billableobjects.api.model;

import java.util.List;

import org.openmrs.module.openhmis.cashier.api.model.BillLineItem;

public interface IBillingHandler<T> {
	public List<BillLineItem> handleObject(T object);
}
