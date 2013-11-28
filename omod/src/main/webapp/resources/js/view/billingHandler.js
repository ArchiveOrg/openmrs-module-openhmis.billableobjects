define(
	[
		openhmis.url.backboneBase + 'js/lib/jquery',
		openhmis.url.backboneBase + 'js/lib/backbone',
		openhmis.url.backboneBase + 'js/view/generic',
		openhmis.url.backboneBase + 'js/lib/i18n'
	],
	function($, Backbone, openhmis, i18n) {
		openhmis.BillingHandlerAddEditView = openhmis.GenericAddEditView.extend({
			initialize: function(options) {
				openhmis.GenericAddEditView.prototype.initialize.call(this, options);
				if (options && options.modelType)
					this.modelType = options.modelType;
			},
			beginAdd: function(model) {
				var tmpModel = this.collection.model;
				this.collection.model = this.modelType ? this.modelType : this.collection.model;
				
				openhmis.GenericAddEditView.prototype.beginAdd.call(this);
				
				this.collection.model = tmpModel;
				var self = this;
				this.modelForm.on("type:change", function(editor) {
					self.trigger("change:type", editor);
				});
			}
			
//			render: function() {
//				openhmis.GenericAddEditView.prototype.render.call(this);
//				return this;
//			}
			
		});
	}
);