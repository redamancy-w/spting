package icu.redamancy.redamancyauthenticationcenter.service.imlp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonObject;
import icu.redamancy.common.model.pojo.auth.EntityUser;
import icu.redamancy.common.model.dao.auth.mapper.UserMapper;
import icu.redamancy.redamancyauthenticationcenter.properties.ParameterCollection;
import icu.redamancy.redamancyauthenticationcenter.service.UserAuthService;
import icu.redamancy.redamancyauthenticationcenter.service.feign.auth.MyOauth2;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;


/**
 * @Author redamancy
 * @Date 2022/5/5 17:55
 * @Version 1.0
 */
@Service("wxUserAuthServiceImpl")
public class WxUserAuthServiceImpl implements UserAuthService {

    @Resource(name = "userMapper")
    private UserMapper userMapper;

    @Resource
    private MyOauth2 oauth2;

    @Override
    public String Login(JsonObject resJson) {

        String token = oauth2.auth(ParameterCollection.PASSWORD, resJson.get(ParameterCollection.OPENID).getAsString(), resJson.get(ParameterCollection.OPENID).getAsString(), ParameterCollection.ALL);

        assert Objects.equals(token, "") : "oauth接口访问失败";

//        String toekn = oauth2.test();

        return token;
    }

    @Override
    public boolean userIsExist(String onlyId) {
        long count = userMapper.selectCount(new QueryWrapper<EntityUser>()
                .eq("openid", onlyId));
        boolean userIsExist = count > 0;
        return userIsExist;
    }

    @Override
    public boolean signIn(JsonObject jsonObject, JsonObject resJson) {
        JsonObject userInfoJson = jsonObject.get(ParameterCollection.USERINFO).getAsJsonObject();
        String openid = resJson.get(ParameterCollection.OPENID).getAsString();

        EntityUser user = new EntityUser().setAvatarUrl(userInfoJson.get(ParameterCollection.AVATARURL).getAsString())
                .setNickname(userInfoJson.get(ParameterCollection.NICKNAME).getAsString())
                .setOpenid(openid);

        int count = userMapper.insert(user);

        return count > 0;
    }

    /**
     * 解密微信信息
     * 已经弃用，已无法获得用户的性别，地区等私密信息
     */
    private static class DeCodeProfile {

        private static final String KEY_ALGORITHM = "AES";
        private static final String ALGORITHM_STR = "AES/CBC/PKCS7Padding";
        private static Key key;
        private static Cipher cipher;

        private EntityUser decryptData(String encryptDataB64, String sessionKey, String ivB64) {

            String res = new String(
                    decryptOfDiyIV(
                            Base64.getDecoder().decode(encryptDataB64),
                            Base64.getDecoder().decode(sessionKey),
                            Base64.getDecoder().decode(ivB64)

                    ));

            System.out.println(res);

            return null;
        }


        byte[] decryptOfDiyIV(byte[] encryptDataB64, byte[] sessionKey, byte[] ivs) {
            byte[] encryptedText = null;
            init(sessionKey);
            try {
                cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivs));
                encryptedText = cipher.doFinal(encryptDataB64);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return encryptedText;
        }

        private void init(byte[] keyBytes) {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyBytes.length % base != 0) {
                int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
                keyBytes = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            // 转化成JAVA的密钥格式
            key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
            try {
                // 初始化cipher
                cipher = Cipher.getInstance(ALGORITHM_STR, "BC");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}

