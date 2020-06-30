package com.aws.codestar.projecttemplates.services;
 
import java.util.List;
 
import javax.inject.Inject;
 
import org.springframework.stereotype.Service;
 
import com.aws.codestar.projecttemplates.dao.DAOUser;
import com.aws.codestar.projecttemplates.vo.VOUser;
 
@Service
public class ServiceImplUser implements ServiceUser {
 
    @Inject
    private DAOUser dao;
    
    @Override
    public List<VOUser> selectUser() throws Exception {
        System.out.println("Service Impl User - selectUser");
        
        List<VOUser> result = dao.selectUser();
        if(result != null){
            if(!result.isEmpty())
            {
                System.out.println("Service -- object is not empty! : " + String.valueOf(result.size()));
            }
            else
            {
                System.out.println("Service -- object is empty..");
            }
        }else {System.out.println("Service Impl : Object is null" );}
        
        return result;
    }
   
    //웹
    @Override
    public int insertUser(VOUser user) throws Exception {
        int result = dao.insertUser(user);
        System.out.println("Service -- insert result : " + String.valueOf(result));
        return result;
    }
    
    //모바일
    @Override
    public int registerUser(VOUser user) throws Exception    {
        int result = dao.registerUser(user);
        System.out.println("service -- insert result : " + String.valueOf(result));
        return result;
    }
    
    //네이버 모바일
    @Override
    public int naverRegisterUser(VOUser user) throws Exception    {
        int result = dao.registerUser(user);
        System.out.println("service -- insert result(naver) : " + String.valueOf(result));
        return result;
    }
     
    @Override
    public VOUser selectUserWithID(String id) throws Exception{
        VOUser result = dao.selectUserWithID(id);
        return result;
    }
    
    @Override
    public VOUser selectUserWithAuth(String auth) throws Exception{
        VOUser result = dao.selectUserWithAuth(auth);
        return result;
    }
    
    @Override
    public int modifyPassword(VOUser user) throws Exception{
        int result = dao.modifyPassword(user);
        return result;
    }
    
    //모바일 회원 정보 수정
    @Override
    public int modifyUser(VOUser user) throws Exception{
        int result = dao.modifyUser(user);
        return result;
    }
    
}
