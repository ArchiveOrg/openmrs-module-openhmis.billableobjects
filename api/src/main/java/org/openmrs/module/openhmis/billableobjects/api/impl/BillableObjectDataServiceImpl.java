package org.openmrs.module.openhmis.billableobjects.api.impl;

import org.openmrs.api.APIException;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObjectDataService;
import org.openmrs.module.openhmis.billableobjects.api.security.BasicObjectAuthorizationPrivileges;
import org.openmrs.module.openhmis.billableobjects.api.type.BaseBillableObject;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseObjectDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IObjectAuthorizationPrivileges;

public class BillableObjectDataServiceImpl extends BaseObjectDataServiceImpl<BaseBillableObject, IObjectAuthorizationPrivileges>
		implements IBillableObjectDataService {

	@Override
	protected IObjectAuthorizationPrivileges getPrivileges() {
		return new BasicObjectAuthorizationPrivileges();
	}

	@Override
	protected void validate(BaseBillableObject object) throws APIException {
	}
}
