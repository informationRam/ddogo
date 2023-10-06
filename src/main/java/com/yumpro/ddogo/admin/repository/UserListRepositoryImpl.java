package com.yumpro.ddogo.admin.repository;

import com.yumpro.ddogo.admin.domain.UserDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserListRepositoryImpl implements UserListRepository {
    private final SqlSession sqlSession;
    @Override
    public List<UserDTO> userList(Map<String,Object> map) throws DataAccessException{
        return sqlSession.selectList("admin.userList",map);
    }
    @Override
    public int getUserListCount(Map<String, Object> map){
        return sqlSession.selectOne("admin.userListCnt",map);
    }
}
