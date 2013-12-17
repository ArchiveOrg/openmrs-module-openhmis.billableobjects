package org.openmrs.module.openhmis.billableobjects.api;

import java.util.Set;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.openhmis.billableobjects.api.model.IBillableObject;

public interface IBillableObjectsService extends OpenmrsService {
	
	Class<? extends IBillableObject> getBillableObjectTypeForClassName(String className);
	Set<String> getHandledTypeNames();
	void bindListenerForAllHandlers();
	void unbindListenerForAllHandlers();
	IBillingHandlerService getBillingHandlerService();
	void setBillingHandlerService(IBillingHandlerService billingHandlerService);
}
