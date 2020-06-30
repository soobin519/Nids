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
        <spring:url value="/resources/css/analysistable.css" var="tableCss" />
        <spring:url value="/resources/js/graph.js" var="graphJs" />
        <link href="${graphCss}" rel="stylesheet">
        <link href="${tableCss}" rel="stylesheet">
    </head>

    <body>
        <h1 style="font-size: 30px;font-weight: bold;color: #ffffff;">실내 미세먼지 분석 보고서<br></h1>
        <!-- HTML -->
        <div id="chartdiv" style="width:900px; height:700px; margin: 0 auto;"></div>
        <div>
            <table class="container">
                <thead>
                    <tr>
                        <th style="width:15%"><h1>항목</h1></th>
                        <th style="width:15%"><h1>적정기준치</h1></th>
                        <th style="width:15%"><h1>지난주 평균</h1></th>
                        <th style="width:15%"><h1>이번주 평균</h1></th>
                        <th><h1>분석내용</h1></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>미세먼지(PM10)</td>
                        <td>100(㎍/㎥)</td>
                        <td>37(㎍/㎥)</td>
                        <td>12(㎍/㎥)</td>
                        <td style="text-align:left; padding-left:1.5%; padding-right:1.5%;">지난주보다 약 <span class="highlightedgreen">15(㎍/㎥)감소</span>하였고, <span class="highlightedgreen">보통</span> 수준에서 <span class="highlightedblue">좋음</span> 수준으로 미세먼지(PM10) 환경이 개선되었습니다. 현재 실내 환경을 유지해주세요.</td>
                    </tr>
                    <tr>
                        <td>초미세먼지(PM2.5)</td>
                        <td>35(㎍/㎥)</td>
                        <td>21(㎍/㎥)</td>
                        <td>8(㎍/㎥)</td>
                        <td style="text-align:left; padding-left:1.5%; padding-right:1.5%;">지난주보다 약 <span class="highlightedgreen">13(㎍/㎥)감소</span>하였고, <span class="highlightedgreen">보통</span> 수준에서 <span class="highlightedblue">좋음</span> 수준으로 초미세먼지(PM2.5) 환경이 개선되었습니다. 현재 실내 환경을 유지해주세요.</td>
                    </tr>
                    <tr>
                        <td>초미세먼지(PM1.0)</td>
                        <td>35(㎍/㎥)</td>
                        <td>19(㎍/㎥)</td>
                        <td>5(㎍/㎥)</td>
                        <td style="text-align:left; padding-left:1.5%; padding-right:1.5%;">지난주보다 약 <span class="highlightedgreen">14(㎍/㎥)감소</span>하였고, <span class="highlightedgreen">보통</span> 수준에서 <span class="highlightedblue">좋음</span> 수준으로 초미세먼지(PM1.0) 환경이 개선되었습니다. 현재 실내 환경을 유지해주세요.</td>
                    </tr>
                    <tr>
                        <td>유기화합물(VOC)</td>
                        <td>400(㎍/㎥)</td>
                        <td>80(㎍/㎥)</td>
                        <td>90(㎍/㎥)</td>
                        <td style="text-align:left; padding-left:1.5%; padding-right:1.5%;">지난주보다 약 <span class="highlightedorange">10(㎍/㎥)증가</span>하였고, <span class="highlightedgreen">보통</span> 수준에서 <span class="highlightedgreen">보통</span> 수준으로 유기화합물(VOC) 환경이 유지중입니다. 하지만 수치가 증가중이므로 관리에 유의해주세요.</td>
                    </tr>
                    <tr>
                        <td>이산화탄소(CO2)</td>
                        <td>1000(ppm)</td>
                        <td>50(ppm)</td>
                        <td>800(ppm)</td>
                        <td style="text-align:left; padding-left:1.5%; padding-right:1.5%;">지난주보다 약 <span class="highlightedorange">750(ppm)증가</span>하였고, <span class="highlightedblue">좋음</span> 수준에서 <span class="highlightedred">매우나쁨</span> 수준으로 이산화탄소(CO2) 환경이 악화되었습니다. 적절한 환기 등을 통한 실내 공기질 개선이 필요합니다.</td>
                    </tr>
                    <tr>
                        <td>오존(O₃)</td>
                        <td>0.06(ppm)</td>
                        <td>0.01(ppm)</td>
                        <td>0.01(ppm)</td>
                        <td style="text-align:left; padding-left:1.5%; padding-right:1.5%;">지난주와 <span class="highlightedyellow">동일한 상태</span>를 유지중이고, <span class="highlightedblue">좋음</span> 수준에서 <span class="highlightedblue">좋음</span> 수준으로 오존(O₃) 환경이 유지중입니다. 현재 실내 환경을 유지해주세요.</td>
                    </tr>
                    <tr>
                        <td>이산화질소(NO₂)</td>
                        <td>0.05(ppm)</td>
                        <td>0.01(ppm)</td>
                        <td>0.01(ppm)</td>
                        <td style="text-align:left; padding-left:1.5%; padding-right:1.5%;">지난주와 <span class="highlightedyellow">동일한 상태</span>를 유지중이고, <span class="highlightedblue">좋음</span> 수준에서 <span class="highlightedblue">좋음</span> 수준으로 이산화질소(NO₂) 환경이 유지중입니다. 현재 실내 환경을 유지해주세요.</td>
                    </tr>
                    <tr>
                        <td>일산화탄소(CO)</td>
                        <td>10(ppm)</td>
                        <td>1(ppm)</td>
                        <td>1(ppm)</td>
                        <td style="text-align:left; padding-left:1.5%; padding-right:1.5%;">지난주와 <span class="highlightedyellow">동일한 상태</span>를 유지중이고, <span class="highlightedblue">좋음</span> 수준에서 <span class="highlightedblue">좋음</span> 수준으로 일산화탄소(CO) 환경이 유지중입니다. 현재 실내 환경을 유지해주세요.</td>
                    </tr>
                </tbody>
            </table>  
        </div>
        
        <script src="https://www.amcharts.com/lib/4/core.js"></script>
        <script src="https://www.amcharts.com/lib/4/charts.js"></script>
        <script src="https://www.amcharts.com/lib/4/themes/animated.js"></script>
        <script src="${jquery321Js}"></script>
        <script>
            
