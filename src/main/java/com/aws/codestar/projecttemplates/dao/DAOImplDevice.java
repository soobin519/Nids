package com.aws.codestar.projecttemplates.dao;
import  com.aws.codestar.projecttemplates.vo.VODevice;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
 
@Repository
public class DAOImplDevice implements DAODevice {
 
    @Autowired
    private SqlSession sqlSession;
    
    private static final String Namespace = "com.aws.codestar.projecttemplates.MapperDevice";
    
    @Override
    public List<VODevice> selectDeviceWithID(String id) throws Exception{
        List<VODevice> result = sqlSession.selectList(Namespace+".selectDeviceWithID", id);
        return result;
    }
    
    @Override
    public int insertDevice(VODevice device) throws Exception{
        int result = sqlSession.insert(Namespace+".insertDevice", device);
        return result;
    }
    
    @Override
    public int insertDeviceList(List<VODevice> devices) throws Exception{
        int result = 0;
        for(VODevice device: devices){
            result += sqlSession.insert(Namespace+".insertDevice", device);
        }
        return result;
    }
}