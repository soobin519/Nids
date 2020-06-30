package com.aws.codestar.projecttemplates.controller;
import  com.aws.codestar.projecttemplates.vo.VOUser;
import  com.aws.codestar.projecttemplates.services.ServiceUser;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

 
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
import org.apache.ibatis.jdbc.Null;

import com.jhlabs.map.proj.*;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;

import com.jhlabs.map.proj.Projection;
import com.jhlabs.map.proj.ProjectionFactory;

/**
 * Basic Spring MVC controller that handles all GET requests.
 */
@Controller
public class UserUtilController{

    private final String siteName;
    
    @Inject
    private ServiceUser service;


    public UserUtilController(final String siteName) {
        this.siteName = siteName;
    }
    
    @RequestMapping(value="/Echo",produces="application/json;charset=UTF-8",method=RequestMethod.POST)
    public @ResponseBody Map<String, Object> echoServer(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("result", true);
        map.put("echo", request.getParameter("echo"));
        return map;
    }
    
    
    @RequestMapping(value="/UserLogout",produces="text/html;charset=UTF-8",method=RequestMethod.GET)
    public ModelAndView doPost2(HttpServletRequest request, HttpServletResponse response, HttpSession session)
    {
        session.invalidate();
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("siteName", this.siteName);
        return mav;
    }
    
