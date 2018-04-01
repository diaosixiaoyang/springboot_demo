package com.fangxuele.ocs.inter.m.service;

import java.util.Map;

/**
 * Created by wfc on 2017/7/21.
 */
public interface DictionaryMService {

	void initDictionary();

	Map<Integer, String> getStatueMap(String classCode);
}
