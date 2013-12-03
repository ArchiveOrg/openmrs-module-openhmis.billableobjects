package org.openmrs.module.openhmis.billableobjects.api;

import java.util.List;

import org.openmrs.module.openhmis.billableobjects.api.model.BaseBillingHandler;
import org.openmrs.module.openhmis.billableobjects.api.model.IBillingHandler;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;

public interface IBillingHandlerDataService extends IMetadataDataService<BaseBillingHandler> {
	public <T extends IBillingHandler> List<T> getAll(PagingInfo pagingInfo, Class<T> clazz);
}
