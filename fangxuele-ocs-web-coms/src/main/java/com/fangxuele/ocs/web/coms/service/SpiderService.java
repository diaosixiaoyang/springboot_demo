package com.fangxuele.ocs.web.coms.service;

import com.fangxuele.ocs.common.dto.response.ResponseDTO;
import com.fangxuele.ocs.common.util.HttpClientUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springside.modules.utils.mapper.JsonMapper;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

/**
 * 爬虫相关Service
 *
 * @author wfc
 * @date 2017/8/17
 */
@Service
public class SpiderService {

    private static JsonMapper mapper = new JsonMapper(JsonInclude.Include.ALWAYS);

    private static final String DANGDANG_SEARCH_BASE_URL = "http://search.dangdang.com";

    private static final String DANGDANG_PRODUCT_DETAIL_URL = "http://product.dangdang.com/index.php?r=callback%2Fdetail&templateType=publish&describeMap=&shopId=0&categoryPath=01.41.50.03.00.00&productId=";

    private static final String JINGDONG_BASE_URL = "https://search.jd.com/Search";

    private static final String PREFIX_URL = "https:";

    private static final String M_JD_BASE_URL = "https://so.m.jd.com/ware/search.action?keyword=";

    private static final String M_JD_INDEX_URL = "https://m.jd.com/";

    private static final String M_DD_BASE_URL = "http://search.m.dangdang.com/search.php?keyword=";

    private static final String M_DD_INDEX_URL = "http://m.dangdang.com/";

    private WebClient webClient;

    @PostConstruct
    public void init() {
        // 初始化浏览器对象
        webClient = new WebClient();
        // 配置是否加载css和javaScript
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        // webClient.getOptions().setRedirectEnabled(true);

        // 初始化Httpclient
        HttpClientUtil.init();
    }

    /**
     * 根据isbn获取京东手机图书详情URL
     *
     * @param isbn
     * @return
     * @throws IOException
     */
    public String getMJdDetailUrlByIsbn(String isbn) throws IOException {
        // 获取网页对象
        HtmlPage htmlpage = webClient.getPage(M_JD_BASE_URL + isbn);

        DomElement seachList = htmlpage.getElementById("seach_list");

        DomNodeList<HtmlElement> a = seachList.getElementsByTagName("a");

        if (!a.isEmpty()) {
            String href;
            String behaviordata = a.get(0).getAttribute("behaviordata");
            if (StringUtils.isNotEmpty(behaviordata)) {
                Map<String, String> map = mapper.fromJson(behaviordata, Map.class);
                href = map.get("url");
            } else {
                href = a.get(0).getAttribute("href");
            }

            return PREFIX_URL + href.substring(0, href.indexOf("?"));

        }
        return M_JD_INDEX_URL;
    }


    /**
     * 根据isbn获取当当手机图书详情URL
     *
     * @param isbn
     * @return
     * @throws IOException
     */
    public String getMDdDetailUrlByIsbn(String isbn) throws IOException, InterruptedException {
        // 获取网页对象
        HtmlPage htmlpage = webClient.getPage(M_DD_BASE_URL + isbn);

        // 线程等待3秒, 待页面加载完js
        Thread.sleep(3000);

        DomElement seachList = htmlpage.getElementById("j_list");

        DomNodeList<HtmlElement> a = seachList.getElementsByTagName("a");

        if (!a.isEmpty()) {
            String href = a.get(0).getAttribute("href");
            return href.substring(0, href.indexOf("?"));
        }
        return M_DD_INDEX_URL;
    }

    /**
     * 根据isbn获取京东图书详情URL
     *
     * @param isbn
     * @return
     * @throws IOException
     */
    public ResponseDTO getJdDetailUrlByIsbn(String isbn) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        Map<String, Object> params = Maps.newHashMap();
        params.put("keyword", isbn);
        params.put("enc", "utf-8");
        params.put("wq", isbn);
        String html = HttpClientUtil.callUrlGet(JINGDONG_BASE_URL, params);

        // 获取dom并解析
        Document document = Jsoup.parse(html);

        Element element = document.getElementById("J_goodsList");

        String href;
        if (element != null) {
            // 获取详情url
            href = element.getElementsByTag("a").get(0).attr("href");
        } else {
            href = JINGDONG_BASE_URL;
        }
        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg(href);
        return responseDTO;
    }

    /**
     * 根据isbn获取当当图书详情URL
     *
     * @param isbn
     * @return
     * @throws IOException
     */
    public ResponseDTO getDdDetailUrlByIsbn(String isbn) throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        Map<String, Object> params = Maps.newHashMap();
        params.put("key", isbn);
        params.put("art", "input");
        String html = HttpClientUtil.callUrlGet(DANGDANG_SEARCH_BASE_URL, params);

        // 获取dom并解析
        Document document = Jsoup.parse(html);

        Element element = document.getElementById("search_nature_rg");
        String href;
        if (element != null) {
            // 获取详情url
            href = element.getElementsByTag("a").get(0).attr("href");
        } else {
            href = DANGDANG_SEARCH_BASE_URL;
        }
        responseDTO.setSuccess(true);
        responseDTO.setStatusCode(0);
        responseDTO.setMsg(href);
        return responseDTO;
    }
}
