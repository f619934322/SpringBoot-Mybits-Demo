package com.appliance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.appliance.annotation.Idempotent;
import com.appliance.model.BaseResponse;
import com.appliance.pojo.dto.UserDto;
import com.appliance.pojo.vo.UserVo;
import com.appliance.service.LoginService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/appliance/user")
public class LoginController {

	@Autowired
	private LoginService loginService;

	/**
	 * 用户登录
	 * 
	 * @param userDto
	 * @return
	 */
	@Idempotent(value = "/userLogin", expireMillis = 1000L)
	@PostMapping(value = "/userLogin", produces = { "application/json" })
	public String login(@RequestBody UserDto userDto) {
		log.info("执行userLogin");
		BaseResponse<UserVo> response = loginService.login(userDto);
		String resultToString = JSON.toJSONString(response);
		log.info("userLogin返回的JSON: {}", resultToString);
		return resultToString;
	}

	/**
	 * 用户登出
	 * 
	 * @return
	 */
	@Idempotent(value = "/userLogout", expireMillis = 1000L)
	@PostMapping(value = "/userLogout", produces = { "application/json" })
	public String userLogout() {
		log.info("执行userLogout");
		BaseResponse<UserVo> response = loginService.logout();
		String resultToString = JSON.toJSONString(response);
		log.info("userLogout返回的JSON: {}", resultToString);
		return resultToString;
	}

	/**
	 * 获取用户信息给前端
	 * 
	 * @return
	 */
	@GetMapping(value = "/getUserInfo", produces = { "application/json" })
	public String getUserInfo() {
		log.info("执行getUserInfo");
		BaseResponse<UserVo> response = loginService.getUserInfo();
		String resultToString = JSON.toJSONString(response);
		log.info("getUserInfo返回的JSON: {}", resultToString);
		return resultToString;
	}
}
