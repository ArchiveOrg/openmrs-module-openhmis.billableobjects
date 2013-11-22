package org.openmrs.module.openhmis.eventbasedbilling.api.impl;

import org.openmrs.OpenmrsObject;
import org.openmrs.api.APIException;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseObjectDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IObjectAuthorizationPrivileges;
import org.openmrs.module.openhmis.eventbasedbilling.api.IBillableObject;
import org.openmrs.module.openhmis.eventbasedbilling.api.IBillableObjectDataService;

public class BillableObjectDataServiceImpl extends
		BaseObjectDataServiceImpl<IBillableObject, IObjectAuthorizationPrivileges>
		implements IBillableObjectDataService {

	@Override
	protected IObjectAuthorizationPrivileges getPrivileges() {
		return null;
	}

	@Override
	protected void validate(IBillableObject object) throws APIException {
	}
}
