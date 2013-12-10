package org.openmrs.module.openhmis.billableobjects.api.model;

import java.util.List;

import org.openmrs.DrugOrder;
import org.openmrs.module.openhmis.cashier.api.model.BillLineItem;

public class DrugOrderHandler extends BaseBillingHandler<DrugOrder> {
	
	private Integer id;

	@Override
	public List<BillLineItem> handleObject(DrugOrder drugOrder) {
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
