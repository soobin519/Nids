package com.aws.codestar.projecttemplates.dao;
 
import java.util.List;
 
import com.aws.codestar.projecttemplates.vo.VOCar;
 
public interface DAOCar {
   
    public int insertCar(VOCar car) throws Exception;
    public int deleteCar(VOCar car) throws Exception;
    public int editCar(VOCar car) throws Exception;
    public VOCar selectCarWithID(String id) throws Exception;
    public VOCar selectCarWithAuth(String num) throws Exception;
}