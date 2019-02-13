package com.appliance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appliance.mapper.DemoMapper;
import com.appliance.model.BaseResponse;
import com.appliance.pojo.DemoPojo;
import com.appliance.pojo.dto.DemoDto;
import com.appliance.service.DemoService;
import com.appliance.utils.MD5;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DemoServiceImpl implements DemoService {
	@Autowired
	private DemoMapper demoMapper;

	@Override
	@Deprecated
	public BaseResponse<DemoPojo> demoLogin(DemoDto demoDto) {
		DemoPojo demoLogin = demoMapper.demoLogin(demoDto);
		demoDto.setPassword(MD5.md5(demoDto.getPassword()));
		if (demoLogin != null && demoDto.getPassword().equals(demoLogin.getPassword())) {
			BaseResponse<DemoPojo> result = new BaseResponse<>();
			demoLogin.setPassword(null);
			result.setResponseData(demoLogin);
			result.setStatusCode(200);
			result.setStatusMsg("SUCCESS");
			return result;
		} else {
			BaseResponse<DemoPojo> result = new BaseResponse<>();
			result.setResponseData(demoLogin);
			result.setStatusCode(201);
			result.setStatusMsg("FAILED");
			return result;
		}
	}

	@Override
	public BaseResponse<PageInfo<DemoPojo>> getPageInfoDemo(DemoDto demoDto) { // TODO 改成try后未测试，2019年2月13日 23:09:23
		try {
			PageHelper.startPage(demoDto.getPageNum(), demoDto.getPageSize());
			List<DemoPojo> list = demoMapper.getPageInfoDemo(demoDto);
			PageInfo<DemoPojo> pageInfo = new PageInfo<>(list);
			BaseResponse<PageInfo<DemoPojo>> result = new BaseResponse<>();
			result.setResponseData(pageInfo);
			result.setStatusCode(200);
			result.setStatusMsg("SUCCESS");
			return result;
		} catch (Exception e) {
			log.error("测试分页失败");
			BaseResponse<PageInfo<DemoPojo>> result = new BaseResponse<>();
			result.setStatusCode(201);
			result.setStatusMsg("FAILED");
			return result;
		}
	}
}
