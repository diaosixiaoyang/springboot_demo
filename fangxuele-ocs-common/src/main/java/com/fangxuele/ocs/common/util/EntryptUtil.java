package com.fangxuele.ocs.common.util;

import com.fangxuele.ocs.common.constant.SecurityConstant;
import org.springside.modules.utils.text.EncodeUtil;
import org.springside.modules.utils.text.HashUtil;

/**
 * @author rememberber(https://github.com/rememberber)
 */
public class EntryptUtil {

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    public static EncodePassWord entryptPassword(String passWord) {
        EncodePassWord encodePassWord = new EncodePassWord();
        byte[] salt = HashUtil.generateSalt(SecurityConstant.SALT_SIZE);
        encodePassWord.setSalt(EncodeUtil.encodeHex(salt));
        byte[] hashPassword = HashUtil.sha1(passWord.getBytes(), salt, SecurityConstant.INTERATIONS);
        encodePassWord.setEncodedPassword(EncodeUtil.encodeHex(hashPassword));
        return encodePassWord;
    }

    public static class EncodePassWord {
        private String salt;

        private String encodedPassword;

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getEncodedPassword() {
            return encodedPassword;
        }

        public void setEncodedPassword(String encodedPassword) {
            this.encodedPassword = encodedPassword;
        }
    }
}
