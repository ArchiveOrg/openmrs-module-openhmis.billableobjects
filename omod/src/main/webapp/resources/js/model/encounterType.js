define(
    [
        openhmis.url.backboneBase + 'js/openhmis',
        openhmis.url.backboneBase + 'js/lib/i18n',
        openhmis.url.backboneBase + 'js/model/generic'
    ],
    function(openhmis, __) {
        openhmis.EncounterType = openhmis.GenericModel.extend({
            meta: {
                name: __("Encounter Type"),
                openmrsType: 'metadata',
                restUrl: 'v1/encountertype'
            },

            schema: {
                name: 'Text',
                description: 'Text'
            }
        });

        return openhmis;
    }
);