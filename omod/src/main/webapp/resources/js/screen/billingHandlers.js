curl(
    { baseUrl: openhmis.url.resources },
    [
        openhmis.url.backboneBase + 'js/lib/jquery',
        openhmis.url.backboneBase + 'js/openhmis',
        openhmis.url.backboneBase + 'js/view/generic',
        openhmis.url.billableobjBase + 'js/model/billingHandler',
        openhmis.url.billableobjBase + 'js/view/editors',
        openhmis.url.billableobjBase + 'js/view/billingHandler'
    ],
    function($, openhmis) {
    	var	listView, listElement,
    		addEditView, addEditElement,
    		collection;
    		
        $(function() {
            $("#content").append('<div id="existing-form"></div>');
            listElement = $("#existing-form");

            $("#content").append('<div id="add-edit-form"></div>');
            addEditElement = $("#add-edit-form");

			collection = new openhmis.BillingHandlerCollection();

			setupAddEditView(new openhmis.BillingHandlerAddEditView({ collection: collection }));

			listView = new openhmis.GenericListView({
				model: collection,
				listFields: ['name', 'description', 'type']
			});
			rebindEvents();
			listView.setElement(listElement);
			listView.fetch();
		});
        
        function setupAddEditView(view) {
        	if (addEditView) {
        		// We want to reuse the element, so don't remove it with the view
        		addEditView.setElement(undefined);
        		addEditView.remove();
        	}
        	addEditView = view;
			addEditView.setElement(addEditElement);
			addEditView.render();        	
        }
        
        function rebindEvents() {
        	// Reset to base BillingHandler view on cancel
			addEditView.on("cancel", function() {
				setupAddEditView(new openhmis.BillingHandlerAddEditView({ collection: collection }));
				rebindEvents();
				listView.blur();
			});
			addEditView.on("change:type", changeHandlerView);

			listView.on("itemSelect", function(view) { addEditView.edit(view.model); });
        }
        
		function changeHandlerView(editor) {
			try {
				var type = editor.getValue().type.id;
				var modelType;
				if (!type) {
					modelType = openhmis.BillingHandler;
				}
				else if (typeof openhmis[type] === 'function') {
					modelType = openhmis[type];
				}
				if (modelType !== undefined) {
					var handlerView;
					try {
						if (typeof openhmis.billableobj.addEdit[type] === 'function')
							handlerView = new openhmis.billableobj.addEdit[type]({ collection: collection, modelType: modelType }); 
						else
							throw "missing view";
					}
					catch (e) {
						handlerView = new openhmis.BillingHandlerAddEditView({ collection: collection, modelType: modelType });
					}
					
					setupAddEditView(handlerView);
					handlerView.beginAdd();
					rebindEvents();
				}
				else
					throw "missing type";
			}
			catch (e) {
				alert('Missing model or view for "' + type + '" (' + e + ')');
			}
		}		
    }
);