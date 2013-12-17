package org.openmrs.module.openhmis.billableobjects.api.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openmrs.annotation.Handler;
import org.openmrs.event.Event;
import org.openmrs.event.Event.Action;
import org.openmrs.module.openhmis.billableobjects.api.BillableObjectEventListenerFactory;
import org.openmrs.module.openhmis.billableobjects.api.model.IBillableObject;
import org.reflections.Reflections;

public class BillableObjectsHelper {
	private static final Logger logger = Logger.getLogger(BillableObjectsHelper.class);
	private static volatile Map<String, Class<? extends IBillableObject>> classNameToBillableObjectTypeMap;
	
	/**
	 * Search the type package for any concrete class that implements the
	 * IBillableObject interface.  Search for Handler annotation on
	 * results and map the OpenMRS class to its billable object class.
	 * 
	 * @return
	 */
	private static Map<String, Class<? extends IBillableObject>> getClassNameToBillableObjectTypeMap() {
		if (classNameToBillableObjectTypeMap == null) {
			classNameToBillableObjectTypeMap = new HashMap<String, Class<? extends IBillableObject>>();
			Reflections reflections = new Reflections("org.openmrs.module.openhmis.billableobjects.api.type");
	
			Set<Class<? extends IBillableObject>> allClasses = reflections.getSubTypesOf(IBillableObject.class);
	
			for (Class<? extends IBillableObject> cls : allClasses) {
				if (Modifier.isAbstract(cls.getModifiers())) continue;
				Annotation[] annotations = cls.getAnnotations();
				if (annotations.length == 0) {
					logger.warn("Found class implementing " + IBillableObject.class.getSimpleName() + " but it does not specify a handled class: skipping.");
					continue;
				}
				
				for (Annotation annotation : annotations) {
					if (annotation instanceof Handler) {
						Handler handler = (Handler) annotation;
						Class<?>[] supported = handler.supports();
					for (Class<?> supportedClass : supported)
			    		classNameToBillableObjectTypeMap.put(supportedClass.getName(), cls);
					}
				}
			}
		}
		return classNameToBillableObjectTypeMap;
	}
	
	public static Class<? extends IBillableObject> getBillableObjectTypeForClassName(String className) {
		return getClassNameToBillableObjectTypeMap().get(className);
	}
	
	public static Set<String> getHandledTypeNames() {
		return getClassNameToBillableObjectTypeMap().keySet();		
	}

	/**
	 * @should bind all existing handlers
	 */
	public static void bindListenerForAllHandlers() {
		for (Class<?> handledClass : BillingHandlerHelper.getActivelyHandledClasses()) {
			Event.subscribe(
					handledClass,
					Action.CREATED.toString(), 
					BillableObjectEventListenerFactory.getInstance()
			);
		}
	}

	public static void unbindListenerForAllHandlers() {
		Set<String> handledTypeNames = getHandledTypeNames();
		for (String typeName : handledTypeNames) {
			Class<?> cls;
			try {
				cls = Class.forName(typeName);
			} catch (ClassNotFoundException e) {
				logger.warn("Could not find class \"" + typeName + "\": not unbinding any event handler.");
				continue;
			}
			Event.unsubscribe(
				cls,
				Action.CREATED,
				BillableObjectEventListenerFactory.getInstance()
			);
		}
	}
}
