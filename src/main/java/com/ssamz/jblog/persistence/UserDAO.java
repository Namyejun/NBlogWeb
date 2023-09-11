package com.ssamz.jblog.persistence;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssamz.jblog.domain.User;

@Repository
public class UserDAO {
	
	@Autowired // 의존성 주입(DI, Dependency Injection)
	private SqlSessionTemplate mybatis;
	
	public void insertUser(User user) {
		mybatis.insert("insertUser", user);
	}
	
	public void updateUser(User user) {
		mybatis.update("updateUser", user);
	}
	
	public void deleteUser(User user) {
		mybatis.delete("deleteUser", user);
	}
	
	public User getUser(User user) {
		return mybatis.selectOne("getUser", user);
	}
	
	public List<User> getUserList() {
		return mybatis.selectList("getUserList");
	}
}
