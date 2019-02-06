package com.appliance.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.appliance.model.BaseResponse;
import com.appliance.pojo.DemoPojo;
import com.appliance.pojo.dto.DemoDto;
import com.appliance.service.DemoService;
import com.appliance.utils.ExportPOIUtils;
import com.appliance.utils.MD5;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/appliance/demo")
public class DemoController {

	@Autowired
	private DemoService demoService;

	/**
	 * 测试MD5转换和返回字符串
	 * 
	 * @return
	 */
	@GetMapping(value = "/hello") // 如果只接受GET请求，sonar要求使用GetMapping，POST请求要求使用PostMapping；只有两者都接受的接口才写成@RequestMapping，@RequestMapping里不需要加方法为GET或POST
	public String helloWorld(@RequestHeader String token) {// 测试请求头，如果请求头和对象名不一样，需要在此注解加上括号并写入传入的名称，如：@RequestHeader("实际请求头名称")
		// 测试时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String nowTime = sdf.format(date);
		// 测试MD5
		return MD5.md5("undefined") + token + nowTime;
	}

	/**
	 * 测试返回json
	 * 
	 * @return
	 */
	@GetMapping(value = "/demoToString", produces = { "application/json" })
	public List<DemoPojo> demoToString() {
		DemoPojo demopojo = new DemoPojo();
		demopojo.setUser("admin");
		demopojo.setPassword(MD5.md5("123456"));
		DemoPojo demopojo2 = new DemoPojo();
		demopojo2.setUser("admin2");
		demopojo2.setPassword("654321");
		List<DemoPojo> listDemoPojo = new ArrayList<>();
		listDemoPojo.add(demopojo);
		listDemoPojo.add(demopojo2);
		// 转为json格式,由于用了RestController注解，所以不一定需要toJSONString方法将对象和List转换为JSON，Controller层的方法返回类型可以写为对象和List
		// 考虑到整体框架需要比较合理的格式，未来的Controller方法返回类型写为String,此方法做Demo
		return listDemoPojo;// 可用fastjson来转换为JSON,可以转换list和object
							// 范例：JSON.toString(Object)/JSON.toString(ArrayList)
	}

	/**
	 * 测试连接数据库并获取值(随着项目进展，此测试方法废除。时间：2019年1月26日 01:42:14)
	 * 
	 * @param httpSession
	 * @param DemoDto
	 * @return
	 */
	@Deprecated
	@PostMapping(value = "/demoLogin", produces = { "application/json" })
	public String demoLogin(HttpSession httpSession, HttpServletResponse response, @RequestBody DemoDto demoDto) {
		log.info("执行demoLogin");
		BaseResponse<DemoPojo> result = demoService.demoLogin(demoDto);
		String resultToString = JSON.toJSONString(result);
		log.info("demoLogin返回的JSON: {}", resultToString);
		return resultToString;
	}

	/**
	 * 测试POI导出功能
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/demoExecl")
	public String demoExecl(HttpServletResponse response) throws IOException {// postman下载的文件名默认叫response.xls
		DemoPojo demopojo = new DemoPojo();
		demopojo.setUser("admin");
		demopojo.setPassword(MD5.md5("123456"));
		List<DemoPojo> listDemopojo = new ArrayList<>();
		listDemopojo.add(demopojo);
		String fileName = "账户密码";
		// 列名
		String[] columnNames = { "用户", "密码" };
		// map中的key
		String[] keys = { "user", "password" };
		ExportPOIUtils.startDownload(response, fileName, listDemopojo, columnNames, keys);
		return "导出出现问题，请检查导出工具";
	}

	/**
	 * 测试动态路由格式,getMenu
	 * 
	 * @param role
	 * @return
	 */
	@RequestMapping(value = "/demoRouter")
	public String demoRouter(String role) {
		DemoPojo demoPojo = new DemoPojo();
		demoPojo.setUser("测试");
		demoPojo.setPassword("aaaaa");
		DemoDto demoDto = new DemoDto();
		demoDto.setUser("afgaswfgafg");
		List<DemoDto> demoDtoList = new ArrayList<>();
		demoDtoList.add(demoDto);
		demoPojo.setDemoDto(demoDtoList);
		// 第二条路由，以此类推
		DemoPojo demoPojo2 = new DemoPojo();
		demoPojo2.setUser("测试2");
		demoPojo2.setPassword("aaaaa2");
		DemoDto demoDto2 = new DemoDto();
		demoDto2.setUser("afgaswfgafg2");
		List<DemoDto> demoDtoList2 = new ArrayList<>();
		demoDtoList2.add(demoDto2);
		demoPojo2.setDemoDto(demoDtoList2);
		// 把每条单独的路由存到集合中，返回给前端一个侧边栏的JSON
		List<DemoPojo> demoPojoList = new ArrayList<>();
		demoPojoList.add(demoPojo);
		demoPojoList.add(demoPojo2);
		return JSON.toJSONString(demoPojoList);// 最后可以返回一个符合前端路由格式的JSON，格式如下：
												// [{"a0":"a0","b0":[{"c0":"c0"},{"d0":"d0"}]},{"a1":"a1","b1":[{"c1":"c1"},{"d1":"d1"}]},...]
	}
}
