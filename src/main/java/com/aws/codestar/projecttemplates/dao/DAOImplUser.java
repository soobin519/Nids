package com.aws.codestar.projecttemplates.dao;
import  com.aws.codestar.projecttemplates.vo.VOUser;

import javax.inject.Inject;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class DAOImplUser implements DAOUser{
    
    @Autowired
    private SqlSession sqlSession;
    
    private static final String Namespace = "com.aws.codestar.projecttemplates.MapperUser";
    
    @Override
    public List<VOUser> selectUser() throws Exception {
        System.out.println("DAO Impl - selectUser");
        List<VOUser> result = sqlSession.selectList(Namespace+".selectUser");
        if(result != null){
            if(!result.isEmpty())
            {
                System.out.println("object is not empty! : " + String.valueOf(result.size()));
            }
            else
            {
                System.out.println("object is empty..");
            }
        }else {System.out.println("DAO Impl : Object is null" );}
        
        return result;
    }
    
    //웹
    @Override
    public int insertUser(VOUser user) throws Exception{
        int result = sqlSession.insert(Namespace+".insertUser", user);
        System.out.println("DAO insert result : " + String.valueOf(result));
        return result;
    }
    
    //모바일
    @Override
    public int registerUser(VOUser user) throws Exception{
        int result = sqlSession.insert(Namespace+".registerUser", user);
        System.out.println("DAO insert result : " + String.valueOf(result));
        return result;
    }
    
    //네이버 모바일
    @Override
    public int naverRegisterUser(VOUser user) throws Exception{
        int result = sqlSession.insert(Namespace+".naverRegisterUser", user);
        System.out.println("DAO insert result : " + String.valueOf(result));
        return result;
    }
    
    @Override
    public VOUser selectUserWithID(String id) throws Exception{
        VOUser result = sqlSession.selectOne(Namespace+".selectUserWithID", id);
        return result;
    }
    
    @Override
    public VOUser selectUserWithAuth(String auth) throws Exception{
        System.out.println("dao - auth : " + auth);
        VOUser result = sqlSession.selectOne(Namespace+".selectUserWithAuth", auth);
        return result;
    }
    
    @Override
    public int modifyPassword(VOUser user) throws Exception{
        int result = sqlSession.update(Namespace+".modifyPassword",user);
        return result;
    }
    
    //모바일 회원 정보 수정
    @Override
    public int modifyUser(VOUser user) throws Exception{
        int result = sqlSession.update(Namespace+".modifyUser",user);
        return result;
    }
}