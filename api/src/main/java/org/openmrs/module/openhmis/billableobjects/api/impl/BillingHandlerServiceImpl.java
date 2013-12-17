package org.openmrs.module.openhmis.billableobjects.api.impl;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerService;
import org.openmrs.module.openhmis.billableobjects.api.model.BaseBillingHandler;
import org.openmrs.module.openhmis.billableobjects.api.model.IBillingHandler;
import org.reflections.Reflections;

public class BillingHandlerServiceImpl extends BaseOpenmrsService implements IBillingHandlerService {
	private static final Logger logger = Logger.getLogger(BillingHandlerServiceImpl.class);
	private static Set<Class<? extends IBillingHandler>> billingHandlerClasses;
	private static List<String> billingHandlerClassNames;
	private static volatile Map<Class<?>, Set<IBillingHandler<?>>> handledClassToHandlerSetMap = new HashMap<Class<?>, Set<IBillingHandler<?>>>();

	/**
	 * Search for any concrete class that implements the IBillingHandler 
	 * interface (or return cached set)
	 * 
	 * @return set of handler classes
	 */
	public Set<Class<? extends IBillingHandler>> getBillingHandlerClasses() {
		if (billingHandlerClasses == null) {
			billingHandlerClasses = new HashSet<Class<? extends IBillingHandler>>();
			Reflections reflections = new Reflections("org.openmrs.module");
			for (Class<? extends IBillingHandler> cls : reflections.getSubTypesOf(IBillingHandler.class)) {
				// We only care about public instantiable classes so ignore others
				if (!cls.isInterface() &&
						!Modifier.isAbstract(cls.getModifiers()) &&
						Modifier.isPublic(cls.getModifiers())) {
					billingHandlerClasses.add(cls);
				}
			}
		}
		return billingHandlerClasses;
	}
	
	private static Map<Class<?>, Set<IBillingHandler<?>>> getHandledClassToHandlerSetMap() {
		return getHandledClassToHandlerSetMap(false);
	}

	/**
	 * Get map from handled classes to a set of associated handlers.
	 * 
	 * @param refresh true to refresh the cached list
	 * @return map from handled classes to handler set
	 */
	private static Map<Class<?>, Set<IBillingHandler<?>>> getHandledClassToHandlerSetMap(boolean refresh) {
		if (handledClassToHandlerSetMap == null || refresh) {
			handledClassToHandlerSetMap = new HashMap<Class<?>, Set<IBillingHandler<?>>>();
			List<BaseBillingHandler> handlers = Context.getService(IBillingHandlerDataService.class).getAll();
			for (IBillingHandler handler : handlers) {
				Class<?> handledClass = (Class<?>) ((ParameterizedType) handler.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
				if (handledClass == null) {
					logger.warn(String.format("Error determining handled class for %s class %s.",
							IBillingHandler.class.getSimpleName(),
							handler.getClass().getSimpleName()
					));
					continue;
				}
				registerHandler(handler, handledClass);
			}
		}
		return handledClassToHandlerSetMap;
	}

	/**
	 * Update internal map from class to handler
	 * 
	 * @param handler
	 * @param handled class
	 */
	private static void registerHandler(IBillingHandler<?> handler, Class<?> handledClass) {
		if (handler == null)
			throw new APIException("Cannot register null " + IBillingHandler.class.getSimpleName() + ".");
		if (handledClass == null)
			throw new APIException("Cannot register a handler for a null class.");
		if (handledClassToHandlerSetMap.get(handledClass.getName()) == null) {
			Set<IBillingHandler<?>> set = new HashSet<IBillingHandler<?>>();
			set.add(handler);
			handledClassToHandlerSetMap.put(handledClass, set);
		}
		else {
			handledClassToHandlerSetMap.get(handledClass.getName()).add(handler);
		}
	}
	
	/**
	 * Get the list handled classes by looking up the current list of saved
	 * handlers. 
	 * 
	 * @return set of handled classes
	 * @should return currently handled classes
	 */
	public Set<Class<?>> getActivelyHandledClasses() {
		return getHandledClassToHandlerSetMap(true).keySet();
	}
	
	public Set<IBillingHandler<?>> getHandlersForClassName(String className) {
		return getHandledClassToHandlerSetMap().get(className);
	}

	/**
	 * Get alphabetically ordered list of available handler class names
	 * 
	 * @return list of handler class names
	 * @should return all names in alphabetical order
	 */
	public List<String> getHandlerTypeNames() {
		if (billingHandlerClassNames == null) {
			billingHandlerClassNames = new ArrayList<String>(getBillingHandlerClasses().size());
			for (Class<? extends IBillingHandler> cls : getBillingHandlerClasses()) {
				billingHandlerClassNames.add(cls.getSimpleName());
			}
			Collections.sort(billingHandlerClassNames);
		}
		return billingHandlerClassNames;
	}
}