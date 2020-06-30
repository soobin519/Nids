package com.aws.codestar.projecttemplates.dao;

import java.util.List;

import com.aws.codestar.projecttemplates.vo.VOUser;

public interface DAOUser{
    public List<VOUser> selectUser() throws Exception;
    public int insertUser(VOUser user) throws Exception; //웹
    public int registerUser(VOUser user) throws Exception; //모바일
    public int naverRegisterUser(VOUser user) throws Exception; //네이버 모바일
    public VOUser selectUserWithID(String id) throws Exception;
    public VOUser selectUserWithAuth(String auth) throws Exception;
    public int modifyPassword(VOUser user) throws Exception;
    public int modifyUser(VOUser user) throws Exception; //모바일 회원정보 수정
}