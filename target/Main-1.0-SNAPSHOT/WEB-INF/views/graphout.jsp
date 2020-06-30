<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<mvc:interceptors>
    <beans:bean id="webContentInterceptor" class="org.springframework.web.servlet.mvc.WebContentInterceptor">
        <beans:property name="cacheSeconds" value="0" />
        <beans:property name="useExpiresHeader" value="true" />
        <beans:property name="useCacheControlHeader" value="true" />
        <beans:property name="useCacheControlNoStore" value="true" />
    </beans:bean>
</mvc:interceptors>
<html lang="ko">
<head>
	<spring:url value="/resources/vendor/jquery/jquery-3.2.1.min.js" var="jquery321Js" />
	<spring:url value="/resources/css/graph.css" var="graphCss" />
    <spring:url value="/resources/js/graph.js" var="graphJs" />
	<link href="${graphCss}" rel="stylesheet">
</head>

<body>

	
<h1 id="p_title">실외 미세먼지 현황</h1>
    <div id="p_time">
        
    </div>
    <br>
    
<svg class="chart-line" id="chart-1" viewBox="0 0 80 40" style="width:0px; height:0px;">
    <defs>
        <clipPath id="clip" x="0" y="0" width="80" height="40" >
            <rect id="clip-rect" x="-80" y="0" width="77" height="38.7"/>
        </clipPath>

        <linearGradient id="gradient-1">
            <stop offset="0" stop-color="#00d5bd" />
            <stop offset="100" stop-color="#24c1ed" />
        </linearGradient>

        <linearGradient id="gradient-2">
            <stop offset="0" stop-color="#954ce9" />
            <stop offset="0.3" stop-color="#954ce9" />
            <stop offset="0.6" stop-color="#24c1ed" />
            <stop offset="1" stop-color="#24c1ed" />
        </linearGradient>

        <linearGradient id="gradient-3" x1="0%" y1="0%" x2="0%" y2="100%">>
            <stop offset="0" stop-color="rgba(0, 213, 189, 1)" stop-opacity="0.07"/>
            <stop offset="0.5" stop-color="rgba(0, 213, 189, 1)" stop-opacity="0.13"/>
            <stop offset="1" stop-color="rgba(0, 213, 189, 1)" stop-opacity="0"/>
        </linearGradient>

        <linearGradient id="gradient-4" x1="0%" y1="0%" x2="0%" y2="100%">>
            <stop offset="0" stop-color="rgba(149, 76, 233, 1)" stop-opacity="0.07"/>
            <stop offset="0.5" stop-color="rgba(149, 76, 233, 1)" stop-opacity="0.13"/>
            <stop offset="1" stop-color="rgba(149, 76, 233, 1)" stop-opacity="0"/>
        </linearGradient>
    </defs>
</svg>  
    
<div class="charts-container cf"> 
    <div class="chart-container cf-row">
        <div class="chart-container cf-cell">           
            <div class="chart circle" id="circle-1">
                <h2 class="title">PM10</h2>
                <div class="chart-svg align-center">
                    <h2 class="circle-percentage"></h2>
                    <svg class="chart-circle" id="chart-4" width="50%" viewBox="0 0 100 100">
                        <path class="underlay" d="M5,50 A45,45,0 1 1 95,50 A45,45,0 1 1 5,50"/>
                    </svg>
                </div>
                <div class="triangle green"></div>
            </div>
        </div><!-- chart-container cell -->
        <div class="chart-container cf-cell">
            <div class="chart circle" id="circle-2">
                <h2 class="title">PM2.5</h2>

                <div class="chart-svg align-center">
                    <h2 class="circle-percentage"></h2>
                    <svg class="chart-circle" id="chart-5" width="50%" viewBox="0 0 100 100">
                        <path class="underlay" d="M5,50 A45,45,0 1 1 95,50 A45,45,0 1 1 5,50"/>
                    </svg>
                </div>
                <div class="triangle yellow"></div>
            </div>
        </div> <!-- chart-container cell -->
    </div>	
</div>

<script src="${jquery321Js}"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/snap.svg/0.3.0/snap.svg-min.js"></script>
<script src="${graphJs}"></script>
<script>
    
function loadData(){
    
}
    
function getDustData(station){
    $.ajax({
        type:"POST",
        url:"/DataLoad",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: "type=outdoor&station="+station,
        datatype:"json",
        success: function(data) {
            //console.log(data.data);
            var json_obj_position = JSON.parse(data.data);
            console.log(json_obj_position);
            $('#p_time').text('');
            $('#p_time').append("측정시간 : " + json_obj_position.list[0].dataTime);
            var pm10 = Number(json_obj_position.list[0].pm10Value);
            var pm25 = Number(json_obj_position.list[0].pm25Value);
            drawCircle('#chart-4',1,pm10,'#circle-1');
            drawCircle('#chart-5',2,pm25,'#circle-2');
        },
        error: function(e) {
            console.log(e);
        }			
    }); 
}
    
function findStation(){
    // var tmX="967008.9473912376";
    // var tmY="1939010.4010677664";
    var ServiceKey="U6jMUnCIHDYkWAQmMXEcpEuHxl1N%2F5Tk0T9CTl0CVyDYCydnOwfN1wG0R7zgZunR6P6DTBdG6b6cihUuicC%2FPQ%3D%3D";
    var id = "<c:out value="${session_id}" />";
    
    $.ajax({
        type:"POST",
        url:"/UserUtil",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: "type=station&user_id="+id,
        datatype:"json",
        success: function(data) {
            console.log(data.data);
            var json_obj_position = JSON.parse(data.data);
            console.log(json_obj_position);
            getDustData(json_obj_position.list[0].stationName);
            $('#p_title').text('');
            $('#p_title').append("실외 미세먼지 현황 - " + json_obj_position.list[0].stationName + " 측정소");
        },
        error: function(e) {
            console.log(e);
        }			
    }); 
}
    
$(window).on('load',function(){
    findStation();
    loadData();
});    
</script>
</body>

</html>