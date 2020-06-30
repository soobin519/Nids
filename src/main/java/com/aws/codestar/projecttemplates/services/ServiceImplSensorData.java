package com.aws.codestar.projecttemplates.services;
 
import java.util.List;
 
import javax.inject.Inject;
 
import org.springframework.stereotype.Service;
 
import com.aws.codestar.projecttemplates.dao.DAOSensorData;
import com.aws.codestar.projecttemplates.vo.VOSensorData;
 
@Service
public class ServiceImplSensorData implements ServiceSensorData {
 
    @Inject
    private DAOSensorData dao;
    
    @Override
    public VOSensorData selectLastData(String id) throws Exception {
        System.out.println("Service Impl Data - selectData");
        
        VOSensorData result = dao.selectLastData(id);
        return result;
    }
    
    @Override
    public List<VOSensorData> selectDataWithHour(String id, int hour) throws Exception{
        List<VOSensorData> result = dao.selectDataWithHour(id, hour);
        System.out.println("Service Impl Data - selectData");
        return result;
    }
    
    @Override
    public List<VOSensorData> selectDataWithCarNum(String num, int hour) throws Exception{
        List<VOSensorData> result = dao.selectDataWithCarNum(num, hour);
        System.out.println("Service Impl Data - selectData");
        return result;
    }
    
    @Override
    public List<VOSensorData> selectThisWeek(String id) throws Exception{
        List<VOSensorData> result = dao.selectThisWeek(id);
        System.out.println("Service Impl Data - selectThisWeek");
        return result;
    }
    
    @Override
    public List<VOSensorData> selectLastWeek(String id) throws Exception{
        List<VOSensorData> result = dao.selectLastWeek(id);
        System.out.println("Service Impl Data - selectThisWeek");
        return result;
    }
    
    @Override
    public List<VOSensorData> selectLocatWithID(String id) throws Exception{
        List<VOSensorData> result = dao.selectLocatWithID(id);
        return result;
    }
    
    @Override
    public int insertData(VOSensorData data) throws Exception {
        int result = dao.insertData(data);
        System.out.println("Service -- insert data result : " + String.valueOf(result));
        return result;
    }
    
    @Override
    public int insertDataWithTime(VOSensorData data) throws Exception {
        int result = dao.insertDataWithTime(data);
        System.out.println("Service -- insert data result : " + String.valueOf(result));
        return result;
    }
}