function loadData(){
    
    var id = "<c:out value="${session_id}" />";
  	
    $.ajax({
        type:"POST",
        url:"/DataLoad",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        data: "user_id="+id+"&type=analysis",
        datatype:"json",
        success: function(data) {
            console.log(data);
        },
        error: function(e) {
            console.log(e);
        }			
    });
}
            

$(window).on('load',function(){

     // Themes begin
    am4core.useTheme(am4themes_animated);
    // Themes end

    /* Create chart instance */
    var chart = am4core.create("chartdiv", am4charts.RadarChart);

    /* Add data */
    chart.data = [{
      "items": "미세먼지(PM10)",
      "last": 37,
      "this": 12,
    }, {
      "items": "초미세먼지(PM2.5)",
      "last": 60,
       "this": 23
    }, {
      "items": "초미세먼지(PM1.0)",
      "last": 54,
       "this": 15
    }, {
      "items": "유기화합물(VOC)",
      "last": 20,
       "this": 23
    }, {
      "items": "이산화탄소(CO2)",
      "last": 5,
       "this": 80
    }, {
      "items": "오존(O₃)",
      "last": 17,
       "this": 17
    }, {
      "items": "이산화질소(NO₂)",
      "last": 20,
       "this": 20
    }, {
      "items": "일산화탄소(CO)",
      "last": 10,
       "this": 10
    }];

    /* Create axes */
    var categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis());
    categoryAxis.dataFields.category = "items";
    categoryAxis.renderer.labels.template.fill = am4core.color("#ffaaaa");
    console.log(categoryAxis.renderer.labels);
    console.log(categoryAxis.renderer);

    var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
    valueAxis.renderer.axisFills.template.fill = chart.colors.getIndex(3);
    valueAxis.renderer.axisFills.template.fillOpacity = 0.0;
    valueAxis.renderer.labels.template.fill = am4core.color("#aaaaaa");
    valueAxis.renderer.grid.template.stroke = am4core.color("#ffffff");
    valueAxis.renderer.gridType = "polygons";
    valueAxis.renderer.minGridDistance = 50;
    valueAxis.min = 0;
    valueAxis.max = 90;

    /* Create and configure series */
    var series = chart.series.push(new am4charts.RadarSeries());
    series.dataFields.valueY = "last";
    series.dataFields.categoryX = "items";
    series.name = "Air Quality Last Week";
    series.stroke = am4core.color("#662277"); 
    series.fill = am4core.color("#662277");
    series.strokeWidth = 3;
    series.fillOpacity = 0.4;
    
    var series2 = chart.series.push(new am4charts.RadarSeries());
    series2.dataFields.valueY = "this";
    series2.dataFields.categoryX = "items";
    series2.name = "Air Quality This Week";
    series2.stroke = am4core.color("#5dd55d"); 
    series2.fill = am4core.color("#5dd55d");
    series2.strokeWidth = 3;
    series2.fillOpacity = 0.4;
    loadData();
});  
        </script>
    </body>    
</html>