package org.openmrs.module.openhmis.eventbasedbilling.api.model;

import javax.jms.Message;

import org.openmrs.DrugOrder;
import org.openmrs.Order;
import org.openmrs.module.openhmis.cashier.api.model.Bill;
import org.openmrs.module.openhmis.eventbasedbilling.api.util.BillsFor;

@BillsFor({ Order.class })
public class DrugOrderHandler extends BaseBillingHandler {
	
	private Integer id;

	@Override
	public Bill handleEvent(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

}
