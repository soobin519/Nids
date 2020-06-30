package com.aws.codestar.projecttemplates.services;
 
import java.util.List;
 
import com.aws.codestar.projecttemplates.vo.VOCar;
 
public interface ServiceCar {
    
    public int insertCar(VOCar car) throws Exception;
    public int deleteCar(VOCar car) throws Exception;
    public int editCar(VOCar car) throws Exception;
    public VOCar selectCarWithID(String id) throws Exception;
    public VOCar selectCarWithAuth(String value) throws Exception;
}
