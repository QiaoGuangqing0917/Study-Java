package com.qgq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : Controller
 * @Author : QiaoGuangqing
 * @Date : 2020-06-10
 * @Description :
 */
@RestController
public class Controller {

	@Autowired
	UserService userService;

	@GetMapping("/getUserById")
	public String getUserById(long userId){
		return userService.getById(userId).toString();
	}
}
