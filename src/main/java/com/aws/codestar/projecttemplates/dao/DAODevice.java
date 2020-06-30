package com.aws.codestar.projecttemplates.dao;
 
import java.util.List;
 
import com.aws.codestar.projecttemplates.vo.VODevice;
 
public interface DAODevice {
    
    public List<VODevice> selectDeviceWithID(String id) throws Exception;
    public int insertDevice(VODevice device) throws Exception;
    public int insertDeviceList(List<VODevice> device) throws Exception;
}