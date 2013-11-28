package org.openmrs.module.webservices.rest.resource;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillAssociator;
import org.openmrs.module.openhmis.billableobjects.api.IBillAssociatorDataService;
import org.openmrs.module.openhmis.billableobjects.api.model.SimpleNewBillAssociator;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.webservices.rest.web.BillableObjectsRestConstants;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubclassHandler;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;

@Resource(name = BillableObjectsRestConstants.SIMPLE_NEW_ASSOCIATOR_RESOURCE, supportedClass = SimpleNewBillAssociator.class, supportedOpenmrsVersions = {"1.8.*", "1.9.*"})
@Handler(supports = SimpleNewBillAssociator.class)
public class SimpleNewBillAssociatorResource extends BaseRestMetadataResource<IBillAssociator> implements
		DelegatingSubclassHandler<IBillAssociator, IBillAssociator> {

	@Override
	public Class<? extends IMetadataDataService<IBillAssociator>> getServiceClass() {
		return IBillAssociatorDataService.class;
	}
	
	@Override
	public PageableResult getAllByType(RequestContext context) throws ResourceDoesNotSupportOperationException {
		PagingInfo info = PagingUtil.getPagingInfoFromContext(context);
        return new AlreadyPaged<SimpleNewBillAssociator>(
                context,
                Context.getService(IBillAssociatorDataService.class).getAll(info, SimpleNewBillAssociator.class),
                info.hasMoreResults());
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
		return description;
	}

	@Override
	public String getTypeName() {
		return SimpleNewBillAssociator.class.getSimpleName();
	}

	@Override
	public Class<IBillAssociator> getSuperclass() {
		return IBillAssociator.class;
	}

	@Override
	public Class<IBillAssociator> getSubclassHandled() {
		return IBillAssociator.class;
	}

	@Override
	public IBillAssociator newDelegate() {
		return new SimpleNewBillAssociator();
	}

}
