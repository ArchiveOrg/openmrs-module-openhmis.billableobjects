package org.openmrs.module.webservices.rest.resource;

import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
import org.openmrs.module.openhmis.billableobjects.api.model.BaseBillingHandler;
import org.openmrs.module.openhmis.billableobjects.api.model.DrugOrderHandler;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.webservices.rest.web.BillableObjectsRestConstants;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubclassHandler;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;

@Resource(name = BillableObjectsRestConstants.DRUGORDER_HANDLER_RESOURCE, supportedClass = DrugOrderHandler.class, supportedOpenmrsVersions = {"1.8.*", "1.9.*"})
public class DrugOrderHandlerResource extends BillingHandlerResource<BaseBillingHandler> implements
		DelegatingSubclassHandler<BaseBillingHandler, BaseBillingHandler> {

	@Override
	public PageableResult getAllByType(RequestContext context) throws ResourceDoesNotSupportOperationException {
		PagingInfo info = PagingUtil.getPagingInfoFromContext(context);
        return new AlreadyPaged<DrugOrderHandler>(
                context,
                Context.getService(IBillingHandlerDataService.class).getAll(info, DrugOrderHandler.class),
                info.hasMoreResults());
	}
	
	@Override
	public BaseBillingHandler newDelegate() {
		return new DrugOrderHandler();
	}

	@Override
	public String getTypeName() {
		return DrugOrderHandler.class.getSimpleName();
	}

	@Override
	public Class<BaseBillingHandler> getSuperclass() {
		return BaseBillingHandler.class;
	}

	@Override
	public Class<BaseBillingHandler> getSubclassHandled() {
		return BaseBillingHandler.class;
	}

}