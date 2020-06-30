<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="ko">
    
<head>
    
    <meta name="viewport" content="initial-scale=1.0">
    <meta charset="utf-8">
    
    <style>
      /* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
      #map {
        height: 100%;
      }
      /* Optional: Makes the sample page fill the window. */
      html, body {
        height: 100%;
        margin: 0;
        padding: 0;
      }
    </style>
    
</head>
    
<body>
    <div id="map"></div>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>    
<script src="${jquery321Js}"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/snap.svg/0.3.0/snap.svg-min.js"></script>
    <script>       
        
        var map; 
            
        function findlocat(){
            $.ajax({
            type:"POST",
            url:"/DataLoad",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: "id=kind4u&type=withhour",
            datatype:"json",
            success: function(data){
                
                
            //sensor 데이터 가져오기
            var sensor_data = data.data;
            var current_data = sensor_data[sensor_data.length-1].data;
            var json_obj_current = JSON.parse(current_data);
            var curr_pm10;
                
            for(key in json_obj_current.raw[0].data) {
                    curr_pm10 = json_obj_current.raw[0].data["PM10"];
                }    
                
                           
            // lat, lon 데이터 가져오기       
            var location_data = data.data;               
            var json_obj_lat;
            var json_obj_lon;
 
            for(var idx=0; idx<location_data.length; idx++){
                    json_obj_lat = JSON.parse(location_data[idx].lat);
                    json_obj_lon = JSON.parse(location_data[idx].lon);                   
                }
                console.log(json_obj_lat+"위도출력되라");
                console.log(json_obj_lon+"경도출력되라");
                
                initMap(curr_pm10, json_obj_lat, json_obj_lon);
                     
               
           
            },
             error: function(e) {
                    console.log(e);
                }
            });
        } //findlocat 끝    
   
        
        function initMap(data, lat, lon){                              
       
          var locations = [
              ['명동', 37.563576, 126.983431],
              ['가로수길', 37.520300, 127.023008],
              ['광화문', 37.575268, 126.976896],
              ['남산', 37.550925, 126.990945],
              ['이태원', 37.540223, 126.994005],
              [data, lat,lon]
            ];
            
           //-----------------------------------------
            
            if(navigator.geolocation){ //gps를 지원하면
                navigator.geolocation.getCurrentPosition(function(position){               
                    
                    var latLng = new google.maps.LatLng({lat: parseFloat(position.coords.latitude), lng: parseFloat(position.coords.longitude)});
                    
                    map = new google.maps.Map(document.getElementById('map'), {
                          center: latLng,
                          zoom: 15
                    });
                    //var marker = new google.maps.Marker({position: latLng, map: map}); //현재 위치 기반 마커 띄우기
                    
                    var infowindow = new google.maps.InfoWindow();
                    
                    var marker2, i;
                    for(i=0; i<locations.length; i++){
                        console.log(i);
                        marker2 = new google.maps.Marker({
                            id:i,
                            position: new google.maps.LatLng(locations[i][1], locations[i][2]),
                            map: map
                        });
                        
                        google.maps.event.addListener(marker2, 'click', (function(marker2, i) {
                            return function() {
                              infowindow.setContent("미세먼지 농도: "+locations[i][0]+" ㎍/m³");
                              infowindow.open(map, marker2);
                            }
                          })(marker2, i));
                          if(marker2)
                          {
                            marker2.addListener('click', function() {
                              map.setZoom(15);
                              map.setCenter(this.getPosition());
                            });
                        }
                    }
          
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
           
        } //initmap 끝

       findlocat();
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDt41FkdHKSpT0hFuVWHV1419JCajyeQVU&callback=initMap"
    async defer></script>
    

    </body>
</html>

