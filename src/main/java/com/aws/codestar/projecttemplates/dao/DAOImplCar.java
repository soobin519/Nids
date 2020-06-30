package com.aws.codestar.projecttemplates.dao;
import  com.aws.codestar.projecttemplates.vo.VOCar;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
 
@Repository
public class DAOImplCar implements DAOCar {
 
    @Autowired
    private SqlSession sqlSession;
    
    private static final String Namespace = "com.aws.codestar.projecttemplates.MapperCar";

    @Override
    public int insertCar(VOCar car) throws Exception{
        int result = sqlSession.insert(Namespace+".insertCar", car);
        return result;
    }
    @Override
    public int deleteCar(VOCar car) throws Exception{
        int result = sqlSession.delete(Namespace+".deleteCar", car);
        return result;
    }
    @Override
    public int editCar(VOCar car) throws Exception{
        int result = sqlSession.update(Namespace+".editCar", car);
        return result;
    }
    @Override
    public VOCar selectCarWithID(String id) throws Exception{
        VOCar result = sqlSession.selectOne(Namespace+".selectCarWithID", id);
        return result;
    }
    @Override
    public VOCar selectCarWithAuth(String value) throws Exception{
        VOCar result = sqlSession.selectOne(Namespace+".selectCarWithAuth", value);
        return result;
    }
    
}