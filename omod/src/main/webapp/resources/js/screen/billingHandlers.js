curl(
    { baseUrl: openhmis.url.resources },
    [
        openhmis.url.backboneBase + 'js/lib/jquery',
        openhmis.url.backboneBase + 'js/openhmis',
        openhmis.url.backboneBase + 'js/lib/backbone-forms',
        openhmis.url.backboneBase + 'js/view/generic',
        openhmis.url.billableobjBase + 'js/model/billingHandler',
    ],
    function($, openhmis) {
        $(function() {
            openhmis.startAddEditScreen(openhmis.BillingHandler, {
                listFields: ['name', 'description']
            });
        });
    }
);