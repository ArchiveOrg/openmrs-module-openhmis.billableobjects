package org.openmrs.module.openhmis.billableobjects.api.listener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

import org.apache.log4j.Logger;
import org.openmrs.OpenmrsObject;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.event.EventListener;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObject;
import org.openmrs.module.openhmis.billableobjects.api.IBillableObjectDataService;
import org.openmrs.module.openhmis.billableobjects.api.util.BillableObjectsHelper;

public class BillableObjectEventListener implements EventListener {
	private static final Logger logger = Logger.getLogger(BillableObjectEventListener.class);

	@Override
	public void onMessage(Message message) {
		try {
			MapMessage mapMessage = (MapMessage) message;
			String className = mapMessage.getString("classname");
			String associatedUuid = mapMessage.getString("uuid");
			
			IBillableObject billableObject;
			Class<? extends IBillableObject> cls = BillableObjectsHelper
					.getBillableObjectTypeForClassName(className);
			if (cls != null)
				billableObject = cls.newInstance();
			else
				throw new APIException("No handler found for class " + className + ".");
			OpenmrsObject associatedObject = billableObject.getObjectByUuid(associatedUuid);
			billableObject.setObject(associatedObject);			
			Context.getService(IBillableObjectDataService.class).save(billableObject);
		}
		catch (InstantiationException e) {
			logger.error("Error instantiating IBillableObject class: " + e.getMessage());
		}
		catch (JMSException e) {
			logger.error("Error reading message: " + e.getMessage());
		} catch (Exception e) {
			logger.error("Error handling message: " + e.getMessage());
		}
	}

}
