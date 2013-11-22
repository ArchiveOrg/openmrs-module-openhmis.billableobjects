package org.openmrs.module.openhmis.eventbasedbilling.api.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BillsFor {
	Class<?>[] value();
}
