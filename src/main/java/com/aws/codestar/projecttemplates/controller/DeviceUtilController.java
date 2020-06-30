package com.aws.codestar.projecttemplates.controller;
import  com.aws.codestar.projecttemplates.vo.VOUser;
import  com.aws.codestar.projecttemplates.vo.VODevice;
import  com.aws.codestar.projecttemplates.services.ServiceDevice;
import  com.aws.codestar.projecttemplates.services.ServiceUser;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

 
import javax.inject.Inject;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Controller
public class DeviceUtilController{

    private final String siteName;
    
    @Inject
    private ServiceDevice service_device;
    @Inject
    private ServiceUser service_user;


    public DeviceUtilController(final String siteName) {
        this.siteName = siteName;
    }
    
    private ArrayList<Double> getLatLon(String addr_detail){
        ArrayList<Double> latlon = new ArrayList<Double>();
        String clientId = "zpv4pcqhji";//application client id value
        String clientSecret = "dKe84R4o1rK2983P6VDMMnX3ANO7WVwoQYU0rK2C";//application client secret value
        try {
            String addr = URLEncoder.encode(addr_detail, "UTF-8");
            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr; //json
            
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // success
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // failed
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            
            
            //System.out.println(response.toString());
            String json = response.toString();
            ObjectMapper mapper = new ObjectMapper();
            Map<Object, Object> map = new HashMap<Object, Object>();
            map = mapper.readValue(json, new TypeReference<Map<Object, Object>>(){});
            ArrayList<Object> arr_info = (ArrayList<Object>)map.get("addresses");
            Map<Object, Object> address_info = (Map<Object, Object>)(arr_info.get(0));
            String lat = (String)address_info.get("y");
            String lon = (String)address_info.get("x");
            //System.out.println("lat : " + lat +" lon : " + lon);
            latlon.add(Double.parseDouble(lat));
            latlon.add(Double.parseDouble(lon));
            
        } catch (Exception e) {
            System.out.println(e);
            latlon.clear();
        }
        return latlon;
    }
    
    @RequestMapping(value="/DeviceUtil",produces="application/json;charset=UTF-8",method=RequestMethod.POST)
    public @ResponseBody Map<String, Object> postServer(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String req_comm = request.getParameter("command");
        System.out.println("void method - " + req_comm);
        try
        {
            // if(req_comm.equals("add"))
            // {
            //     String auth = request.getParameter("auth");
            //     String s_id = request.getParameter("id");
            //     String s_type = request.getParameter("type");
            //     String s_baud = request.getParameter("baud");
            //     String s_desc = request.getParameter("desc");
            //     System.out.println("auth : " + auth);
                
            //     VOUser user = service_user.selectUserWithAuth(auth);
                
            //     if(user != null){
            //         String full_addr = user.getAddr();
            //         String addr = full_addr.split("&")[1];
            //         System.out.println("addr : " + addr);
            //         ArrayList<Double> lat_lon = getLatLon(addr);
                    
            //         VODevice device = new VODevice();
            //         device.setId(s_id);
            //         device.setUserid(user.getId());
            //         device.setType(Integer.parseInt(s_type));
            //         device.setDesc(s_desc);
            //         device.setLat(lat_lon.get(0));
            //         device.setLon(lat_lon.get(1));
            //         device.setBaud(Integer.parseInt(s_baud));
                    
            //         int result = service_device.insertDevice(device);
            //         if(result == 1)
            //         {
            //             Map<String, Object> map = new HashMap<String, Object>();
            //             map.put("result", true);
            //             return map;
            //         }
            //         else
            //         {
            //             Map<String, Object> map = new HashMap<String, Object>();
            //             map.put("result", false);
            //             map.put("reason", "db insert failed");
            //             return map;
            //         }
            //     }
            //     else{
            //         Map<String, Object> map = new HashMap<String, Object>();
            //         map.put("result", false);
            //         map.put("reason", "cannot find user");
            //         return map;
            //     }
                
            // }
           if(req_comm.equals("edit"))
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", false);
                map.put("reason", "unimplement method");
                return map;
            }
            else if(req_comm.equals("delete"))
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", false);
                map.put("reason", "unimplement method");
                return map;
            }
            else if(req_comm.equals("list"))
            {
                String auth = request.getParameter("auth");
                
                VOUser user = service_user.selectUserWithAuth(auth);
                
                if(user != null){
                    
                    List<VODevice> sensor_list = service_device.selectDeviceWithID(user.getId());
                    
                    if(sensor_list.size() > 0){
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("result", true);
                        map.put("data", sensor_list);
                        return map;
                    }
                    else
                    {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("result", false);
                        map.put("reason", "list size 0");
                        return map;
                    }
                    
                }
                else{
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result", false);
                    map.put("reason", "cannot find user");
                    return map;
                }
                    
            }
            else
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", false);
                map.put("reason", "unkwon command error : " + req_comm);
                return map;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("result", false);
            map.put("reason", "exception : " + e.getMessage());
            return map;
        }
    }
    
}