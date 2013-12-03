package org.openmrs.module.openhmis.billableobjects.api.util;

import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openmrs.api.context.Context;
import org.openmrs.event.Event;
import org.openmrs.event.Event.Action;
import org.openmrs.module.openhmis.billableobjects.api.BillableObjectEventListenerFactory;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
import org.openmrs.module.openhmis.billableobjects.api.model.BaseBillingHandler;
import org.openmrs.module.openhmis.billableobjects.api.model.IBillingHandler;


public class EventHelper {
	private static final Logger logger = Logger.getLogger(EventHelper.class);
	
	/**
	 * @should bind all existing handlers
	 */
	public static void bindAllHandlers() {
		List<BaseBillingHandler> handlers = Context.getService(IBillingHandlerDataService.class).getAll();
		for (IBillingHandler handler : handlers) {
			BillsFor annotation = handler.getClass().getAnnotation(BillsFor.class);
			if (annotation == null) {
				logger.warn(String.format("%s class %s for handler \"%s\" does not specify handled classes using %s annotation: skipping event binding.",
						IBillingHandler.class.getSimpleName(),
						handler.getClass().getSimpleName(),
						handler.getName(),
						BillsFor.class.getSimpleName()));
				continue;
			}
			for (Class<?> handledClass : annotation.value()) {
				Event.subscribe(handledClass,
				Action.CREATED.toString(), 
				BillableObjectEventListenerFactory.getInstance());
			}
		}
	}
	
	public static void unbindAllHandlers() {
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
