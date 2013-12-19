package org.openmrs.module.openhmis.billableobjects.api.model;

import org.openmrs.OpenmrsObject;
import org.openmrs.module.openhmis.commons.api.entity.model.BaseSerializableOpenmrsMetadata;

public abstract class BaseBillingHandler<T extends OpenmrsObject> extends BaseSerializableOpenmrsMetadata implements IBillingHandler<T> {
}