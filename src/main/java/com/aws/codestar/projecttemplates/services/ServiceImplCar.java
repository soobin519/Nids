package com.aws.codestar.projecttemplates.services;
 
import java.util.List;
 
import javax.inject.Inject;
 
import org.springframework.stereotype.Service;
 
import com.aws.codestar.projecttemplates.dao.DAOCar;
import com.aws.codestar.projecttemplates.vo.VOCar;
 
@Service
public class ServiceImplCar implements ServiceCar {
 
    @Inject
    private DAOCar dao;
    
    @Override
    public int insertCar(VOCar car) throws Exception{
        int result = dao.insertCar(car);
        return result;
    }
    @Override
    public int deleteCar(VOCar car) throws Exception{
        int result = dao.deleteCar(car);
        return result;
    }
    @Override
    public int editCar(VOCar car) throws Exception{
        int result = dao.editCar(car);
        return result;
    }
    @Override
    public VOCar selectCarWithID(String id) throws Exception{
        VOCar result = dao.selectCarWithID(id);
        return result;
    }
    @Override
    public VOCar selectCarWithAuth(String value) throws Exception{
        VOCar result = dao.selectCarWithAuth(value);
        return result;
    }
    
}