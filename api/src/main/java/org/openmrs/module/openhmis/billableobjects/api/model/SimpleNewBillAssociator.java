package org.openmrs.module.openhmis.billableobjects.api.model;

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.Provider;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.cashier.api.IBillService;
import org.openmrs.module.openhmis.cashier.api.ICashPointService;
import org.openmrs.module.openhmis.cashier.api.model.Bill;
import org.openmrs.module.openhmis.cashier.api.model.BillLineItem;

public class SimpleNewBillAssociator extends BaseBillAssociator {
	private Integer id;
	
	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public Bill associateItemsToBill(List<BillLineItem> lineItems, Patient patient, Provider provider) {
		Bill bill = new Bill();
		bill.setPatient(patient);
		bill.setLineItems(lineItems);
		bill.setCashier(provider);
		// TODO: Fix auto cash point lookup
		bill.setCashPoint(Context.getService(ICashPointService.class).getAll().get(0));
		Context.getService(IBillService.class).save(bill);
		return bill;
	}
}
