package cn.xyy.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.xyy.springboot.domain.User;


public interface UserMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	User selectByUsernameAndPassword(@Param("username") String username,
			@Param("password") String password);

	List<User> selectAll();
}