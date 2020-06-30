package com.aws.codestar.projecttemplates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Basic Spring MVC controller that handles all GET requests.
 */
@Controller
@RequestMapping("/")
public class MainController {

    private final String siteName;

    public MainController(final String siteName) {		
        this.siteName = siteName;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView helloWorld(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("siteName", this.siteName);
        
        String car_num = request.getParameter("num");
         try    {
        car_num = new String(car_num.getBytes("8859_1"), "UTF-8");
    }
    catch(Exception e)    {
        e.printStackTrace();
    }
        
        mav.addObject("car_num", car_num);
        System.out.println(car_num+"main controller 프린트");
        
        return mav;
    }
  

}
