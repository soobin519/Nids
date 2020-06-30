package com.aws.codestar.projecttemplates.controller;
import  com.aws.codestar.projecttemplates.vo.VOSensorData;
import  com.aws.codestar.projecttemplates.vo.VOUser;
import  com.aws.codestar.projecttemplates.vo.VOCar;
import  com.aws.codestar.projecttemplates.services.ServiceSensorData;
import  com.aws.codestar.projecttemplates.services.ServiceUser;
import  com.aws.codestar.projecttemplates.services.ServiceCar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.HashMap;
import javax.inject.Inject;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/Upload")
public class UploadController{
    
    @Inject
    private ServiceSensorData service;
    @Inject
    private ServiceUser service_user;
    @Inject
    private ServiceCar service_car;
    
    public UploadController() {
        
    }
    
    @RequestMapping(produces="application/json;charset=UTF-8",method=RequestMethod.POST)
    public @ResponseBody Map<String, Object> postServer(HttpServletRequest request, HttpServletResponse response) {
        
        String upload_Type = request.getParameter("type");
        System.out.println(upload_Type);
        
        if(upload_Type.equals("no_date"))
        {
            try{
                    String auth = request.getParameter("auth");
                
                    //VOUser user = service_user.selectUserWithAuth(auth);
                    VOCar car = service_car.selectCarWithAuth(auth);
                    if(car != null)
                    {
                
                        VOSensorData test = new VOSensorData();
                        //test.setId(user.getId());
                        test.setId(car.getId());
                        test.setNum(car.getNum());
                        String data = request.getParameter("data"); 
                        test.setData(data);
                        test.setAmount(Integer.parseInt(request.getParameter("amount")));
                        int result = service.insertData(test);
                        System.out.println("Upload Result : " + String.valueOf(result));


                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("success", true);
                        return map;
                    }
                    else{
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("success", false);
                        map.put("reason", "user not found");
                        return map;
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("success", false);
                    return map;
                }
        }
        else if(upload_Type.equals("has_date"))
        {
            try{
                    VOSensorData test = new VOSensorData();
                    test.setId(request.getParameter("id"));
                    test.setData(request.getParameter("data"));
                    String input_date_s = request.getParameter("date");
                    java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				    java.util.Date dateLatestLoginDate = formatter.parse(input_date_s);
                    java.sql.Timestamp input_date = new java.sql.Timestamp( dateLatestLoginDate.getTime() ) ;
                    test.setDate(input_date);
                    test.setAmount(Integer.parseInt(request.getParameter("amount")));

                    int result = service.insertDataWithTime(test);
                    System.out.println("Upload Result : " + String.valueOf(result));
                    
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("success", true);
                    return map;
                
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("success", false);
                    return map;
                }
        }
        else
        {
            System.out.println("POST Server Response");
            Map<String, Object> map = new HashMap<String, Object>();
            //클라이언트 페이지로 부터 Httpclient로 받은 parameter값
            map.put("success", false);
            return map;
        }        
    }   
}