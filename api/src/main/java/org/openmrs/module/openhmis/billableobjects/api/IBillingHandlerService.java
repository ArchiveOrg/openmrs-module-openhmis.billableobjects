package org.openmrs.module.openhmis.billableobjects.api;

import java.util.List;
import java.util.Set;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.openhmis.billableobjects.api.model.IBillingHandler;

public interface IBillingHandlerService extends OpenmrsService {
	
	/**
	 * Search for any concrete class that implements the IBillingHandler 
	 * interface (or return cached set)
	 * 
	 * @return set of handler classes
	 */
	Set<Class<? extends IBillingHandler>> getBillingHandlerClasses();
	
	/**
	 * Get the list handled classes by looking up the current list of saved
	 * handlers. 
	 * 
	 * @return set of handled classes
	 * @should return currently handled classes
	 */
	Set<Class<?>> getActivelyHandledClasses();
	
	/**
	 * Get the set of handlers for the specified class name (based on the
	 * cached list of saved handlers)
	 * 
	 * @param className name of an OpenmrsObject class
	 * @return set of handlers
	 */
	Set<IBillingHandler<?>> getHandlersForClassName(String className);
	
	/**
	 * Get alphabetically ordered list of available handler class names
	 * 
	 * @return list of handler class names
	 * @should return all names in alphabetical order
	 */
	List<String> getHandlerTypeNames();
}
