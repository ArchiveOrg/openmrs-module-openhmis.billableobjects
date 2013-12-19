package org.openmrs.module.openhmis.billableobjects.api.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openmrs.annotation.Handler;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.event.Event;
import org.openmrs.event.Event.Action;
import org.openmrs.module.openhmis.billableobjects.api.BillableObjectEventListenerFactory;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObjectsService;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerService;
import org.openmrs.module.openhmis.billableobjects.api.type.IBillableObject;
import org.reflections.Reflections;

public class BillableObjectsServiceImpl extends BaseOpenmrsService implements IBillableObjectsService {
	private static final Logger logger = Logger.getLogger(BillableObjectsServiceImpl.class);
	private static IBillingHandlerService billingHandlerService;
	private static volatile Map<String, Class<? extends IBillableObject>> classNameToBillableObjectTypeMap;
	
	/**
	 * Search the type package for any concrete class that implements the
	 * IBillableObject interface.  Search for Handler annotation on
	 * results and map the OpenMRS class to its billable object class.
	 * 
	 * @return generated map from class to corresponding IBillableObject
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
	
	public Class<? extends IBillableObject> getBillableObjectClassForClassName(String className) {
		return getClassNameToBillableObjectTypeMap().get(className);
	}
	
	public Set<String> getBillableClassNames() {
		return getClassNameToBillableObjectTypeMap().keySet();		
	}

	/**
	 * @see IBillableObjectsService#rebindListenerForAllHandlers()
	 */
	public void rebindListenerForAllHandlers() {
		unbindListenerForAllHandlers();
		for (Class<?> handledClass : billingHandlerService.getActivelyHandledClasses()) {
			Event.subscribe(
					handledClass,
					Action.CREATED.toString(), 
					BillableObjectEventListenerFactory.getInstance()
			);
		}
	}

	public void unbindListenerForAllHandlers() {
		Set<String> handledTypeNames = getBillableClassNames();
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
	
	@Override
	public IBillingHandlerService getBillingHandlerService() {
		return billingHandlerService;
	}

	@Override
	public void setBillingHandlerService(IBillingHandlerService billingHandlerService) {
		BillableObjectsServiceImpl.billingHandlerService = billingHandlerService;
	}

}
