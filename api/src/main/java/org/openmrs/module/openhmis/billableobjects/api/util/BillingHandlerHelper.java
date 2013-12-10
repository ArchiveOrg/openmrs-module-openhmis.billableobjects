package org.openmrs.module.openhmis.billableobjects.api.util;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.openhmis.billableobjects.api.IBillingHandlerDataService;
import org.openmrs.module.openhmis.billableobjects.api.model.BaseBillingHandler;
import org.openmrs.module.openhmis.billableobjects.api.model.IBillingHandler;

public class BillingHandlerHelper {
	private static final Logger logger = Logger.getLogger(BillingHandlerHelper.class);
	private static volatile Map<String, Set<IBillingHandler<?>>> handledClassToHandlerSetMap = new HashMap<String, Set<IBillingHandler<?>>>();
	
	public static Set<Class<?>> getActivelyHandledClasses() {
		Set<Class<?>> handledClasses = new HashSet<Class<?>>();
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
			handledClasses.add(handledClass);
			registerHandler(handler, handledClass);
		}
		return handledClasses;
	}
	
	public static void registerHandler(IBillingHandler<?> handler, Class<?> handledClass) {
		if (handler == null)
			throw new APIException("Cannot register null " + IBillingHandler.class.getSimpleName() + ".");
		if (handledClass == null)
			throw new APIException("Cannot register a handler for a null class.");
		if (handledClassToHandlerSetMap.get(handledClass.getName()) == null) {
			Set<IBillingHandler<?>> set = new HashSet<IBillingHandler<?>>();
			set.add(handler);
			handledClassToHandlerSetMap.put(handledClass.getName(), set);
		}
		else {
			handledClassToHandlerSetMap.get(handledClass.getName()).add(handler);
		}
	}
	
	public static Set<IBillingHandler<?>> getHandlersForClassName(String className) {
		return handledClassToHandlerSetMap.get(className);
	}
}