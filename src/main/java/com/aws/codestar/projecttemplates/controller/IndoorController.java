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
@RequestMapping("/Indoor")
public class IndoorController {

    private final String siteName;

    public IndoorController(final String siteName) {		
        this.siteName = siteName;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView helloWorld(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("indoor");
        mav.addObject("siteName", this.siteName);

    String car_num = request.getParameter("num");
    try    {
        car_num = new String(car_num.getBytes("8859_1"), "UTF-8");
    }
    catch(Exception e)    {
        e.printStackTrace();
    }
        mav.addObject("car_num", car_num);
        System.out.println(car_num+"indoor controller 프린트");
        
        return mav;
    }

}
