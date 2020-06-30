package com.aws.codestar.projecttemplates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Locale;
 
import javax.inject.Inject;

import java.util.Map;
import java.util.HashMap;
import javax.inject.Inject;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Basic Spring MVC controller that handles all GET requests.
 */
@Controller
@RequestMapping("/JusoPopup")
public class JusoPopupController{

    private final String siteName;


    public JusoPopupController(final String siteName) {
        this.siteName = siteName;
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView doPopup() {
		
        ModelAndView mav = new ModelAndView("jusoPopup");
        mav.addObject("siteName", this.siteName);
        return mav;
    }
    /*
    @RequestMapping(produces="application/json;charset=UTF-8",method=RequestMethod.POST)
    public @ResponseBody Map<String, Object> postServer(HttpServletRequest request, HttpServletResponse response) {
        String inputYn = request.getParameter("inputYn");
        String roadFullAddr = request.getParameter("roadFullAddr");
        String roadAddrPart1 = request.getParameter("roadAddrPart1");
        String roadAddrPart2 = request.getParameter("roadAddrPart2");
        String engAddr = request.getParameter("engAddr");
        String jibunAddr = request.getParameter("jibunAddr");
        String zipNo = request.getParameter("zipNo");
        String addrDetail = request.getParameter("addrDetail");
        String admCd = request.getParameter("admCd");
        String rnMgtSn = request.getParameter("rnMgtSn");
        String bdMgtSn = request.getParameter("bdMgtSn");
        String detBdNmList = request.getParameter("detBdNmList");
        System.out.println(inputYn);
        System.out.println(roadFullAddr);
        System.out.println(roadAddrPart1);
        System.out.println(roadAddrPart2);
        System.out.println(engAddr);
        System.out.println(jibunAddr);
        System.out.println(zipNo);
        System.out.println(addrDetail);
        System.out.println(admCd);
        System.out.println(rnMgtSn);
        System.out.println(bdMgtSn);
        System.out.println(detBdNmList);
        
        Map<String, Object> map = new HashMap<String, Object>();
        //클라이언트 페이지로 부터 Httpclient로 받은 parameter값
        map.put("result", "200");
        return map;
    }*/
}