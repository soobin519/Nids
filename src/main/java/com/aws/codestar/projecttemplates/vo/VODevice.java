package com.aws.codestar.projecttemplates.vo;


public class VODevice {
    private String id;
    private int type;
    private double lat;
    private double lon;
    private String userid;
    private String desc;
    private int baud;
    
    public VODevice(){}
    
    public void setBaud(int baud){this.baud=baud;}
    public void setId(String id){this.id=id;}
    public void setType(int type){this.type=type;}
    public void setLat(double lat){this.lat=lat;}
    public void setLon(double lon){this.lon=lon;}
    public void setUserid(String userid){this.userid=userid;}
    public void setDesc(String desc){this.desc = desc;}
    
    public String getId(){return this.id;}
    public int getType(){return this.type;}
    public double getLat(){return this.lat;}
    public double getLon(){return this.lon;}
    public String getUserid(){return this.userid;}
    public String getDesc(){return this.desc;}
    public int getBaud(){return this.baud;}
}