package com.aws.codestar.projecttemplates.vo;

import java.sql.Timestamp;
public class VOSensorData {
    private int idx;
    private String data;
    private Timestamp date;
    private String id;
    private int amount;
    private String num;
    private double lat;
    private double lon;
    
    public VOSensorData(){}

    
    public int getIdx(){ return this.idx; }
    public void setIdx(int idx) { this.idx = idx; }
    public String getData() { return this.data; }
    public void setData(String data){ this.data = data; }
    public Timestamp getDate() { return this.date; }
    public void setDate(Timestamp date){ this.date = date; }
    public String getId() { return this.id; }
    public void setId(String id){ this.id = id; }
    public int getAmount() { return this.amount; }
    public void setAmount(int amount) { this.amount = amount; }
    public String getNum() { return this.num; }
    public void setNum(String num){ this.num = num; }
    public double getLat() { return this.lat; }
    public void setLat(double lat){ this.lat = lat; }
    public double getLon() { return this.lon; }
    public void setLon(double lon){ this.lon = lon; }
}