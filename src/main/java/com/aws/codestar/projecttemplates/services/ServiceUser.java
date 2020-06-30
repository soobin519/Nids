package com.aws.codestar.projecttemplates.services;
 
import java.util.List;
 
import com.aws.codestar.projecttemplates.vo.VOUser;
 
public interface ServiceUser {
    
    public List<VOUser> selectUser() throws Exception;
    public int insertUser(VOUser user) throws Exception;
    public int registerUser(VOUser user) throws Exception;
    public int naverRegisterUser(VOUser user) throws Exception;
    public VOUser selectUserWithID(String id) throws Exception;
    public VOUser selectUserWithAuth(String auth) throws Exception;
    public int modifyPassword(VOUser user) throws Exception;
    public int modifyUser(VOUser user) throws Exception;
}
