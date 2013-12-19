package org.openmrs.module.openhmis.billableobjects.api.model;

import java.util.LinkedList;
import java.util.List;

import org.openmrs.DrugOrder;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.util.BillingHandlerRecoverableException;
import org.openmrs.module.openhmis.cashier.api.model.BillLineItem;
import org.openmrs.module.openhmis.inventory.api.IItemDataService;
import org.openmrs.module.openhmis.inventory.api.model.Item;
import org.openmrs.module.openhmis.inventory.api.search.ItemSearch;

public class DrugOrderHandler extends BaseBillingHandler<DrugOrder> {
	private Integer id;

	/**
	 * Find items in the inventory associated with the drug in the drug order.
	 * Use the drug order quantity for the line item quantity.
	 * 
	 * @param drugOrder Drug order to bill for
	 * @return line items for the drug order
	 * @should throw APIException if drugOrder is null
	 * @should generate line items for an associated item
	 * @should throw exception if no item found
	 * @should throw exception if multiple items found
	 */
	@Override
	public List<BillLineItem> handleObject(DrugOrder drugOrder) throws BillingHandlerRecoverableException {
		if (drugOrder == null)
			throw new APIException("Drug order cannot be null.");
		IItemDataService service = Context.getService(IItemDataService.class);
		List<BillLineItem> lineItems = new LinkedList<BillLineItem>();
		ItemSearch itemSearch = new ItemSearch();
		itemSearch.getTemplate().setDrug(drugOrder.getDrug());
		List<Item> results = service.findItems(itemSearch, null);
		Integer quantity = drugOrder.getQuantity();
		if (quantity == null || quantity == 0)
			quantity = 1;
		for (Item item : results) {
			BillLineItem lineItem = new BillLineItem();
			lineItem.setItem(item);
			lineItem.setPrice(item.getDefaultPrice().getPrice());
			lineItem.setQuantity(quantity);
			lineItems.add(lineItem);
		}
		if (lineItems.size() == 0)
			throw new BillingHandlerRecoverableException("No item found for drug \"" + drugOrder.getDrug().getName() + ".\"", lineItems);
		if (lineItems.size() > 1)
			throw new BillingHandlerRecoverableException("Multiple items found for drug \"" + drugOrder.getDrug().getName() + ".\"", lineItems);
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
