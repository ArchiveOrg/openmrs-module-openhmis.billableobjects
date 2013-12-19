package org.openmrs.module.openhmis.billableobjects.api;

import java.util.Set;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.openhmis.billableobjects.api.model.IBillableObject;

public interface IBillableObjectsService extends OpenmrsService {
	
	/**
	 * Get the IBillableObject class that handles the specified class name.
	 * 
	 * @param className name of an OpenmrsObject class
	 * @return corresponding IBillableObject class
	 * @should return the IBillableObject class for a class name
	 */
	Class<? extends IBillableObject> getBillableObjectClassForClassName(String className);
	
	/**
	 * Get the names of all classes with a corresponding IBillableObject
	 * implementation
	 * 
	 * @return set of class names
	 * @should return classes for included IBillableObjects
	 */
	Set<String> getBillableClassNames();
	
	/**
	 * Clear current event bindings and rebind the event listener for all
	 * existing handlers.
	 */
	void rebindListenerForAllHandlers();
	
	/**
	 * Unbind current events
	 */
	void unbindListenerForAllHandlers();
	
	IBillingHandlerService getBillingHandlerService();
	void setBillingHandlerService(IBillingHandlerService billingHandlerService);
}
