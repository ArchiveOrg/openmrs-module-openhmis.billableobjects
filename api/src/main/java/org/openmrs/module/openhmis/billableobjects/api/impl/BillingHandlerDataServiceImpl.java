package org.openmrs.module.openhmis.billableobjects.api.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObjectsService;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
import org.openmrs.module.openhmis.billableobjects.api.model.BaseBillingHandler;
import org.openmrs.module.openhmis.billableobjects.api.security.BasicMetadataAuthorizationPrivileges;
import org.openmrs.module.openhmis.commons.api.PagingInfo;
import org.openmrs.module.openhmis.commons.api.entity.impl.BaseMetadataDataServiceImpl;
import org.openmrs.module.openhmis.commons.api.entity.security.IMetadataAuthorizationPrivileges;

public class BillingHandlerDataServiceImpl extends BaseMetadataDataServiceImpl<BaseBillingHandler>
	implements IBillingHandlerDataService {
	
	private static IBillableObjectsService billableObjectsService;
	
	@Override
	protected IMetadataAuthorizationPrivileges getPrivileges() {
		return new BasicMetadataAuthorizationPrivileges();
	}

	@Override
	protected void validate(BaseBillingHandler object) throws APIException {
		
	}

	/**
	 * Save a billing handler, and if it was new and is successfully saved,
	 * make sure a listener is bound to save the billable object metadata.
	 * 
	 * @param object to be saved
	 * @return saved billing handler
	 * @should rebind listener if object was new
	 * @should not rebind listener when updating
	 */
	@Override
	public BaseBillingHandler save(BaseBillingHandler object) {
		boolean isNew = (object.getId() == null);
		BaseBillingHandler result = super.save(object);
		// If a new handler is successfully saved, make sure event handlers are up to date
		if (isNew && result != null && result.getId() != null)
			billableObjectsService.rebindListenerForAllHandlers();
		return result;
	}
	
	/**
	 * Retire a billing handler, and rebind event listener
	 * 
	 * @param handler to be retired
	 * @return updated handler
	 * @should rebind listener
	 */
	@Override
	public BaseBillingHandler retire(BaseBillingHandler entity, String reason) throws APIException {
		entity = super.retire(entity, reason);
		if (entity.isRetired())
			billableObjectsService.rebindListenerForAllHandlers();
		return entity;
	}
	
	/**
	 * Unretire a billing handler, and rebind event listener
	 * 
	 * @param handler to be unretired
	 * @return updated handler
	 * @should rebind listener
	 */
	@Override
	public BaseBillingHandler unretire(BaseBillingHandler entity) throws APIException {
		entity = super.unretire(entity);
		if (!entity.isRetired())
			billableObjectsService.rebindListenerForAllHandlers();
		return entity;
	}
	
	@Override
	public <T extends BaseBillingHandler> List<T> getAll(PagingInfo pagingInfo, Class<T> clazz) {
		IMetadataAuthorizationPrivileges privileges = getPrivileges();
		if (privileges != null && !StringUtils.isEmpty(privileges.getGetPrivilege())) {
			Context.requirePrivilege(privileges.getGetPrivilege());
		}

		loadPagingTotal(pagingInfo, repository.createCriteria(clazz));

		return executeCriteria(clazz, pagingInfo, null, getDefaultSort());
	}

	public IBillableObjectsService getBillableObjectsService() {
		return billableObjectsService;
	}

	public void setBillableObjectsService(IBillableObjectsService billableObjectsService) {
		BillingHandlerDataServiceImpl.billableObjectsService = billableObjectsService;
	}
	
	
}
