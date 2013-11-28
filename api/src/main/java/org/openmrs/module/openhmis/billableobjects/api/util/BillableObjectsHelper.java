package org.openmrs.module.openhmis.billableobjects.api.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObject;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandler;
import org.reflections.Reflections;

public class BillableObjectsHelper extends BaseOpenmrsService {

	private static volatile List<String> handlerTypeNames;
	private static volatile Map<String, Class<? extends IBillableObject>> classNameToBillableObjectTypeMap;
	
	public static List<Class<? extends IBillingHandler>> locateHandlers() {
		// Search for any modules that define classes which implement the IBillingHandler interface
		Reflections reflections = new Reflections("org.openmrs.module");
		List<Class<? extends IBillingHandler>> classes = new ArrayList<Class<? extends IBillingHandler>>();
		for (Class<? extends IBillingHandler> cls : reflections.getSubTypesOf(IBillingHandler.class)) {
			// We only care about public instantiable classes so ignore others
			if (!cls.isInterface() &&
					!Modifier.isAbstract(cls.getModifiers()) &&
					Modifier.isPublic(cls.getModifiers())) {
				classes.add(cls);
			}
		}		
		return classes;
	}
	
	private static Map<String, Class<? extends IBillableObject>> locateBillableObjectTypes() {
		Map<String, Class<? extends IBillableObject>> classNameToBillableObjectTypeMap = new HashMap<String, Class<? extends IBillableObject>>();
		Reflections reflections = new Reflections("org.openmrs.module.openhmis.billableobjects.api.type");

		Set<Class<? extends IBillableObject>> allClasses = reflections.getSubTypesOf(IBillableObject.class);

		for (Class<? extends IBillableObject> cls : allClasses) {
		   Annotation[] annotations = cls.getAnnotations();

		   for (Annotation annotation : annotations) {
		     if (annotation instanceof Handler) {
		    	Handler handler = (Handler) annotation;
		    	Class<?>[] supported = handler.supports();
		    	for (Class<?> supportedClass : supported)
		    		classNameToBillableObjectTypeMap.put(supportedClass.getName(), cls);
		     }
		   }
		}
		return classNameToBillableObjectTypeMap;
	}
	
	public static List<String> getHandlerTypeNames() {
		if (handlerTypeNames == null) {
			List<Class<? extends IBillingHandler>> classes = locateHandlers();
			handlerTypeNames = new ArrayList<String>(classes.size());
			for (Class<? extends IBillingHandler> cls : classes) {
				handlerTypeNames.add(cls.getSimpleName());
			}
		}
		return handlerTypeNames;
	}
	
	public static Class<? extends IBillableObject> getBillableObjectTypeForClassName(String className) {
		if (classNameToBillableObjectTypeMap == null)
			classNameToBillableObjectTypeMap = locateBillableObjectTypes();
		return classNameToBillableObjectTypeMap.get(className);
	}

	
	@Override
	public void onStartup() {
		super.onStartup();
		Context.openSessionWithCurrentUser();
		Context.closeSessionWithCurrentUser();
	}
}
