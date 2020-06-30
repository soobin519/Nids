package com.aws.codestar.projecttemplates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.*;
/**
 * Basic Spring MVC controller that handles all GET requests.
 */
@Controller
@RequestMapping("/Tvoc")
public class TvocController {

    private final String siteName;

    public TvocController(final String siteName) {
		
        this.siteName = siteName;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView helloWorld() {
        ModelAndView mav = new ModelAndView("tvoc");
        mav.addObject("siteName", this.siteName);
        return mav;
    }

}
