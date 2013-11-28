package org.openmrs.module.webservices.rest.resource;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandler;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
import org.openmrs.module.openhmis.billableobjects.api.model.EncounterHandler;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.BillableObjectsRestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubclassHandler;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;

@Resource(name = BillableObjectsRestConstants.ENCOUNTER_HANDLER_RESOURCE, supportedClass = EncounterHandler.class, supportedOpenmrsVersions = {"1.8.*", "1.9.*"})
@Handler(supports = EncounterHandler.class)
public class EncounterHandlerResource extends BillingHandlerResource<IBillingHandler>
		implements DelegatingSubclassHandler<IBillingHandler, IBillingHandler> {

	@Override
	public Class<? extends IMetadataDataService<IBillingHandler>> getServiceClass() {
		return IBillingHandlerDataService.class;
	}

	@Override
	public PageableResult getAllByType(RequestContext context) throws ResourceDoesNotSupportOperationException {
		PagingInfo info = PagingUtil.getPagingInfoFromContext(context);
        return new AlreadyPaged<EncounterHandler>(
                context,
                Context.getService(IBillingHandlerDataService.class).getAll(info, EncounterHandler.class),
                info.hasMoreResults());
    }

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = super.getRepresentationDescription(rep);
		if (!(rep instanceof RefRepresentation)) {
			description.addProperty("encounterType");
		}
		return description;
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = super.getCreatableProperties();
		description.addProperty("encounterType");
		return description;
	}
	
	@Override
	public Class<IBillingHandler> getSubclassHandled() {
		return IBillingHandler.class;
	}

	@Override
	public Class<IBillingHandler> getSuperclass() {
		return IBillingHandler.class;
	}

	@Override
	public String getTypeName() {
		return EncounterHandler.class.getSimpleName();
	}

	@Override
	public EncounterHandler newDelegate() {
		return new EncounterHandler();
	}

}