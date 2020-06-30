package com.aws.codestar.projecttemplates.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.aws.codestar.projecttemplates.controller.*;
/**
 * Spring configuration for sample application.
 */
@Configuration
@ComponentScan({ "com.aws.codestar.projecttemplates.configuration" })
@PropertySource("classpath:application.properties")
public class ApplicationConfig {

    /**
     * Retrieved from properties file.
     */
    @Value("${Main.SiteName}")
    private String siteName;

  
    @Bean
    public MainController helloWorld() {
        return new MainController(this.siteName);
    }
    
    @Bean
    public TvocController doTvoc() {
        return new TvocController(this.siteName);
    }
    
    @Bean
    public MonitorController doMonitor() {
        return new MonitorController(this.siteName);
    }

    @Bean
    public MonitorInController doMonitorin() {
        return new MonitorInController(this.siteName);
    }
    
    @Bean
    public UploadController uploadData(){
        return new UploadController();
    }
        
    @Bean
    public DataLoadController loadData(){
        return new DataLoadController();
    }
    
    @Bean
    public JusoPopupController doPopup(){
        return new JusoPopupController(this.siteName);
    }
    
    @Bean
    public JusoMobilePopupController doMobilePopup()    {
        return new JusoMobilePopupController(this.siteName);
    }
    
    @Bean
    public UserUtilController doUserManage(){
        return new UserUtilController(this.siteName);
    }
    
    @Bean
    public CarUtilController doCarManage(){
        return new CarUtilController(this.siteName);
    }
    
    @Bean
    public MapController doMapManage(){
        return new MapController(this.siteName);
    }
    
    @Bean
    public DeviceUtilController doDeviceManage(){
        return new DeviceUtilController(this.siteName);
    }
    
    @Bean
    public MenuController loadMenu(){
        return new MenuController(this.siteName);
    }

    @Bean
    public OutdoorController loadOutdoor(){
        return new OutdoorController(this.siteName);
    }
    
    @Bean
    public IndoorController loadIndoor(){
        return new IndoorController(this.siteName);
    }

    /**
     * Required to inject properties using the 'Value' annotation.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}
