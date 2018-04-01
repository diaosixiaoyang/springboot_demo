package com.fangxuele.ocs.web.coms.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fangxuele.ocs.common.constant.OcsConstant;
import com.fangxuele.ocs.common.constant.RedisCacheConstant;
import com.fangxuele.ocs.common.util.ImageUtil;
import com.fangxuele.ocs.inter.cache.service.RedisCacheService;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.utils.misc.IdGenerator;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Reference(version = "1.0.0")
    private RedisCacheService redisCacheService;

    // 七牛图片服务器上传管理器类
    private UploadManager uploadManager = null;

    private BucketManager bucketManager = null;

    // 七牛图片服务图书封面图片 前缀
    public static final String BOOK_COVER_PREFIX = "book/cover/";

    // 七牛图片服务图书头图图片 前缀
    public static final String BOOK_BANNER_PREFIX = "book/banner/";

    // 七牛图片用户头像 前缀
    public static final String AVATAR_PREFIX = "user/avatar/";

    // 七牛图片服务首页轮播图 前缀
    public static final String HOME_BANNER_PREFIX = "home/banner/";

    // 七牛图片服务预览图 前缀
    public static final String BOOK_PAGE_IMAGE_PREFIX = "book/pageImage/";

    // 七牛图片服务首页广告位 前缀
    public static final String HOME_AD_PREFIX = "home/ad/";

    // 七牛图片服务首页是书单广告位 前缀
    public static final String HOME_CHANNEL_AD_PREFIX = "home/channelAd/";

    // 七牛图片服务首页栏目图 前缀
    public static final String HOME_CHANNEL_PREFIX = "home/channel/";

    // 七牛图片服务图书详情图 前缀
    public static final String BOOK_DETAIL_PREFIX = "book/detail/";

    // 七牛图片服务图标 前缀
    public static final String ICON_PREFIX = "icon/";

    // 七牛图片服务广告 前缀
    public static final String AD_PREFIX = "ad/";

    // 七牛图片服务书单广告 前缀
    public static final String CHANNEL_AD_PREFIX = "channelAd/";

    // 七牛图片服务首页轮播图 前缀
    public static final String POSTER_TEMPLATE_PREFIX = "poster/bg/";

    @Value("${qiniu.AK}")
    private String qiniuAk;

    @Value("${qiniu.SK}")
    private String qiniuSK;

    @Value("${qiniu.bucket}")
    private String qiniuBucket;

    @Value("${qiniu.CNAME}")
    private String qiniuCName;

    @Value("${qiniu.image.jpg}")
    private String qiniuImageJpg;

    @Value("${qiniu.image.png}")
    private String qiniuImagePng;

    @Value("${qiniu.image.gif}")
    private String qiniuImageGif;

    @PostConstruct
    public void init() {
        /////////////////////// 指定上传的Zone的信息//////////////////
        // 第一种方式: 指定具体的要上传的zone
        // 注：该具体指定的方式和以下自动识别的方式选择其一即可
        // 要上传的空间(bucket)的存储区域为华东时
//        Zone z = Zone.zone0();
        // 要上传的空间(bucket)的存储区域为华北时
        Zone z = Zone.zone1();
        // 要上传的空间(bucket)的存储区域为华南时
        // Zone z = Zone.zone2();

        // 第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
        // Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        uploadManager = new UploadManager(c);

        Auth auth = Auth.create(qiniuAk, qiniuSK);
        bucketManager = new BucketManager(auth, c);
    }

    public String getQnEventImageToken() {
        // 如果缓存中的七牛token不为空，并且生成时间小于24小时，表示该TOKEN是有效的，否则就重新去七牛服务端生成一个
        String token = redisCacheService.getValueByString(RedisCacheConstant.QnEventImageToken);
        if (StringUtils.isEmpty(token)) {
            // 如果为空，表示token有效期已经
            Auth auth = Auth.create(qiniuAk, qiniuSK);
            token = auth.uploadToken(qiniuBucket, null, OcsConstant.SECONDS_OF_ONE_DAY, null, true);
            // 将获取的token保存到缓存中，并且设置有效期24小时
            redisCacheService.setValueByString(RedisCacheConstant.QnEventImageToken, token);
            redisCacheService.setExpireByKey(RedisCacheConstant.QnEventImageToken, OcsConstant.SECONDS_OF_ONE_DAY);
        }
        return token;
    }

    /**
     * 上传图片七牛方法
     *
     * @param uploadBytes
     * @param name
     * @param token
     * @return
     * @throws Exception
     */
    public Response appuploadQn(byte[] uploadBytes, String name, String token) throws Exception {
        return uploadManager.put(uploadBytes, name, token);
    }

    /**
     * 上传图书封面到七牛
     *
     * @throws Exception
     */
    public String uploadBookCover(MultipartFile imageFile) throws Exception {
        String originalFileName = imageFile.getOriginalFilename();

        StringBuilder urlPath = new StringBuilder().append(BOOK_COVER_PREFIX)
                .append(originalFileName);
        String token = this.getQnEventImageToken();
        Response res = appuploadQn(imageFile.getBytes(), urlPath.toString(), token);

        return qiniuCName + res.jsonToMap().get("key");
    }

    /**
     * 上传图书头图到七牛
     *
     * @throws Exception
     */
    public String uploadBookBanner(MultipartFile imageFile) throws Exception {
        String originalFileName = imageFile.getOriginalFilename();

        StringBuilder urlPath = new StringBuilder().append(BOOK_BANNER_PREFIX)
                .append(originalFileName);
        String token = this.getQnEventImageToken();
        Response res = appuploadQn(imageFile.getBytes(), urlPath.toString(), token);

        return qiniuCName + res.jsonToMap().get("key");
    }

    /**
     * 上传头像图片到七牛
     *
     * @param imageFile
     * @return
     * @throws Exception
     */
    public String uploadAvatar(MultipartFile imageFile) throws Exception {
        String originalFileName = imageFile.getOriginalFilename();

        StringBuilder urlPath = new StringBuilder().append(AVATAR_PREFIX)
                .append(originalFileName);
        String token = this.getQnEventImageToken();
        Response res = appuploadQn(imageFile.getBytes(), urlPath.toString(), token);

        return qiniuCName + res.jsonToMap().get("key");
    }

    /**
     * 上传首页轮播图
     *
     * @param file
     * @return
     */
    public String uploadBanner(MultipartFile file) throws Exception {
        String originalFileName = file.getOriginalFilename();

        StringBuilder urlPath = new StringBuilder().append(HOME_BANNER_PREFIX)
                .append(originalFileName);
        String token = this.getQnEventImageToken();
        Response res = appuploadQn(file.getBytes(), urlPath.toString(), token);

        return qiniuCName + res.jsonToMap().get("key");
    }

    /**
     * 上传海报模板图片
     *
     * @param file
     * @return
     */
    public String uploadPosterTemplate(MultipartFile file) throws Exception {
        String originalFileName = file.getOriginalFilename();

        StringBuilder urlPath = new StringBuilder().append(POSTER_TEMPLATE_PREFIX)
                .append(originalFileName);
        String token = this.getQnEventImageToken();
        Response res = appuploadQn(file.getBytes(), urlPath.toString(), token);

        return qiniuCName + res.jsonToMap().get("key");
    }

    public String uploadChannelImage(MultipartFile file) throws Exception {
        String originalFileName = file.getOriginalFilename();

        StringBuilder urlPath = new StringBuilder().append(HOME_CHANNEL_PREFIX)
                .append(originalFileName);
        String token = this.getQnEventImageToken();
        Response res = appuploadQn(file.getBytes(), urlPath.toString(), token);

        return qiniuCName + res.jsonToMap().get("key");
    }

    public String uploadIcon(MultipartFile file) throws Exception {
        String originalFileName = file.getOriginalFilename();

        StringBuilder urlPath = new StringBuilder().append(ICON_PREFIX)
                .append(originalFileName);
        String token = this.getQnEventImageToken();
        Response res = appuploadQn(file.getBytes(), urlPath.toString(), token);

        return qiniuCName + res.jsonToMap().get("key");
    }

    public String uploadAd(MultipartFile file) throws Exception {
        String originalFileName = file.getOriginalFilename();

        StringBuilder urlPath = new StringBuilder().append(AD_PREFIX)
                .append(originalFileName);
        String token = this.getQnEventImageToken();
        Response res = appuploadQn(file.getBytes(), urlPath.toString(), token);

        return qiniuCName + res.jsonToMap().get("key");
    }

    public String uploadChannelAd(MultipartFile file) throws Exception {
        String originalFileName = file.getOriginalFilename();

        StringBuilder urlPath = new StringBuilder().append(CHANNEL_AD_PREFIX)
                .append(originalFileName);
        String token = this.getQnEventImageToken();
        Response res = appuploadQn(file.getBytes(), urlPath.toString(), token);

        return qiniuCName + res.jsonToMap().get("key");
    }

    /**
     * 删除七牛图片
     *
     * @throws Exception
     */
    public Response deleteByName(String coverName) throws Exception {

        return bucketManager.delete(qiniuBucket, coverName);

    }

    /**
     * ueditor编辑器图片上传的方法，保存到文件服务器上之后返回指定格式的图片文件描述的Map对象
     *
     * @param file
     * @return
     */
    public Map<String, Object> ueditorImageUpload(MultipartFile file) throws Exception {
        Map<String, Object> returnMap = new HashMap<>();
        String extName = file.getContentType().replace("image/", StringUtils.EMPTY);
        StringBuilder urlPath = new StringBuilder().append(BOOK_DETAIL_PREFIX)
                .append(DateFormatUtils.format(new Date(), "yyyy-MM-dd")).append("/").append(IdGenerator.uuid2());
        String token = this.getQnEventImageToken();
        Response response = appuploadQn(file.getBytes(), urlPath.toString(), token);
        String res = qiniuCName + response.jsonToMap().get("key");
        StringBuilder imageUrl = new StringBuilder(res);
        // 判断图片类型拼接不同的url
        if ("jpeg".equalsIgnoreCase(extName)) {
            imageUrl.append(qiniuImageJpg);
        } else if ("png".equalsIgnoreCase(extName)) {
            imageUrl.append(qiniuImagePng);
        } else if ("gif".equalsIgnoreCase(extName)) {
            imageUrl.append(qiniuImageGif);
        }
        returnMap.put("url", imageUrl);
        returnMap.put("state", "SUCCESS");
        return returnMap;
    }

    /**
     * 上传图书预览图
     *
     * @param file
     * @return
     */
    public String uploadPageImage(MultipartFile file) throws Exception {
        String originalFileName = file.getOriginalFilename();

        StringBuilder urlPath = new StringBuilder().append(BOOK_PAGE_IMAGE_PREFIX)
                .append(originalFileName);
        String token = this.getQnEventImageToken();
        Response res = appuploadQn(file.getBytes(), urlPath.toString(), token);

        return qiniuCName + res.jsonToMap().get("key");
    }

    /**
     * 通过url上传图片到七牛
     *
     * @param url  url
     * @param name 文件名
     * @return
     * @throws Exception
     */
    public String uploadByUrl(String url, String name) throws Exception {
        InputStream inputStream = ImageUtil.returnBitMap(url);

        byte[] bytes = ImageUtil.input2byte(inputStream);

        String token = this.getQnEventImageToken();
        Response res = appuploadQn(bytes, name, token);
        return qiniuCName + res.jsonToMap().get("key");
    }
}
