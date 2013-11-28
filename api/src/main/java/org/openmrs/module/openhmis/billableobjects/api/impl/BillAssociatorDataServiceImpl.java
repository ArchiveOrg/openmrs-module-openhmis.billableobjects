package org.openmrs.module.openhmis.billableobjects.api.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillAssociator;
import org.openmrs.module.openhmis.billableobjects.api.IBillAssociatorDataService;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;

public class BillAssociatorDataServiceImpl extends BaseMetadataDataServiceImpl<IBillAssociator>
	implements IBillAssociatorDataService {

	@Override
	protected IMetadataAuthorizationPrivileges getPrivileges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void validate(IBillAssociator object) throws APIException {
		// TODO Auto-generated method stub		
	}
	
	@Override
	public <T extends IBillAssociator> List<T> getAll(PagingInfo pagingInfo, Class<T> clazz) {
		IMetadataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getGetPrivilege())) {
			Context.requirePrivilege(privileges.getGetPrivilege());
		}

		loadPagingTotal(pagingInfo, repository.createCriteria(clazz));

		return executeCriteria(clazz, pagingInfo, null, getDefaultSort());
	}
}
