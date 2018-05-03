package cn.xyy.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.xyy.springboot.domain.User;
import cn.xyy.springboot.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@Api("用户相关信息")
@ApiModel(value = "apiModel")
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "用户登陆",notes = "根据用户账户和密码获取用户信息")
	@ApiImplicitParams({@ApiImplicitParam(value = "用户账号", required = true ,dataType = "String", name = "username"),
			@ApiImplicitParam(value = "用户密码", required = true ,dataType = "String", name = "password")})
	@GetMapping(value = "/login/{username}/{password}")
	public Map<String, Object> login(@PathVariable("username") String username,@PathVariable("password") String password){
		User user = userService.login(username,password);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("status", "200");
		map.put("message", "登录成功");
		map.put("user", user);
		return map;
	}
	
	@ApiOperation(value = "新增用户",notes = "向数据库新增用户信息")
	@ApiModelProperty(value = "用户账户名", name = "username", notes = "用户登陆账户", dataType = "String", reference = "apiModel")
	@PostMapping(value = "/save")
	public Map<String, Object> save(User user){
		Map<String,Object> map = userService.save(user);
		return map;
	}
	/**
	 * 通过thymeleaf来进行页面渲染，使用model来封装参数，return页面的逻辑视图
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String list(Model model){
		List<User> list = userService.queryAll();
		model.addAttribute("users", list);
		return "list";
	}


}
