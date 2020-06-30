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

    
    <div id="outdoor">
        <br>
        <br>
       <h1 id="p_title">실외 미세먼지 현황</h1>
         <h2 id="p_station"></h2>
       <div id="p_time"></div>
        <br>
        <br>
     
    <div align="center">       
      <div style="width:350px; height:250px;">
        <img id="i_state" src="${ pageContext.request.contextPath }/resources/images/blank.png" align="left" width="150" height="150">
        <br>
        <br>
        <h2 id="p_state1" align="right" style="padding-right:60px;">측정중</h2> <!--미세먼지 상태 표시-->
        <h2 id="p_dust1" align="right" style="padding-right:60px;">0.0 ㎍/m³</h2> <!--미세먼지 수치 표시-->
      </div>
    </div>
              
    </div>
    
    <script src="${jquery321Js}"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/snap.svg/0.3.0/snap.svg-min.js"></script>
    
    
    
<script>

function getLocation(){
     //-----  1
    if(navigator.geolocation){ //gps를 지원하면
        navigator.geolocation.getCurrentPosition(function(position){
            var lat = position.coords.latitude;
            var lon = position.coords.longitude;
           
            $('#lat').html(position.coords.latitude);
            $('#lon').html(position.coords.longitude);         
        
            loadData(position.coords.latitude, position.coords.longitude);         
            }, function(error){
            console.error(error);
        }, {
            enableHighAccuracy: false,
            maximumAge: 0,
            timeout: Infinity
        });
        
    }else {
        alert('GPS를 지원하지 않습니다.');
    }   
}

function loadData(lat, lon ){
    
    var params = {};
    params.lat = lat;
    params.lon = lon;
    
    $.ajax({
                type:"POST",
                url:"/DataLoad",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                async: true,
                data: "type=find_station&lat=" +String(params.lat)+"&lon="+String(params.lon), //파라메터
                success: function(data){
                    var json_obj_position = JSON.parse(data.data);
                    console.log(json_obj_position);
                    
                    getDustData(json_obj_position.list[0].stationName);
                    
                    $('#p_station').text('');
                    $('#p_station').append(json_obj_position.list[0].stationName + " 측정소");        
                },
                error: function(e) {
                    console.log(e);
                }
            });        
}

    
function getDustData(station){              
    //   ----- 2
    $.ajax({
        type:"POST",
        url:"/DataLoad",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: "type=outdoor_web&station="+station,
        datatype:"json",
        success: function(data) {
           
            var json_obj_position = JSON.parse(data.data);
            console.log(json_obj_position);
            $('#p_time').text('');
            $('#p_time').append("측정시간 : " + json_obj_position.list[0].dataTime);     
          
            var pm10 = Number(json_obj_position.list[0].pm10Value);            
            
            if (pm10 > 150.0) {
                $('#p_state1').text('매우나쁨');
                document.getElementById("outdoor").style.backgroundColor = "#F09494";
                $("#i_state").attr("src", "${ pageContext.request.contextPath }/resources/images/verybad.png");
            } else if (pm10 > 80.0) {
                $('#p_state1').text('나쁨');      
                document.getElementById("outdoor").style.backgroundColor = "#FFB2AF";
                $("#i_state").attr("src", "${ pageContext.request.contextPath }/resources/images/bad.png");
            } else if (pm10 > 30.0) {
                $('#p_state1').text('보통');      
                document.getElementById("outdoor").style.backgroundColor = "#BEF5BE";
                $("#i_state").attr("src", "${ pageContext.request.contextPath }/resources/images/good.png");
            } else {
                $('#p_state1').text('좋음');               
                document.getElementById("outdoor").style.backgroundColor = "#A5D8FA";
                $("#i_state").attr("src", "${ pageContext.request.contextPath }/resources/images/verygood.png");

            }
            
            $('#p_dust1').text(" ");
            $('#p_dust1').append(pm10+" ㎍/m³");          
          
        },
        error: function(e) {
            console.log(e);
        }			
    });     
   
}
    
$(window).on('load',function(){
    getLocation(); 
});    
</script> 
</body>
</html>