package org.openmrs.module.openhmis.billableobjects.api;

import java.util.List;

import org.openmrs.module.openhmis.billableobjects.api.model.BaseBillAssociator;
import org.openmrs.module.openhmis.billableobjects.api.model.IBillAssociator;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.IMetadataDataService;

public interface IBillAssociatorDataService extends IMetadataDataService<BaseBillAssociator> {
	public <T extends IBillAssociator> List<T> getAll(PagingInfo pagingInfo, Class<T> clazz);
}
