package com.fangxuele.ocs.web.coms.service;

import com.fangxuele.ocs.mapper.domain.TLogInfo;
import com.fangxuele.ocs.mapper.mapper.TLogInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LogInfoService {

	@Autowired
	private TLogInfoMapper logInfoMapper;

	public void save(TLogInfo logInfo) {
		logInfoMapper.insert(logInfo);
	}

}
