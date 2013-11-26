package org.openmrs.module.openhmis.billableobjects.web.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/module/openhmis/billableobjects/handlers")
public class BillingHandlersController {
	@RequestMapping(method = RequestMethod.GET)
	public void cashPoints(ModelMap model) throws JsonGenerationException, JsonMappingException, IOException {
		model.addAttribute("modelBase", "openhmis.billableobjects.billingHandler");
	}
}
