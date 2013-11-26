define(
    [
        openhmis.url.backboneBase + 'js/openhmis',
        openhmis.url.backboneBase + 'js/lib/i18n',
        openhmis.url.backboneBase + 'js/model/generic'
    ],
    function(openhmis, __) {
        openhmis.BillingHandler = openhmis.GenericModel.extend({
            meta: {
                name: __("Billing Handler"),
                openmrsType: 'metadata',
                restUrl: 'v2/billableobjects/handlers'
            },

            schema: {
                name: 'Text',
                description: 'Text'
            },
            
            validate: function(attrs, options) {
    			if (!attrs.name) return { name: __("A name is required") }
                return null;
            },

            toString: function() {
                return this.get('name');
            }
        });

        return openhmis;
    }
);