package com.aws.codestar.projecttemplates.controller;
import  com.aws.codestar.projecttemplates.vo.VOSensorData;
import  com.aws.codestar.projecttemplates.vo.VODevice;
import  com.aws.codestar.projecttemplates.vo.VOUser;
import  com.aws.codestar.projecttemplates.vo.VOGps;
import  com.aws.codestar.projecttemplates.services.ServiceSensorData;
import  com.aws.codestar.projecttemplates.services.ServiceUser;
import  com.aws.codestar.projecttemplates.services.ServiceDevice;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.net.*;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;

import java.awt.geom.Point2D;
import com.jhlabs.map.proj.Projection;
import com.jhlabs.map.proj.ProjectionFactory;

@Controller
// @RequestMapping("/DataLoad")
// @ResponseBody
public class DataLoadController {
    @Inject
    private ServiceSensorData service;
    
    @Inject
    private ServiceUser service_u;
    
    public DataLoadController() {
        
    }
    
    
    @RequestMapping(produces="application/json;charset=UTF-8",method=RequestMethod.POST, value="/DataLoad")
    public @ResponseBody Map<String, Object> postServer(HttpServletRequest request, HttpServletResponse response/*, String lat, String lon, VOGps dto*/) {
        try
        {
            
            //request.getCharacterEncoding("UTF-8");
            String req_type = request.getParameter("type");
            System.out.println(req_type);
            if(req_type.equals("withid"))
            {
                String user_id = request.getParameter("id");
                
                VOSensorData result = service.selectLastData(user_id);

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", "200");
                map.put("idx", result.getIdx());
                map.put("data", result.getData());
                map.put("date", result.getDate());
                map.put("amount", result.getAmount());
                map.put("lat", result.getLat()); //추가
                map.put("lon", result.getLon());
                return map;
            }
            else if(req_type.equals("withauth"))
            {
                String auth_code = request.getParameter("auth");
                System.out.println("authcode : " + auth_code);
                
                VOUser user = service_u.selectUserWithAuth(auth_code);
                if(user != null){
                    
                    if(user.getAuth().equals(auth_code))
                    {
                        String user_id = user.getId();
                        List<VOSensorData> result = service.selectDataWithHour(user_id, -1);
                        System.out.println("result size : " + String.valueOf(result.size()));
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("result", true);
                        map.put("data", result);
                        return map;
                    }
                    else
                    {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("result", false);
                        return map;
                    }
                }
                else{
                    System.out.println("search result - null ");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result", false);
                    return map;
                }
            }
            else if(req_type.equals("withhour"))
            {
                String user_id = request.getParameter("id");
                List<VOSensorData> result = service.selectDataWithHour(user_id, -1);
                System.out.println("result size : " + String.valueOf(result.size()));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", "200");
                map.put("data", result);
                return map;
            }
            else if(req_type.equals("withcarnum"))
            {
                String car_num = request.getParameter("num");
                System.out.println("car_num : " + car_num);

                List<VOSensorData> result = service.selectDataWithCarNum(car_num, -1);
                System.out.println("result size : " + String.valueOf(result.size()));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", "200");
                map.put("data", result);
                return map;       
            }
            else if(req_type.equals("select_locat"))
            {
                String user_id = request.getParameter("id");
                
                List<VOSensorData> result = service.selectLocatWithID(user_id);
                System.out.println("result size : " + String.valueOf(result.size()));
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", "200");
                map.put("data", result);
                return map;       
            }
               
            else if(req_type.equals("outdoor"))
            {
                String station_name = request.getParameter("station");
                //System.out.println(station_name);
                String str_response = "";
                String addr = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty"+"?ServiceKey=";
                String serviceKey = "U6jMUnCIHDYkWAQmMXEcpEuHxl1N%2F5Tk0T9CTl0CVyDYCydnOwfN1wG0R7zgZunR6P6DTBdG6b6cihUuicC%2FPQ%3D%3D";
                String parameter = "";

                //serviceKey = URLEncoder.encode(serviceKey, "UTF-8");

                 //parameter setting
                parameter = parameter + "&" + "numOfRows=1";
                parameter = parameter + "&" + "pageNo=1";
                parameter = parameter + "&" + "ver=1.3";
                parameter = parameter + "&" + "dataTerm=DAILY";
                parameter = parameter + "&" + "stationName="+station_name;
                parameter = parameter + "&" + "_returnType=json";


                addr = addr + serviceKey + parameter;

                URL url = new URL(addr);
                InputStream in = url.openStream(); 
                CachedOutputStream bos = new CachedOutputStream();
                IOUtils.copy(in, bos);
                in.close();
                bos.close();

                str_response = bos.getOut().toString();
                //System.out.println(str_response);
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", "200");
                map.put("data", str_response);
                return map;
            }
            else if(req_type.equals("find_station")) //web outdoor
            { 
                    
                    String lat = request.getParameter("lat");                   
                    String lon = request.getParameter("lon");
                    
                    System.out.println("lat: " +lat);
                    System.out.println("lon: " +lon);            
                
				    Double wgs84x = Double.parseDouble(lon);
				    Double wgs84y = Double.parseDouble(lat);
                
				    
				    String[] proj_info = {"+proj=tmerc", "+lat_0=38","+lon_0=127.5", "+k=0.9996", "+x_0=1000000", "+y_0=2000000","+ellps=GRS80", "+units=m","+no_defs"};
				    Projection proj = ProjectionFactory.fromPROJ4Specification(proj_info);
				    Point2D.Double umtk = new Point2D.Double(wgs84x, wgs84y);
				    umtk = proj.transform(umtk, new Point2D.Double());
                
				    String s_tmX = String.valueOf(umtk.x);
				    String s_tmY = String.valueOf(umtk.y);
				    System.out.println("tmx : "+s_tmX);
				    System.out.println("tmy : "+s_tmY);
                
                    // String s_tmX = "967008.9473912376";
                    // String s_tmY = "1939010.4010677664"; //복정동 고정 좌표
                    String str_response = "";
                    String addr = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList"+"?ServiceKey=";
                    String serviceKey = "U6jMUnCIHDYkWAQmMXEcpEuHxl1N%2F5Tk0T9CTl0CVyDYCydnOwfN1wG0R7zgZunR6P6DTBdG6b6cihUuicC%2FPQ%3D%3D";
                    String parameter = "";
                  
                    //serviceKey = URLEncoder.encode(serviceKey, "UTF-8");

                     //parameter setting
                     parameter = parameter + "&" + "tmX="+ s_tmX;
                     parameter = parameter + "&" + "tmY="+ s_tmY;
                     parameter = parameter + "&" + "ver=1.0";               
                     parameter = parameter + "&" + "_returnType=json";

                    addr = addr + serviceKey + parameter;

                    URL url = new URL(addr);
                    InputStream in = url.openStream(); 
                    CachedOutputStream bos = new CachedOutputStream();
                    IOUtils.copy(in, bos);
                    in.close();
                    bos.close();
                    
                    str_response = bos.getOut().toString();
                    //System.out.println(str_response);

                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result", true);
                    map.put("data", str_response);
                    return map;
            } 
            else if(req_type.equals("outdoor_web")) //web outdoor
            {
                String addr = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty"+"?ServiceKey=";
                 String serviceKey = "U6jMUnCIHDYkWAQmMXEcpEuHxl1N%2F5Tk0T9CTl0CVyDYCydnOwfN1wG0R7zgZunR6P6DTBdG6b6cihUuicC%2FPQ%3D%3D";
                String parameter = "";
                String str_response = "";
                String station_name = "";
                String data_time = "";
               
                //station_name=request.getParameter("station");
                System.out.println("outdoor_web called");
                
                //PrintWriter out = response.getWriter();
                
                parameter = parameter + "&" + "numOfRows=1";
                parameter = parameter + "&" + "pageNo=1";
                parameter = parameter + "&" + "ver=1.3";
                parameter = parameter + "&" + "dataTerm=DAILY";
                parameter = parameter + "&" + "dataTime="+data_time;
                parameter = parameter + "&" + "stationName="+/*station_name*/request.getParameter("station");;
                parameter = parameter + "&" + "_returnType=json";
                
                addr = addr + serviceKey + parameter;
                URL url = new URL(addr); //URL로 부터 자바로 데이터 읽어오도록 URL 객체로 스트림 열기
                System.out.println(addr);            
               
                InputStream in = url.openStream(); 
                CachedOutputStream bos = new CachedOutputStream();
                IOUtils.copy(in, bos);
                in.close();
                bos.close();

                str_response = bos.getOut().toString();
                //System.out.println(str_response);
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", true);
                map.put("station_name", station_name);
                map.put("data", str_response);
                return map;               
         
            }
            else if(req_type.equals("outdoor_mobile"))
            {
                String station_name = request.getParameter("station");
                //System.out.println(station_name);
                String str_response = "";
                String addr = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty"+"?ServiceKey=";
                String serviceKey = "U6jMUnCIHDYkWAQmMXEcpEuHxl1N%2F5Tk0T9CTl0CVyDYCydnOwfN1wG0R7zgZunR6P6DTBdG6b6cihUuicC%2FPQ%3D%3D";
                String parameter = "";

                //serviceKey = URLEncoder.encode(serviceKey, "UTF-8");

                 //parameter setting
                parameter = parameter + "&" + "numOfRows=1";
                parameter = parameter + "&" + "pageNo=1";
                parameter = parameter + "&" + "ver=1.3";
                parameter = parameter + "&" + "dataTerm=DAILY";
                parameter = parameter + "&" + "stationName="+station_name;
                parameter = parameter + "&" + "_returnType=json";


                addr = addr + serviceKey + parameter;

                URL url = new URL(addr);
                InputStream in = url.openStream(); 
                CachedOutputStream bos = new CachedOutputStream();
                IOUtils.copy(in, bos);
                in.close();
                bos.close();

                str_response = bos.getOut().toString();
                //System.out.println(str_response);
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", true);
                map.put("data", str_response);
                return map;
            }
            // else if(req_type.equals("analysis"))
            // {
            //     try{
            //             String u_id = (String)request.getParameter("user_id");
            //             List<VOSensorData> result = service.selectThisWeek(u_id);
            //             List<VOSensorData> result_last = service.selectLastWeek(u_id);
            //             if(result != null && result_last != null)
            //             {
            //                 if(!result.isEmpty() && !result_last.isEmpty())
            //                 {
            //                     System.out.println("Controller -- object is not empty! : " + String.valueOf(result.size()));
            //                     Map<String, Object> map = new HashMap<String, Object>();
            //                     map.put("result", true);
            //                     map.put("this", result);
            //                     map.put("last", result_last);
            //                     return map;
            //                 }
            //             }
            //             System.out.println("controller user id check : " + u_id);
            //             Map<String, Object> map = new HashMap<String, Object>();
            //             map.put("result", false);
            //             map.put("message", "has null or empty data");
            //             map.put("this", result);
            //             map.put("last", result_last);
            //             return map;
            //         }
            //         catch(Exception e)
            //         {
            //             e.printStackTrace();
            //             Map<String, Object> map = new HashMap<String, Object>();
            //             map.put("result", false);
            //             map.put("message", "Exception occurred");
            //             return map;
            //         }
            // }
            else
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", "500");
                return map;
            }
            
        }
        catch(Exception ee)
        {
            ee.printStackTrace();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("result", "500");
            return map;
        }
    }
}