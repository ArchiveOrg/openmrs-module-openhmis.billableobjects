define(
	[
		openhmis.url.backboneBase + 'js/lib/jquery',
		openhmis.url.backboneBase + 'js/lib/backbone',
		openhmis.url.backboneBase + 'js/lib/underscore',
		openhmis.url.backboneBase + 'js/view/generic',
		openhmis.url.backboneBase + 'js/lib/i18n'
	],
	function($, Backbone, _, openhmis, __) {
		// Create new generic add/edit screen
		openhmis.MultiTypeAddEditScreen = Backbone.View.extend({
			initialize: function(options) {
				_.bindAll(this, 'changeHandlerView');
				this.model = options.modelType || openhmis.GenericModel;
				this.modelTypeAttr = options.typeAttr || "type";
				this.baseType = options.baseType || this.model || openhmis.GenericModel;

				this.collection = options.collection || new openhmis.GenericCollection([], {
					url: this.model.prototype.meta.restUrl,
					model: this.modelType
				});

				this.baseAddEditViewType = options.addEditViewType || openhmis.MutiTypeAddEditView;

				var viewOptions = _.extend({
					model: this.collection
				}, options);
				var listViewType = options.listViewType || openhmis.GenericListView;
				this.listView = new listViewType(viewOptions);
			},
			
			setupAddEditView: function(view) {
	        	if (this.addEditView) {
	        		// We want to reuse the element, so don't remove it with the view
	        		this.addEditView.setElement(undefined);
	        		this.addEditView.remove();
	        	}
	        	this.addEditView = view;
				this.addEditView.setElement(this.$addEditElement);
				this.addEditView.render();
			},
			
			rebindEvents: function() {
				var self = this;
	        	// Reset to base type view on cancel
				self.addEditView.on("cancel", function() {
					self.setupAddEditView(new self.baseAddEditViewType({ collection: self.collection }));
					self.rebindEvents();
					self.listView.blur();
				});
				self.addEditView.on(self.modelTypeAttr + ":change", self.changeHandlerView);
				self.listView.on("itemSelect", function(view) { self.addEditView.edit(view.model); });
	        },
			
	        changeHandlerView: function(editor) {
				try {
					var type = editor.getValue()[this.modelTypeAttr].id;
					var modelType;
					if (!type) {
						modelType = this.baseType;
					}
					else if (typeof openhmis[type] === 'function') {
						modelType = openhmis[type];
					}
					if (modelType !== undefined) {
						var handlerView;
						var handlerViewOptions = {
							collection: this.collection,
							modelType: modelType
						}
						try {
							if (typeof openhmis.addEdit[type] === 'function')
								handlerView = new openhmis.addEdit[type](handlerViewOptions); 
							else
								throw "missing view";
						}
						catch (e) {
							handlerView = new this.baseAddEditViewType(handlerViewOptions);
						}
						
						this.setupAddEditView(handlerView);
						handlerView.beginAdd();
						this.rebindEvents();
					}
					else
						throw "missing type";
				}
				catch (e) {
					alert('Missing model or view for "' + type + '" (' + e + ')');
				}
	        },
	        
			render: function() {
				this.$el.html("");
				this.$listElement = $('<div id="existing-form"></div>'); 
                this.$el.append(this.$listElement);

				this.$addEditElement = $('<div id="add-edit-form"></div>'); 
                this.$el.append(this.$addEditElement);

				var newAddEditView = new this.baseAddEditViewType({ collection: this.collection });
				this.setupAddEditView(newAddEditView);
				this.rebindEvents();
				this.listView.setElement(this.$listElement);
				this.listView.fetch();
                return this;
			}
		});
		
		openhmis.MultiTypeAddEditView = openhmis.GenericAddEditView.extend({
			initialize: function(options) {
				openhmis.GenericAddEditView.prototype.initialize.call(this, options);
				if (options) {
					this.modelType = options.modelType;
					this.modelTypeAttr = options.modelTypeAttr || "type";
				}
			},
			beginAdd: function(model) {
				// This is a bit of a hack to pass in a different model type than
				// is set in the collection
				var tmpModel = this.collection.model;
				this.collection.model = this.modelType ? this.modelType : this.collection.model;
				
				openhmis.GenericAddEditView.prototype.beginAdd.call(this);
				
				this.collection.model = tmpModel;

				var self = this;
				this.modelForm.on(this.modelTypeAttr + ":change", function(editor) {
					self.trigger(self.modelTypeAttr + ":change", editor);
				});
			},
			
			prepareModelForm: function(model, options) {
				options = options ? options : {};
				if (model.id !== undefined && model.schema !== undefined) {
					options.schema = $.extend({}, model.schema);
					delete options.schema.type;
				}
				return openhmis.GenericAddEditView.prototype.prepareModelForm.call(this, model, options);
			},
		});
	}
);