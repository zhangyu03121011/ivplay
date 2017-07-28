package com.common.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import com.common.base.constant.PrivilegeConstant;

/**
 * PBE加密解密
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class PBEUtil {

    private static PBEUtil pbeUtil = null;

    public static final String ALGORITHM = "PBEWITHMD5andDES";

    // 迭代次数
    public static final int ITERATION_COUNT = 100;

    public synchronized static PBEUtil getInstance() {
        if (pbeUtil == null) {
            pbeUtil = new PBEUtil();
        }
        return pbeUtil;
    }

    private PBEUtil() {
    }

    /**
     * 制作8位盐
     * 
     * @return
     */
    private static byte[] makeSaltAry() {
        // 实例化安全随机数
        SecureRandom random = new SecureRandom();
        // 生产盐
        return random.generateSeed(8);
    }

    /**
     * 获取密码为password的pbe密钥
     * 
     * @param password
     * @return
     * @throws Exception
     */
    private static SecretKey getPBESecretKey(String password) throws Exception {
        // 制作secretKey
        PBEKeySpec pbeSpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory seckeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretkey = seckeyFactory.generateSecret(pbeSpec);
        return secretkey;
    }

    /**
     * 加密
     * 
     * @param data
     *            数据
     * @param password
     *            密码
     * @param salt
     *            盐
     * @return byte[] 加密数据
     * @throws Exception
     */
    public byte[] encrypt(byte[] data, String password, byte[] salt) throws Exception {
        // 1.根据盐制作pbe计算规则 ： 盐+int数字 ---此加密算法硬性规定
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATION_COUNT);

        // 2.制作密钥
        SecretKey secretkey = getPBESecretKey(password);

        // 3.制作加密解密器
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        // 4.告知加密解密器的操作 -- 加密，密钥，算法
        cipher.init(Cipher.ENCRYPT_MODE, secretkey, parameterSpec);

        // 5.加密器开始工作——加密/解密
        return cipher.doFinal(data);
    }

    /**
     * 解密
     * 
     * @param data
     *            数据
     * @param password
     *            密码
     * @param salt
     *            随机算法盐
     * @return
     * @throws Exception
     */
    public byte[] decrypt(byte[] data, String password, byte[] salt) throws Exception {
        // 1.根据盐制作pbe计算规则 ： 盐+int数字 ---此加密算法硬性规定
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt, ITERATION_COUNT);

        // 2.制作密钥
        SecretKey secretkey = getPBESecretKey(password);

        // 3.制作加密解密器
        Cipher cipher = Cipher.getInstance(ALGORITHM);

        // 4.告知加密解密器的操作 -- 加密，密钥，算法
        cipher.init(Cipher.DECRYPT_MODE, secretkey, parameterSpec);

        // 5.加密器开始工作——加密/解密
        return cipher.doFinal(data);
    }

    public static void main(String args[]) {

        String str = "xxx01001100xxWe9agQL5FR";
       // str="0b484078f0a94e841a42d1ce06a3300e";
        System.out.println("原文 ：" + str);
        String password = PrivilegeConstant.PBE_PASSWORD_KEY;
        System.out.println("密码 ：" + password);

        byte salt[] = makeSaltAry();
        System.out.println(Base64Util.getInstance().encodeByte(salt));
        try {
            byte[] change1 = getInstance().encrypt(str.getBytes(), password, "aaaaaaaa".getBytes());

            String str1 = Base64Util.getInstance().encodeByte(change1);
            System.out.println("加密后：" + str1);

            byte[] change2 = getInstance().decrypt(Base64Util.getInstance().decodeByte(str1), password, "aaaaaaaa".getBytes());
            System.out.println("解密后 ：" + new String(change2));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