    @RequestMapping(value="/UserLogin",produces="text/html;charset=UTF-8",method=RequestMethod.POST)
    public ModelAndView doPost(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        
        String req_type = request.getParameter("type");
        System.out.println("void method - " + req_type);
        try
        {
            String id = request.getParameter("id");
            String pw = request.getParameter("pw");
            
            
            VOUser user = service.selectUserWithID(id);
            if(user != null){

                if(user.getPw().equals(pw))
                {
                    session.setAttribute("user_id", user.getId());
                    ModelAndView mav = new ModelAndView("redirect:/Menu");
                    return mav;
                }
                else
                {
                    
                    ModelAndView mav = new ModelAndView("index");
                    mav.addObject("siteName", this.siteName);
                    mav.addObject("message", "E001");
                    return mav;
                }
            }
            else
            {
                //no-id
                ModelAndView mav = new ModelAndView("index");
                mav.addObject("siteName", this.siteName);
                mav.addObject("message", "E002");
                return mav;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            ModelAndView mav = new ModelAndView("index");
            mav.addObject("siteName", this.siteName);
            mav.addObject("message", "E003");
            return mav;
        }
    }

    @RequestMapping(value="/UserUtil",produces="application/json;charset=UTF-8",method=RequestMethod.POST)
    public @ResponseBody Map<String, Object> postServer(HttpServletRequest request, HttpServletResponse response) {
        try
        {
            request.setCharacterEncoding("UTF-8");
            String req_type = request.getParameter("type");
            System.out.println("map method - " + req_type);
            
            if(req_type.equals("Register"))    { //모바일 회원가입
                Point2D.Double wgs84 = null;
                
                String id = request.getParameter("id");
                String pw = request.getParameter("pw")==null?null:request.getParameter("pw").isEmpty()?null:request.getParameter("pw");
                String name = request.getParameter("name")==null?null:request.getParameter("name").isEmpty()?null:request.getParameter("name");
                //String age = request.getParameter("age")==null?null:request.getParameter("age").isEmpty()?null:request.getParameter("age");
                String s_gender = request.getParameter("gender");
                String platform = request.getParameter("platform");
                String bd = request.getParameter("bd")==null?null:request.getParameter("bd").isEmpty()?null:request.getParameter("bd");
                String email = request.getParameter("email")==null?null:request.getParameter("email").isEmpty()?null:request.getParameter("email");
                String hp = request.getParameter("hp")==null?null:request.getParameter("hp").isEmpty()?null:request.getParameter("hp");
                

                System.out.println(id);
                int gender = Integer.parseInt(s_gender);

                VOUser user = new VOUser(id, pw, name, gender, platform, bd, email, hp);

                int result = service.registerUser(user);
                if(result == 1){ //insert success
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
            else if(req_type.equals("signin")) //smartphone login
            {
                
                String id = request.getParameter("id");
                String pw = request.getParameter("pw");

                VOUser user = service.selectUserWithID(id);
                if(user != null){

                    if(user.getPw().equals(pw))
                    {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("result", true);
                        map.put("user_info", user);
                        map.put("message", "login success");
                        return map;
                    }
                    else
                    {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("result", false);
                        map.put("message", "wrong password");
                        return map;
                    }
                }
                else
                {
                    //no-id
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result", false);
                    map.put("message", "not exist id");
                    return map;
                }
            }
            else if(req_type.equals("getUser")) //DB에 등록된 user 정보 가져오기
            {         
                String id = request.getParameter("id");
              
                VOUser user = service.selectUserWithID(id);
                
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("result", true);
                        map.put("user_info", user);
                        map.put("message", "success");
                        return map;
            }
            
            else if(req_type.equals("edit"))
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", "200");
                return map;
            }
            else if(req_type.equals("delete"))
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", "200");
                return map;
            }
            else if(req_type.equals("check_exist"))
            {
                String id = request.getParameter("id");
                System.out.println(id);
                
                VOUser user = service.selectUserWithID(id);
                if(user != null){
                    System.out.println("search result - not null : " + user.getId());
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
            
            else if(req_type.equals("auth"))
            {
                String auth_code = request.getParameter("auth");
                System.out.println("authcode : " + auth_code);
                
                VOUser user = service.selectUserWithAuth(auth_code);
                if(user != null){
                    
                    if(user.getAuth().equals(auth_code))
                    {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("result", "200");
                        map.put("auth", true);
                        return map;
                    }
                    else
                    {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("result", "200");
                        map.put("auth", false);
                        return map;
                    }
                }
                else{
                    System.out.println("search result - null ");
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result", "200");
                    map.put("auth", false);
                    return map;
                }
                
            }
         
            else if(req_type.equals("stationgps"))    {
                Double wgs84x = Double.parseDouble(request.getParameter("lat"));
                Double wgs84y = Double.parseDouble(request.getParameter("lon"));
                
                String[] proj_info = {"+proj=tmerc", "+lat_0=38","+lon_0=127.5", "+k=0.9996", "+x_0=1000000", "+y_0=2000000","+ellps=GRS80", "+units=m","+no_defs"};
				Projection proj = ProjectionFactory.fromPROJ4Specification(proj_info);
				Point2D.Double umtk = new Point2D.Double(wgs84x, wgs84y);
				umtk = proj.transform(umtk, new Point2D.Double());
                
                String s_tmX = String.valueOf(umtk.x);
                String s_tmY = String.valueOf(umtk.y);
                System.out.println("tmx : "+s_tmX);
                System.out.println("tmy : "+s_tmY);
                String str_response = "";
                String addr = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList"+"?ServiceKey=";
                String serviceKey = "H76ogs6ia5bDiECM4RWWdLt%2FCfDZ4ysG8v87ZKsCDwhP4cNDdZ5ZqfgppgOEjGk6A1K5QCZEkmQx8ae7%2BXCMEA%3D%3D";
                //String serviceKey = "U6jMUnCIHDYkWAQmMXEcpEuHxl1N%2F5Tk0T9CTl0CVyDYCydnOwfN1wG0R7zgZunR6P6DTBdG6b6cihUuicC%2FPQ%3D%3D";// - 만료되서 임시키 만들어둠
                String parameter = "";
                
                parameter = parameter + "&" + "tmX="+s_tmX;
                parameter = parameter + "&" + "tmY="+s_tmY;
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
            else if(req_type.equals("position"))
            {
                String admCd = request.getParameter("admCd");
                String rnMgtSn = request.getParameter("rnMgtSn");
                String udrtYn = request.getParameter("udrtYn");
                String buldMnnm = request.getParameter("buldMnnm");
                String buldSlno = request.getParameter("buldSlno");
                
                System.out.println(admCd);
                System.out.println(rnMgtSn);
                System.out.println(udrtYn);
                System.out.println(buldMnnm);
                System.out.println(buldSlno);
                
                String confmKey = "U01TX0FVVEgyMDE5MDEwMjA0MTUyMzEwODQxMDM=";
                
                URI uri = new URI("http://www.juso.go.kr/addrlink/addrCoordApi.do"); 
				uri = new URIBuilder(uri).addParameter("admCd", admCd)
										 .addParameter("rnMgtSn", rnMgtSn)
										 .addParameter("udrtYn", udrtYn)
										 .addParameter("buldMnnm", buldMnnm)
										 .addParameter("buldSlno", buldSlno)
                                         .addParameter("confmKey", confmKey)
                                         .addParameter("resultType", "json")
										 .build();
				
				HttpClient httpClient = HttpClientBuilder.create().build(); 
				
				HttpResponse position_response = httpClient.execute(new HttpPost(uri));
				
				HttpEntity entity = position_response.getEntity(); 
				String content = EntityUtils.toString(entity); 
				System.out.println(content);
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("result", true);
                map.put("data", content);
                return map;
            }
            else if(req_type.equals("modUser"))    { //모바일 회원 정보 수정
                String id = request.getParameter("id");
                String name = request.getParameter("name");
                String s_gender = request.getParameter("gender");
                String hp = request.getParameter("hp");
                String bd = request.getParameter("bd");
                String age = request.getParameter("age");
                String email = request.getParameter("email");
                
                int gender = Integer.parseInt(s_gender);
                
                VOUser voUser = new VOUser(id, name, gender, hp, bd, age, email);
                
                int result = service.modifyUser(voUser);
                
                if(result == 1)    {    // update success
                   Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result",true);
                    return map;
                }    else    {        // update failed
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result",false);
                    return map;
                }
            }
                    else if(req_type.equals("modPw"))    {
                String id = request.getParameter("id");
                String pw = request.getParameter("pw");
                
                VOUser voUser = new VOUser(id, pw);
                
                int result = service.modifyPassword(voUser);
                
                if(result == 1)    {    // update success
                   Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result",true);
                    return map;
                }    else    {        // update failed
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("result",false);
                    return map;
                }
            }
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