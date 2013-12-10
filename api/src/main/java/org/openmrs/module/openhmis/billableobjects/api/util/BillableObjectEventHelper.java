package org.openmrs.module.openhmis.billableobjects.api.util;

import java.util.Set;

import org.apache.log4j.Logger;
import org.openmrs.event.Event;
import org.openmrs.event.Event.Action;
import org.openmrs.module.openhmis.billableobjects.api.BillableObjectEventListenerFactory;


public class BillableObjectEventHelper {
	private static final Logger logger = Logger.getLogger(BillableObjectEventHelper.class);

	/**
	 * @should bind all existing handlers
	 */
	public static void bindListenerForAllHandlers() {
		for (Class<?> handledClass : BillingHandlerHelper.getActivelyHandledClasses()) {
			Event.subscribe(
					handledClass,
					Action.CREATED.toString(), 
					BillableObjectEventListenerFactory.getInstance()
			);
		}
	}
	
	public static void unbindListenerForAllHandlers() {
		Set<String> handledTypeNames = BillableObjectsHelper.getHandledTypeNames();
		for (String typeName : handledTypeNames) {
			Class<?> cls;
			try {
				cls = Class.forName(typeName);
			} catch (ClassNotFoundException e) {
				logger.warn("Could not find class \"" + typeName + "\": not unbinding any event handler.");
				continue;
			}
			Event.unsubscribe(
				cls,
				Action.CREATED,
				BillableObjectEventListenerFactory.getInstance()
			);
		}
	}	
}
