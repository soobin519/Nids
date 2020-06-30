package com.aws.codestar.projecttemplates;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.aws.codestar.projecttemplates.configuration.MvcConfig;

/**
 * Utility to initialize the Spring MVC HelloWorld application.
 */
public class MainAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
                MvcConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return null;
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {
                "/", "/Main", "/Upload","/DataLoad", "/JusoMobilePopup", "/JusoPopup", "/UserUtil", "/Menu", "/DeviceUtil", "/CarUtil", "/Outdoor", "/Indoor", "/Map", "/Tvoc", "/Monitor", "/Monitorin"
        };
    }

}
