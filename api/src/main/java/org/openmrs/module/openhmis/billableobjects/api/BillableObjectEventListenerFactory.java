package org.openmrs.module.openhmis.billableobjects.api;

import org.openmrs.event.EventListener;
import org.openmrs.module.DaemonToken;
import org.openmrs.module.openhmis.billableobjects.api.util.OpenmrsObjectEventListener;

public class BillableObjectEventListenerFactory {

	private static OpenmrsObjectEventListener instance;
	private static DaemonToken daemonToken; 

	public static EventListener getInstance() {
		if (instance == null)
			instance = new OpenmrsObjectEventListener(daemonToken);
		return instance;
	}
	
	public static void setDaemonToken(DaemonToken token) {
		if (daemonToken == null)
			daemonToken = token;
	}

}
