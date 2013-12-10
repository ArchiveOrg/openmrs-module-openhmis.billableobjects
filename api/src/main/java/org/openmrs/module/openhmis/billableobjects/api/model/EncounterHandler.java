package org.openmrs.module.openhmis.billableobjects.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.module.openhmis.cashier.api.model.BillLineItem;
import org.openmrs.module.openhmis.inventory.api.model.Item;

public class EncounterHandler extends BaseBillingHandler<Encounter> {
	private static final Logger logger = Logger.getLogger(EncounterHandler.class);
	
	private Integer encounterHandlerId;
	private EncounterType encounterType;
	private Set<Item> associatedItems;
	
	@Override
	public List<BillLineItem> handleObject(Encounter encounter) {
		Set<Item> associatedItems = getAssociatedItems();
		// Only handle for specified encounter type; return empty list
		if (encounter.getEncounterType().getId() != getEncounterType().getId())
			return null;
		// return empty list if there are no associated items
		if (associatedItems == null || associatedItems.isEmpty())
			return new ArrayList<BillLineItem>();
		
		List<BillLineItem> lineItems = new ArrayList<BillLineItem>(associatedItems.size());
		if (encounter.getEncounterType().getUuid().equals(getEncounterType().getUuid())) {
			for (Item item : getAssociatedItems()) {
				BillLineItem lineItem = new BillLineItem();
				lineItem.setItem(item);
				lineItem.setPrice(item.getDefaultPrice().getPrice());
				lineItem.setQuantity(1);
				lineItems.add(lineItem);
			}
		}
		return lineItems;
	}
	
	/** Getters & setters **/
	@Override
	public Integer getId() {
		return encounterHandlerId;
	}

	@Override
	public void setId(Integer id) {
		encounterHandlerId = id;
	}

	public EncounterType getEncounterType() {
		return encounterType;
	}

	public void setEncounterType(EncounterType encounterType) {
		this.encounterType = encounterType;
	}

	public Set<Item> getAssociatedItems() {
		return associatedItems;
	}

	public void setAssociatedItems(Set<Item> associatedItems) {
		this.associatedItems = associatedItems;
	}

}