package org.openmrs.module.openhmis.billableobjects.api.impl;

import org.openmrs.api.APIException;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandler;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
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

}
