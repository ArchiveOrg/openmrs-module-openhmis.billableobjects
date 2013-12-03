package org.openmrs.module.openhmis.billableobjects.api.model;

import javax.jms.Message;

import org.openmrs.Order;
import org.openmrs.module.openhmis.billableobjects.api.util.BillsFor;
import org.openmrs.module.openhmis.cashier.api.model.Bill;

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
