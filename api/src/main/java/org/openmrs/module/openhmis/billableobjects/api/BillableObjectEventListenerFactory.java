package org.openmrs.module.openhmis.billableobjects.api;

import org.openmrs.event.EventListener;
import org.openmrs.module.openhmis.billableobjects.api.listener.BillableObjectEventListener;

public class BillableObjectEventListenerFactory {

	private static volatile BillableObjectEventListener instance;

	public static EventListener getInstance() {
		if (instance == null)
			instance = new BillableObjectEventListener();
		return instance;
	}

}
