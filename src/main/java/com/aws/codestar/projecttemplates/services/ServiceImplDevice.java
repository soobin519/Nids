package com.aws.codestar.projecttemplates.services;
 
import java.util.List;
 
import javax.inject.Inject;
 
import org.springframework.stereotype.Service;
 
import com.aws.codestar.projecttemplates.dao.DAODevice;
import com.aws.codestar.projecttemplates.vo.VODevice;
 
@Service
public class ServiceImplDevice implements ServiceDevice {
 
    @Inject
    private DAODevice dao;
    
    @Override
    public List<VODevice> selectDeviceWithID(String id) throws Exception{
        List<VODevice> result = dao.selectDeviceWithID(id);
        return result;
    }
    
    @Override
    public int insertDevice(VODevice device) throws Exception{
        int result = dao.insertDevice(device);
        return result;
    }
    
    @Override
    public int insertDeviceList(List<VODevice> devices) throws Exception{
        int result = 0;
        for(VODevice device: devices ){
            result += dao.insertDevice(device);
        }
        return result;
    }
}