package org.openmrs.module.openhmis.billableobjects.api;

import java.util.List;
import java.util.Set;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.openhmis.billableobjects.api.model.IBillingHandler;

public interface IBillingHandlerService extends OpenmrsService {
	Set<Class<? extends IBillingHandler>> getBillingHandlerClasses();
	Set<Class<?>> getActivelyHandledClasses();
	Set<IBillingHandler<?>> getHandlersForClassName(String className);
	List<String> getHandlerTypeNames();
}
