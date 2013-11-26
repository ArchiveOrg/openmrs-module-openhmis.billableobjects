package org.openmrs.module.webservices.rest.resource;

import org.apache.commons.lang.NotImplementedException;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandler;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;

@Resource(name = RestConstants.VERSION_2 + "/billableobjects/handlers", supportedClass = IBillingHandler.class, supportedOpenmrsVersions = {"1.8.*", "1.9.*"})
public class BillingHandlerResource<T extends IBillingHandler> extends BaseRestMetadataResource<IBillingHandler> {
	private static final String NEED_SUBCLASS_HANDLER = "This operation should be handled by a subclass handler.";

	@Override
	public Class<? extends IMetadataDataService<IBillingHandler>> getServiceClass() {
		return IBillingHandlerDataService.class;
	}

	@Override
	public IBillingHandler newDelegate() {
		throw new NotImplementedException(NEED_SUBCLASS_HANDLER);
	}
}
