package org.openmrs.module.openhmis.billableobjects.api.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandler;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;

public class BillingHandlerDataServiceImpl extends BaseMetadataDataServiceImpl<IBillingHandler>
	implements IBillingHandlerDataService {

	@Override
	protected IMetadataAuthorizationPrivileges getPrivileges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void validate(IBillingHandler object) throws APIException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends IBillingHandler> List<T> getAll(PagingInfo pagingInfo, Class<T> clazz) {
		IMetadataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getGetPrivilege())) {
			Context.requirePrivilege(privileges.getGetPrivilege());
		}

		loadPagingTotal(pagingInfo, repository.createCriteria(clazz));

		return executeCriteria(clazz, pagingInfo, null, getDefaultSort());
	}
}
