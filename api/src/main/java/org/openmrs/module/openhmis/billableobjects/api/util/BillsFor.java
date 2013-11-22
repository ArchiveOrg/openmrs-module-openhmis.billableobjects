package org.openmrs.module.openhmis.billableobjects.api.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BillsFor {
	Class<?>[] value();
}
