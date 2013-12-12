package org.openmrs.module.webservices.rest.resource;

import org.apache.commons.lang.NotImplementedException;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
import org.openmrs.module.openhmis.billableobjects.api.model.BaseBillingHandler;
import org.openmrs.module.openhmis.billableobjects.api.model.IBillingHandler;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.webservices.rest.web.BillableObjectsRestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.PropertySetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;

@Resource(name = BillableObjectsRestConstants.HANDLER_RESOURCE, supportedClass = IBillingHandler.class, supportedOpenmrsVersions = {"1.8.*", "1.9.*"})
public class BillingHandlerResource<T extends IBillingHandler> extends BaseRestMetadataResource<BaseBillingHandler> {
	private static final String NEED_SUBCLASS_HANDLER = "This operation should be handled by a subclass handler.";

	@Override
	public Class<? extends IMetadataDataService<BaseBillingHandler>> getServiceClass() {
		return IBillingHandlerDataService.class;
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
		description.addProperty("description");
		description.addProperty("type");
		return description;
	}

	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = super.getCreatableProperties();
		description.addProperty("type");
		return description;
	}
	
	@Override
	public BaseBillingHandler newDelegate() {
		throw new NotImplementedException(NEED_SUBCLASS_HANDLER);
	}
	
	@PropertySetter("type")
	public void setHandlerType(T instance, String type) {
		// Allow this property to be silently ignored
	}
	
	@PropertyGetter("type")
	public String getHandlerType(T instance) {
		return instance.getClass().getSimpleName();
	}
}
