package com.fangxuele.ocs.inter.coms.service;

import java.util.Map;

/**
 * Created by wfc on 2017/7/21.
 */
public interface DictionaryLomsService {

    void initDictionary();

    Map<Integer, String> getStatueMap(String classCode);
}
