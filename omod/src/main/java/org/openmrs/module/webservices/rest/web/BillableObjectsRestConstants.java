package org.openmrs.module.webservices.rest.web;

public class BillableObjectsRestConstants {
    public static final String MODULE_REST_ROOT = RestConstants.VERSION_2 + "/billableobjects/";

    public static final String HANDLER_RESOURCE = MODULE_REST_ROOT + "handlers";
    public static final String ENCOUNTER_HANDLER_RESOURCE = MODULE_REST_ROOT + "encounterhandlers";
    public static final String DRUGORDER_HANDLER_RESOURCE = MODULE_REST_ROOT + "drugorderhandlers";    

    public static final String ASSOCIATOR_RESOURCE = MODULE_REST_ROOT + "associators";
    public static final String SIMPLE_NEW_ASSOCIATOR_RESOURCE = MODULE_REST_ROOT + "simplenewbillassociators";

}
