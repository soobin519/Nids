package com.aws.codestar.projecttemplates.controller;
import  com.aws.codestar.projecttemplates.vo.VOCar;
import  com.aws.codestar.projecttemplates.services.ServiceCar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.ModelAndView;
 
import javax.inject.Inject;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.*;
import java.net.URI;

import java.awt.geom.Point2D;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.jhlabs.map.proj.*;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;

import com.jhlabs.map.proj.Projection;
import com.jhlabs.map.proj.ProjectionFactory;

@Controller
public class CarUtilController{
    
    private final String siteName;
    
    @Inject
    private ServiceCar service;
    
    public CarUtilController(final String siteName){
        this.siteName = siteName;
    }
    
    @RequestMapping(value="/CarUtil", produces ="application/json;charset=UTF-8",method=RequestMethod.POST )
    public @ResponseBody Map<String, Object> postServer(HttpServletRequest request, HttpServletResponse response) {
        try{
            request.setCharacterEncoding("UTF-8");
            String req_type = request.getParameter("type");
            System.out.println("map method - " + req_type);
            
            if(req_type.equals("registCar")){
                String id = request.getParameter("id");
                String num = request.getParameter("num");
                int model = Integer.parseInt(request.getParameter("model"));
                
                VOCar car = new VOCar(id, num, model);
                
                int result = service.insertCar(car);
                if(result ==1){ //insert success
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result", "200");
                    map.put("insert", true);
                    return map;
                }
                else{//insert failed
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result", "500");
                    map.put("reason", "db insert error - return false");
                    map.put("insert", false);
                    return map;
                }     
            }
            else if(req_type.equals("deleteCar"))
            {
                String id = request.getParameter("id");
                String num = request.getParameter("num");
                int model = Integer.parseInt(request.getParameter("model"));
                
                VOCar car = new VOCar(id, num, model);
                
                int result = service.deleteCar(car);
                if(result ==1){ //delete success
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result", "200");
                    map.put("delete", true);
                    return map;
                }
                else{//delete failed
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result", "500");
                    map.put("reason", "db delete error - return false");
                    map.put("delete", false);
                    return map;
                } 
            }
            else if(req_type.equals("editCar"))
            {
                String id = request.getParameter("id");
                String num = request.getParameter("num");
                int model = Integer.parseInt(request.getParameter("model"));
                
                VOCar car = new VOCar(id, num, model);
                
                int result = service.editCar(car);
                if(result ==1){ //edit success
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result", "200");
                    map.put("edit", true);
                    return map;
                }
                else{//edit failed
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result", "500");
                    map.put("reason", "db edit error - return false");
                    map.put("edit", false);
                    return map;
                } 
            }
             else if(req_type.equals("checkCar")) //모바일 차량 정보 등록 여부 확인
            {
                
                String id = request.getParameter("id");

                VOCar car = service.selectCarWithID(id);
                if(car != null){
                    System.out.println("search result - not null : " + car.getId()); //getId 다시 체크
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result", "200");
                    map.put("exist", true);
                    return map;
                }
                else{
                    System.out.println("search result - null ");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result", "200");
                    map.put("exist", false);
                    return map;
                }          
            }
            // else if(req_type.equals("selectCarNum")) //차량 번호 도메인 주소로
            // {
                
            //     String num = request.getParameter("num");

            //     VOCar car = service.selectCarNum(num);
            //     if(car != null){
            //         System.out.println("search result - not null : " + car.getNum());
            //         Map<String, Object> map = new HashMap<String, Object>();
            //         map.put("result", "200");
            //         map.put("exist", true);
            //         return map;
            //     }
            //     else{
            //         System.out.println("search result - null ");
            //         Map<String, Object> map = new HashMap<String, Object>();
            //         map.put("result", "200");
            //         map.put("exist", false);
            //         return map;
            //     }          
            // }
            else
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", "500");
                map.put("reason", "type error : not defined - " + req_type);
                return map;
            }
            
        }     
        
        catch(Exception e){
            e.printStackTrace();
            Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", "500");
                map.put("reason", "exception occured! - " + e.getMessage());
                return map;
        }
    }
}
