package cn.xyy.springboot.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.xyy.springboot.domain.User;
import cn.xyy.springboot.mapper.UserMapper;
import cn.xyy.springboot.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public User login(String username, String password) {
		User user = userMapper.selectByUsernameAndPassword(username, password);
		return user;
	}

	@Override
	public Map<String, Object> save(User user) {
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			userMapper.insertSelective(user);
			map.put("message", "保存成功！");
		}catch(Exception e) {
			e.printStackTrace();
			map.put("message", "保存失败！");
		}
		return map;
	}

	@Override
	public List<User> queryAll() {
		return userMapper.selectAll();
	}

}
