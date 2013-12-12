package org.openmrs.module.openhmis.billableobjects.api.util;

import java.util.List;

import org.openmrs.module.openhmis.cashier.api.model.BillLineItem;

public class BillingHandlerRecoverableException extends Exception {
	private List<BillLineItem> lineItems;
	private static final long serialVersionUID = -4314010951829143852L;
	public BillingHandlerRecoverableException(String message, List<BillLineItem> lineItems) {
		super(message);
		this.lineItems = lineItems;
	}
	
	public List<BillLineItem> getLineItems() {
		return lineItems;
	}
}
