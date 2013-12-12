package org.openmrs.module.openhmis.billableobjects.api.model;

import java.util.LinkedList;
import java.util.List;

import org.openmrs.Drug;
import org.openmrs.DrugOrder;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.util.BillingHandlerRecoverableException;
import org.openmrs.module.openhmis.cashier.api.model.BillLineItem;
import org.openmrs.module.openhmis.inventory.api.IItemDataService;
import org.openmrs.module.openhmis.inventory.api.model.Item;
import org.openmrs.module.openhmis.inventory.api.search.ItemSearch;

public class DrugOrderHandler extends BaseBillingHandler<DrugOrder> {
	private Integer id;

	@Override
	public List<BillLineItem> handleObject(DrugOrder drugOrder) throws BillingHandlerRecoverableException {
		IItemDataService service = Context.getService(IItemDataService.class);
		List<BillLineItem> lineItems = new LinkedList<BillLineItem>();
		Drug drug = drugOrder.getDrug();
		Item template = new Item();
		template.setDrug(drug);
		ItemSearch itemSearch = new ItemSearch(template);
		List<Item> results = service.findItems(itemSearch, null);
		for (Item item : results) {
			BillLineItem lineItem = new BillLineItem();
			lineItem.setItem(item);
			lineItem.setPrice(item.getDefaultPrice().getPrice());
			lineItem.setQuantity(1);
			lineItems.add(lineItem);
		}
		if (lineItems.size() == 0)
			throw new BillingHandlerRecoverableException("No item found for drug \"" + drug.getName() + ".\"", lineItems);
		if (lineItems.size() > 1)
			throw new BillingHandlerRecoverableException("Multiple items found for drug \"" + drug.getName() + ".\"", lineItems);
		return lineItems;
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
