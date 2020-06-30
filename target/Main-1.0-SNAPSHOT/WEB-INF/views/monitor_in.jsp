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
       
    <div id="indoor">
        <br>
        <br>
        <h1>실내 미세먼지 현황<br></h1>
        <h3>차량번호: ${car_num}</h3>
        <div id="p_temp"></div>
        <div id="p_humi"></div>
        <br>
        
        <div align="center">      
            <div style="width:350px; height:250px;">
                <img id="i_state" src="${ pageContext.request.contextPath }/resources/images/blank.png" align="left" width="150" height="150">
                <br>
                <br>
                <h2 id="p_state1" align="right" style="padding-right:60px;">측정중</h2> <!--미세먼지 상태 표시-->
                <h2 id="h-value1" align="right" style="padding-right:60px;">0.0 ㎍/m³</h2><!--미세먼지 수치 표시-->
            </div>    
        </div>

    </div>
    
    
    <script src="${jquery321Js}"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/snap.svg/0.3.0/snap.svg-min.js"></script>
    
    
    
<script>
function loadData(delay){

    setTimeout(() => {
    
    var num = "<c:out value="${car_num}"/>";
    console.log(num+"car_num 테스트중 ajax");
  	
    $.ajax({
        type:"POST",
        url:"/DataLoad",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: "num="+num+"&type=withcarnum",
        datatype:"json",
        success: function(data) {
            console.log(data);
            
            if(data.data.length > 0)
            {
                var sensor_data = data.data;

                var current_data = sensor_data[sensor_data.length-1].data;
                var json_obj_current = JSON.parse(current_data);

                var curr_pm10;
                var curr_temp;
                var curr_humi;
              

                for(key in json_obj_current.raw[0].data) {
                    if(key=="PM10"){curr_pm10 = parseInt(json_obj_current.raw[0].data[key]);}
                }
               
                if (curr_pm10 > 150.0) {
                $('#p_state1').text('매우나쁨');
                document.getElementById("indoor").style.backgroundColor = "#F09494";
                $("#i_state").attr("src", "${ pageContext.request.contextPath }/resources/images/verybad.png");
            } else if (curr_pm10 > 80.0) {
                $('#p_state1').text('나쁨');      
                document.getElementById("indoor").style.backgroundColor = "#FFB2AF";
                $("#i_state").attr("src", "${ pageContext.request.contextPath }/resources/images/bad.png");
            } else if (curr_pm10 > 30.0) {
                $('#p_state1').text('보통');      
                document.getElementById("indoor").style.backgroundColor = "#BEF5BE";
                $("#i_state").attr("src", "${ pageContext.request.contextPath }/resources/images/good.png");
            } else {
                $('#p_state1').text('좋음');               
                document.getElementById("indoor").style.backgroundColor = "#A5D8FA";
                $("#i_state").attr("src", "${ pageContext.request.contextPath }/resources/images/verygood.png");
            }
                
                for(key in json_obj_current.raw[1].data) {
                    if(key=="temp"){curr_temp = parseInt(json_obj_current.raw[1].data[key]);}
                    else if(key=="humi"){curr_humi = parseInt(json_obj_current.raw[1].data[key]);} //
                }
                
                $('#h-value1').text(" ");
                $('#h-value1').append(curr_pm10+" ㎍/m³");
                $('#p_temp').text(" ");
                $('#p_temp').append("온도: "+curr_temp+" °C");
                $('#p_humi').text(" ");
                $('#p_humi').append("습도: "+curr_humi+" %");


            }
        },
        error: function(e) {
            console.log(e);
        }			
    }); 
   
    loadData(30000);
        
  }, delay)
}
             
    
$(window).on('load',function(){
    loadData(0);
});    

</script> 
</body>
</html>