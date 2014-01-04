package org.openmrs.module.openhmis.billableobjects.api.security;

import org.openmrs.module.openhmis.billableobjects.api.util.PrivilegeConstants;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;

public class BasicMetadataAuthorizationPrivileges extends BasicObjectAuthorizationPrivileges
		implements IMetadataAuthorizationPrivileges {

	@Override
	public String getRetirePrivilege() {
		return PrivilegeConstants.RETIRE;
	}

}
