define(
    [
        openhmis.url.backboneBase + 'js/openhmis',
        openhmis.url.backboneBase + 'js/lib/i18n',
        openhmis.url.backboneBase + 'js/model/generic',
    ],
    function(openhmis, __) {
    	openhmis.InheritanceCollection = openhmis.GenericCollection.extend({
        	add: function(models, options) {
        		var processedModels = [];
        		models = _.isArray(models) ? models.slice() : [models];
        		for (i in models) {
        			if (models[i].type && openhmis[models[i].type])
        				processedModels.unshift(new openhmis[models[i].type](models[i])); 
        			else
        				processedModels.unshift(models[i]);
        		}
        		openhmis.GenericCollection.prototype.add.call(this, processedModels, options);
        	}
        });
    }
);