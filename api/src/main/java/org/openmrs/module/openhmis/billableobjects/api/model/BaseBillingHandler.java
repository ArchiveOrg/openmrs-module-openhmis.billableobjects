package org.openmrs.module.openhmis.billableobjects.api.model;

import org.apache.log4j.Logger;
import org.openmrs.OpenmrsObject;
import org.openmrs.module.openhmis.commons.api.entity.model.BaseSerializableOpenmrsMetadata;

public abstract class BaseBillingHandler<T extends OpenmrsObject> extends BaseSerializableOpenmrsMetadata implements IBillingHandler<T> {
	private static final Logger logger = Logger.getLogger(BaseBillingHandler.class);
}