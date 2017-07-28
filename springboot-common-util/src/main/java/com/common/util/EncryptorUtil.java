package com.common.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptorUtil {

    public static final String ALGORITHM_DES = "DES";

    public static final String ALGORITHM_THREEDES = "DESede";

    public static final String ALGORITHM_AES = "AES";

    public static final String ALGORITHM_BLOWFISH = "Blowfish";

    private String algorithm;

    private Key key;

    private int blocksize;

    /**
     * 解密
     */
    public byte[] decrypt(byte[] ivAndCiphertext) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] iv = new byte[this.blocksize];
        byte[] c = new byte[ivAndCiphertext.length - this.blocksize];
        System.arraycopy(ivAndCiphertext, 0, iv, 0, this.blocksize);
        System.arraycopy(ivAndCiphertext, this.blocksize, c, 0, ivAndCiphertext.length - this.blocksize);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(algorithm + "/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        return cipher.doFinal(c);
    }

    /**
     * 加密
     */
    public byte[] encrypt(byte[] iv, byte[] plaintext) throws InvalidKeyException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance(algorithm + "/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        this.blocksize = cipher.getBlockSize();
        byte[] c = cipher.doFinal(plaintext);
        byte[] ivAndCiphertext = new byte[c.length + this.blocksize];
        System.arraycopy(iv, 0, ivAndCiphertext, 0, this.blocksize);
        System.arraycopy(c, 0, ivAndCiphertext, this.blocksize, c.length);
        return ivAndCiphertext;
    }

    public Key generateKey(byte[] key) throws Exception {
        if (key == null) {// 随机生成密钥
            KeyGenerator keygen = KeyGenerator.getInstance(algorithm);
            SecureRandom random = new SecureRandom();
            keygen.init(random);
            return keygen.generateKey();
        } else {// 从一组固定的原始数据（也许是由口令或者随机键产生的）生成密钥
            // SecretKeyFactory keyFactory =
            // SecretKeyFactory.getInstance(algorithm);
            // SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
            // return keyFactory.generateSecret(keySpec);
            Key secretKey = new SecretKeySpec(key, algorithm);
            return secretKey;
        }
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    // 将字符转化为字节

    public static byte[] charToByte(char ch) {
        int temp = (int) ch;
        byte[] b = new byte[2];
        // 将高8位放在b[0],将低8位放在b[1]
        for (int i = 1; i > -1; i--) {
            b[i] = (byte) (temp & 0xFF);// 低8位
            // 向右移8位
            temp >>= 8;
        }
        return b;
    }

    // 将字节转化为比特数组
    public static byte[] byteToBitArray(byte b) {
        // 强制转换成int?
        int temp = (int) b;
        byte[] result = new byte[8];
        for (int i = 7; i > -1; i--) {
            result[i] = (byte) (temp & 0x01);
            temp >>= 1;
        }
        return result;
    }

    // 将二维比特数组转化为字节
    public static byte bitToByteArray(byte[] b) {
        byte result;
        result = (byte) (b[7] | b[6] << 1 | b[5] << 2 | b[4] << 3 | b[3] << 4 | b[2] << 5 | b[1] << 6 | b[0] << 7);
        return result;
    }

    // 将字节转化为字符
    public static char byteToChar(byte[] b) {
        int s = 0;
        if (b[0] > 0) {
            s += b[0];
        }
        if (b[0] < 0) {
            s += 256 + b[0];
        }
        s *= 256;
        if (b[1] > 0) {
            s += b[1];
        }
        if (b[1] < 0) {
            s += 256 + b[1];
        }
        char ch = (char) s;
        return ch;
    }

    public static String byteToHexString(byte b) {
        String hex = "";
        hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex;
    }

    public static String bytesToHexString(byte[] bs) {
        StringBuffer sb = new StringBuffer();
        String hex = "";
        for (int i = 0; i < bs.length; i++) {
            hex = Integer.toHexString(bs[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static byte[] hexStringToBytes(String in) {
        byte[] arrB = in.getBytes();
        int iLen = arrB.length;
        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public String encrypt(String type, String str) throws Exception {
        EncryptorUtil e = new EncryptorUtil();
        e.setAlgorithm(type);
        Key key = e.generateKey(null);
        e.setKey(key);

        String iv = "000a0a0a0a0202aa";
        String ivAndCiphertext = bytesToHexString(e.encrypt(hexStringToBytes(iv), str.getBytes()));
        return ivAndCiphertext;
    }

    public String decrypt(String type, String str) throws Exception {
        EncryptorUtil e = new EncryptorUtil();
        e.setAlgorithm(type);
        Key key = e.generateKey(null);
        e.setKey(key);
        byte[] decrypt = e.decrypt(hexStringToBytes(str));
        return new String(decrypt);
    }

    public static void main(String[] args) throws Exception {
        EncryptorUtil e = new EncryptorUtil();

        String str = "12345678";
        str = e.encrypt(ALGORITHM_THREEDES, str);
        System.out.println(str);

        System.out.println(e.decrypt(ALGORITHM_THREEDES, str));
        // e.testDESede();

    }

    public void testDESede() throws Exception {
        EncryptorUtil e = new EncryptorUtil();
        e.setAlgorithm(EncryptorUtil.ALGORITHM_THREEDES);
        Key key = e.generateKey(null);
        System.out.println("密钥为(hex)：" + bytesToHexString(key.getEncoded()));
        e.setKey(key);

        String plaintext = "jlkasffdspfk阿斯达jlkasffdspfk阿斯达jlkasffdspfk阿斯达jlkasffdspfk阿斯达jlkasffdspfk阿斯达jlkasffdspfk阿斯达";
        System.out.println("明文为：\n" + plaintext);
        System.out.println("明文(hex)为：\n" + bytesToHexString(plaintext.getBytes()));

        String iv = "000a0a0a0a0202aa";
        String ivAndCiphertext = bytesToHexString(e.encrypt(hexStringToBytes(iv), plaintext.getBytes()));
        System.out.println("加密后：\n" + ivAndCiphertext);

        byte[] decrypt = e.decrypt(hexStringToBytes(ivAndCiphertext));
        System.out.println("解密后(hex)：\n" + bytesToHexString(decrypt));

        System.out.println("解密后明文为：\n" + new String(decrypt));

        decrypt = e.decrypt(hexStringToBytes(ivAndCiphertext));
        System.out.println("解密后(hex)：\n" + bytesToHexString(decrypt));

        System.out.println("解密后明文为：\n" + new String(decrypt));
    }

    public void testAES() throws Exception {
        EncryptorUtil e = new EncryptorUtil();
        e.setAlgorithm(EncryptorUtil.ALGORITHM_AES);
        Key key = e.generateKey(null);
        System.out.println("密钥为(hex)：" + bytesToHexString(key.getEncoded()));
        e.setKey(key);

        String plaintext = "jlkasffdspfk阿斯达";
        System.out.println("明文为：\n" + plaintext);
        System.out.println("明文(hex)为：\n" + bytesToHexString(plaintext.getBytes()));
        String iv = "000a0a0a0a0202aa000a0a0a0a0202aa";
        String ivAndCiphertext = bytesToHexString(e.encrypt(hexStringToBytes(iv), plaintext.getBytes()));
        System.out.println("加密后：\n" + ivAndCiphertext);

        byte[] decrypt = e.decrypt(hexStringToBytes(ivAndCiphertext));
        System.out.println("解密后(hex)：\n" + bytesToHexString(decrypt));

        System.out.println("解密后明文为：\n" + new String(decrypt));

        decrypt = e.decrypt(hexStringToBytes(ivAndCiphertext));
        System.out.println("解密后(hex)：\n" + bytesToHexString(decrypt));

        System.out.println("解密后明文为：\n" + new String(decrypt));
    }
}
