package org.openmrs.module.openhmis.billableobjects.api.security;

import org.openmrs.module.openhmis.billableobjects.api.util.PrivilegeConstants;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;
import org.openmrs.module.openhmis.commons.api.entity.security.IObjectAuthorizationPrivileges;

public class BasicObjectAuthorizationPrivileges implements
		IObjectAuthorizationPrivileges {

	@Override
	public String getSavePrivilege() {
		return PrivilegeConstants.MANAGE;
	}

	@Override
	public String getPurgePrivilege() {
		return PrivilegeConstants.PURGE;
	}

	@Override
	public String getGetPrivilege() {
		return PrivilegeConstants.VIEW;
	}

}
