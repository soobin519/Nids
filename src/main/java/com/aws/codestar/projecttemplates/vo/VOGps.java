package com.aws.codestar.projecttemplates.vo;

public class VOGps {
   
    private String lat;
    private String lon;
  
    public VOGps(String lat, String lon)    {
          this.lat =lat;
          this.lon =lon;
    }
    
    public String getLat(){ return this.lat; }
    public void setLat(int model) { this.lat = lat; }
    public String getLon() { return this.lon; }
    public void setLon(String num){ this.lon = lon; }
    
    
}