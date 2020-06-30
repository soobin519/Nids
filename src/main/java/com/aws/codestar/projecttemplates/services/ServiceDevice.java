package com.aws.codestar.projecttemplates.services;
 
import java.util.List;
 
import com.aws.codestar.projecttemplates.vo.VODevice;
 
public interface ServiceDevice {

    public List<VODevice> selectDeviceWithID(String id) throws Exception;
    public int insertDevice(VODevice device) throws Exception;
    public int insertDeviceList(List<VODevice> devices) throws Exception;
}
