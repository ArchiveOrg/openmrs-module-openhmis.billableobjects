curl(
    { baseUrl: openhmis.url.resources },
    [
        openhmis.url.backboneBase + 'js/lib/jquery',
        openhmis.url.backboneBase + 'js/openhmis',
        openhmis.url.backboneBase + 'js/view/generic',
        openhmis.url.backboneBase + 'js/view/nestedExistingModel', // for exiting model select
        openhmis.url.backboneBase + 'js/view/editors',  // for ListSelect
        openhmis.url.inventoryBase + 'js/view/editors', // for item selection
        openhmis.url.billableobjBase + 'js/model/generic',
        openhmis.url.billableobjBase + 'js/model/billingHandler',
        openhmis.url.billableobjBase + 'js/view/editors',
        openhmis.url.billableobjBase + 'js/view/generic',
        openhmis.url.billableobjBase + 'js/view/billingHandler'
    ],
    function($, openhmis) {
    	var screen = new openhmis.MultiTypeAddEditScreen({
    		modelType: openhmis.BillingHandler,
    		collection: new openhmis.InheritanceCollection(null, { model: openhmis.BillingHandler }),
    		addEditViewType: openhmis.MultiTypeAddEditView,
    		listFields: ["name", "description", "type"]
    	});
    	$("#content").append(screen.render().el);
    }

);