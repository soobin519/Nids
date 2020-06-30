package com.aws.codestar.projecttemplates.services;
 
import java.util.List;
 
import com.aws.codestar.projecttemplates.vo.VOSensorData;
 
public interface ServiceSensorData {
    
    public VOSensorData selectLastData(String id) throws Exception;
    public List<VOSensorData> selectDataWithHour(String id, int hour) throws Exception;
    public List<VOSensorData> selectDataWithCarNum(String num, int hour) throws Exception;
    public List<VOSensorData> selectThisWeek(String id) throws Exception;
    public List<VOSensorData> selectLastWeek(String id) throws Exception;
    public List<VOSensorData> selectLocatWithID(String id) throws Exception;
    
    public int insertData(VOSensorData data) throws Exception;
    public int insertDataWithTime(VOSensorData data) throws Exception;
}