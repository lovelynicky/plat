package com.xiangzhu.plat.utils;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by liluoqi on 15/5/4.
 */
public class RSAUtils {

    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    private static Logger logger = LoggerFactory.getLogger(RSAUtils.class);
    private static final String CHAR_SET="UTF-8";

    //都是原始的字符串，不需要经过base64处理
    public static boolean verify(String content, String publicKeyString, String sign) {
        try {
            //解密公钥
            byte[] keyBytes = Base64.decodeBase64(publicKeyString);
//            byte[] keyBytes = publicKeyString.getBytes("utf-8");
            //构造X509EncodedKeySpec对象
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            //指定加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            //取公钥匙对象
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(content.getBytes(CHAR_SET));
            //验证签名是否正常
            return signature.verify(Base64.decodeBase64(sign));
        } catch (NoSuchAlgorithmException e) {
            logger.error(String.format("verify sign NoSuchAlgorithmException message : %s,stackTrace :%s happens",
                    e.getMessage(), e.getStackTrace()));
        } catch (UnsupportedEncodingException e) {
            logger.error(String.format("verify sign UnsupportedEncodingException message : %s,stackTrace :%s happens",
                    e.getMessage(), e.getStackTrace()));
        } catch (InvalidKeySpecException e) {
            logger.error(String.format("verify sign InvalidKeySpecException message : %s,stackTrace :%s happens",
                    e.getMessage(), e.getStackTrace()));
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            logger.error(String.format("verify sign InvalidKeyException message : %s,stackTrace :%s happens",
                    e.getMessage(), e.getStackTrace()));
            e.printStackTrace();
        } catch (SignatureException e) {
            logger.error(String.format("verify sign SignatureException message : %s,stackTrace :%s happens",
                    e.getMessage(), e.getStackTrace()));
            e.printStackTrace();
        }
        return false;
    }
}
