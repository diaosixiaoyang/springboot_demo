package cn.xyy.springboot.service;

import java.util.List;
import java.util.Map;

import cn.xyy.springboot.domain.User;

public interface UserService {

	User login(String username, String password);

	Map<String, Object> save(User user);

	List<User> queryAll();

}
