package com.aws.codestar.projecttemplates.vo;

public class VOCar {
    private int model;
    private String num;
    private String id;
    
    public VOCar() { }
  
    public VOCar(String id, String num, int model)    {
        this.model = model;
        this.num = num;
        this.id = id;
    }
    
    public int getModel(){ return this.model; }
    public void setModel(int model) { this.model = model; }
    public String getNum() { return this.num; }
    public void setNum(String num){ this.num = num; }
    public String getId() { return this.id; }
    public void setId(String id){ this.id = id; }
    
}
