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
    <!-- <style>
        div {height: 100%;}
    </style> -->
</head>

<body>

	
<h1>실내 온/습도 현황<br></h1>
    <!-- <h3>도메인: ${car_num}</h3> -->
    
<div class="charts-container cf"> 
    <div class="chart-container cf-row">
        <div class="chart-container cf-cell">
            <div class="chart" id="graph-1-container">
                <h2 class="title">TEMP - 최근 1시간</h2>
                <div class="chart-svg">
                    <svg class="chart-line" id="chart-1" viewBox="0 0 80 40">
                        <defs>
                            <clipPath id="clip" x="0" y="0" width="80" height="40" >
                                <rect id="clip-rect" x="-80" y="0" width="77" height="38.7"/>
                            </clipPath>

                            <linearGradient id="gradient-1">
                                <stop offset="0" stop-color="#954ce9" />
                                <stop offset="0.3" stop-color="#954ce9" />
                                <stop offset="0.6" stop-color="#24c1ed" />
                                <stop offset="1" stop-color="#24c1ed" />
                            </linearGradient>

                            <linearGradient id="gradient-2">
                                <stop offset="0" stop-color="#954ce9" />
                                <stop offset="0.3" stop-color="#954ce9" />
                                <stop offset="0.6" stop-color="#24c1ed" />
                                <stop offset="1" stop-color="#24c1ed" />
                            </linearGradient>


                        </defs>
                    </svg>
                    <h3 class="valueX">time (1 Hour)</h3>
                </div> <!-- chart svg -->

                <div class="chart-values">
                    <p class="h-value">°C</p>
                    <p class="percentage-value"></p>
                    <p class="total-gain" style="visibility:hidden;"></p>
                </div> <!-- chart-values -->
                <div class="triangle green"></div>
            </div> <!-- chart -->   
        </div> <!-- chart-container cell -->


        <div class="chart-container cf-cell">
            <div class="chart" id="graph-2-container">
                <h2 class="title">HUMI - 최근 1시간</h2>

                <div class="chart-svg">
                    <svg class="chart-line" id="chart-2" viewBox="0 0 80 40"></svg>
                    <h3 class="valueX">time (1 Hour)</h3>
                </div>

                <div class="chart-values">
                    <p class="percentage-value"></p>
                    <p class="total-gain" style="visibility:hidden;"></p>
                </div>

                <div class="triangle yellow"></div>
            </div>
        </div> <!-- chart-container cell -->
        
        

    </div><!-- chart-container row -->

    <div class="chart-container cf-row">
        <div class="chart-container cf-cell">
            <div class="chart circle" id="circle-1">
                <h2 class="title">TEMP - 현재</h2>
                <div class="chart-svg align-center">
                    <h2 class="circle-percentage"></h2>
                    <svg class="chart-circle" id="chart-6" width="50%" viewBox="0 0 100 100">
                        <path class="underlay" d="M5,50 A45,45,0 1 1 95,50 A45,45,0 1 1 5,50"/>
                    </svg>
                </div>
                <div class="triangle green"></div>
            </div>
        </div><!-- chart-container cell -->
        <div class="chart-container cf-cell">
            <div class="chart circle" id="circle-2">
                <h2 class="title">HUMI - 현재</h2>

                <div class="chart-svg align-center">
                    <h2 class="circle-percentage"></h2>
                    <p class="h-value">%</p>
                    <svg class="chart-circle" id="chart-7" width="50%" viewBox="0 0 100 100">
                         
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
    
    setTimeout(() => {
    
    //var num = ${car_num}
    var num = "<c:out value="${car_num}"/>";
    console.log(num+"car_num 테스트중 ajax");
  	
    $.ajax({
        type:"POST",
        url:"/DataLoad",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: "num=321 모 3131&type=withcarnum",
        datatype:"json",
        success: function(data) {
            console.log(data);
            
            if(data.data.length > 0)
            {
                var sensor_data = data.data;

                var current_data = sensor_data[sensor_data.length-1].data;
                var json_obj_current = JSON.parse(current_data);

                var curr_temp;
                var curr_humi;
               

                var json_obj_all = new Array();
                var chart_temp = new Array();
                var chart_humi = new Array();
              
                

                for(var idx=0; idx<sensor_data.length; idx++){
                    var json_obj = JSON.parse(sensor_data[idx].data);
                    for(key in json_obj.raw[1].data) 
                    {
                        if(key=="temp"){chart_temp.push(parseInt(json_obj.raw[1].data[key]))}

                        else if(key=="humi"){chart_humi.push(parseInt(json_obj.raw[1].data[key]))}
                    }
                }

                for(key in json_obj_current.raw[1].data) {
                    //alert('key:' + key + ' / ' + 'value:' + json[key]);
                    //console.log(json_obj_current.raw[0].data[key]);
                    //data_arr.push(json_obj_current.raw[0].data[key]);
                    if(key=="temp"){curr_temp = parseInt(json_obj_current.raw[1].data[key]);}
                    else if(key=="humi"){curr_humi = parseInt(json_obj_current.raw[1].data[key]);} //
                }
                //console.log(json_obj_current.raw[0].data["PM2.5"]);
                drawCircle('#chart-6',1,curr_temp,'#circle-1');
                drawCircle('#chart-7',2,curr_humi,'#circle-2');
                

                console.log(chart_temp);
                console.log(chart_humi);


                drawLineGraph('#chart-1', chart_temp, '#graph-1-container', 1);
                drawLineGraph('#chart-2', chart_humi, '#graph-2-container', 2);

            }
        },
        error: function(e) {
            console.log(e);
        }			
    }); 
    delay = 30000;
    loadData();
        
  }, delay)
}
    
$(window).on('load',function(){
    
    drawGrid('#chart-2');
    drawGrid('#chart-1');

    
    loadData();
});    
</script>
</body>

</html>