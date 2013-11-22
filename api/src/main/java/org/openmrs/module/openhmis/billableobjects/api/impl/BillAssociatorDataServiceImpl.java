package org.openmrs.module.openhmis.billableobjects.api.impl;

import org.openmrs.api.APIException;
import org.openmrs.module.openhmis.billableobjects.api.IBillAssociator;
import org.openmrs.module.openhmis.billableobjects.api.IBillAssociatorDataService;
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
}
