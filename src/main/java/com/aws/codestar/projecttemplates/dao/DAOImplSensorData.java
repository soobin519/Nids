package com.aws.codestar.projecttemplates.dao;
import  com.aws.codestar.projecttemplates.vo.VOSensorData;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import java.util.HashMap; 
import java.util.Map; 
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class DAOImplSensorData implements DAOSensorData{
    
    @Autowired
    private SqlSession sqlSession;
    
    private static final String Namespace = "com.aws.codestar.projecttemplates.MapperData";
    
    @Override
    public VOSensorData selectLastData(String id) throws Exception {
        System.out.println("DAO Impl - selectData : " + id);
        
        VOSensorData result = sqlSession.selectOne(Namespace+".selectLastData", id);
        
        System.out.println(result.getData());
        return result;
    }
    
    @Override
    public int insertData(VOSensorData data) throws Exception{
        int result = sqlSession.insert(Namespace+".insertData", data);
        System.out.println("DAO insert data result : " + String.valueOf(result));
        return result;
    }
    
    @Override
    public int insertDataWithTime(VOSensorData data) throws Exception{
        int result = sqlSession.insert(Namespace+".insertDataWithTime", data);
        System.out.println("DAO insert data result : " + String.valueOf(result));
        return result;
    }
    
    @Override
    public List<VOSensorData> selectDataWithHour(String id, int hour) throws Exception{
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("id", id);
        parameters.put("hour", hour);
        List<VOSensorData> result = sqlSession.selectList(Namespace+".selectDataWithHour", parameters);
        System.out.println("DAO select with hour result : " + String.valueOf(result.size()));
        return result;
    }
    
    @Override
    public List<VOSensorData> selectDataWithCarNum(String num, int hour) throws Exception{
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("num", num);
        parameters.put("hour", hour);
        List<VOSensorData> result = sqlSession.selectList(Namespace+".selectDataWithCarNum", parameters);
        System.out.println("DAO select with car_num result : " + String.valueOf(result.size()));
        return result;
    }
    
    @Override
    public List<VOSensorData> selectThisWeek(String id) throws Exception{
        List<VOSensorData> result = sqlSession.selectList(Namespace+".selectThisWeek", id);
        return result;
    }
    
    @Override
    public List<VOSensorData> selectLastWeek(String id) throws Exception{
        List<VOSensorData> result = sqlSession.selectList(Namespace+".selectLastWeek", id);
        return result;
    }
    
        
    @Override
    public List<VOSensorData> selectLocatWithID(String id) throws Exception{
        List<VOSensorData> result = sqlSession.selectList(Namespace+".selectLocatWithID", id);
        return result;
    }
}