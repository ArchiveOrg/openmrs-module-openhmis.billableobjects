package org.openmrs.module.openhmis.billableobjects.api;

import org.openmrs.event.EventListener;
import org.openmrs.module.DaemonToken;
import org.openmrs.module.openhmis.billableobjects.api.util.BillableObjectEventListener;

public class BillableObjectEventListenerFactory {

	private static volatile BillableObjectEventListener instance;
	private static DaemonToken daemonToken; 

	public static EventListener getInstance() {
		if (instance == null)
			instance = new BillableObjectEventListener(daemonToken);
		return instance;
	}
	
	public static void setDaemonToken(DaemonToken token) {
		if (daemonToken == null)
			daemonToken = token;
	}

}
