package com.sourcod.wechat.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sourcod.wechat.mapper.KyfwMapper;
import com.sourcod.wechat.service.KyfwService;

@Service
@Transactional
public class KyfwServiceImpl implements KyfwService {

	@Resource
	private KyfwMapper mapper;

	@Override
	public int selectCount() {
		return mapper.selectCount();
	}

}
