package org.openmrs.module.openhmis.billableobjects.api.util;

import java.util.Collection;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

import org.apache.log4j.Logger;
import org.openmrs.Encounter;
import org.openmrs.api.context.Context;
import org.openmrs.event.EventListener;
import org.openmrs.module.openhmis.cashier.api.model.Bill;
import org.openmrs.module.openhmis.cashier.api.model.BillLineItem;
import org.openmrs.module.openhmis.inventory.api.model.Item;

public class BillableObjectHandler implements EventListener {
	private static final Logger logger = Logger.getLogger(BillableObjectHandler.class);
	
	private Collection<Class<?>> handledClasses;
	
	@Override
	public void onMessage(Message message) {
//		try {
			MapMessage mapMessage = (MapMessage) message;
			// Need some annotation on types to check which one to save I guess
//		}
//		catch (JMSException e) {
//			logger.error("Error reading message.");
//		}
	}
	
	public boolean addHandledClass(Class<?> handledClass) {
		return handledClasses.add(handledClass);
	}
}
