package com.common.util;

import java.util.Arrays;

/**
 * 验证码生成工具
 * 
 * @author: HuiJunLuo
 * @date:2016年1月8日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class CodeUtil {

    private static CodeUtil codeUtil = null;

    public synchronized static CodeUtil getInstance() {
        if (codeUtil == null) {
            codeUtil = new CodeUtil();
        }
        return codeUtil;
    }

    private CodeUtil() {
    }

    /**
     * 验证码难度级别，Simple只包含数字，Medium包含数字和小写英文，Hard包含数字和大小写英文
     */
    public enum SecurityCodeLevel {
        Simple, Medium, Hard
    };

    private final char[] CHAR_CODE = { '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    /**
     * 产生默认验证码，4位中等难度<br>
     * 调用此方法和调用getSecurityCode(4, SecurityCodeLevel.Medium, false)有一样的行为
     *
     * @see #getSecurityCode(int, SecurityCodeLevel, boolean)
     * @return 验证码
     */
    public String getSecurityCode(int len) {
        return String.valueOf(getSecurityCode(len, SecurityCodeLevel.Hard, false));
    }

    /**
     * 获取验证码，指定长度、难度、是否允许重复字符
     *
     * @param length
     *            长度
     * @param level
     *            难度
     * @param isCanRepeat
     *            是否允许重复字符
     * @return 验证码
     */
    public char[] getSecurityCode(int length, SecurityCodeLevel level, boolean isCanRepeat) {
        int len = length;
        char[] code;

        switch (level) {
        case Simple: {
            code = Arrays.copyOfRange(CHAR_CODE, 0, 9);
            break;
        }
        case Medium: {
            code = Arrays.copyOfRange(CHAR_CODE, 0, 33);
            break;
        }
        case Hard: {
            code = Arrays.copyOfRange(CHAR_CODE, 0, CHAR_CODE.length);
            break;
        }
        default: {
            code = Arrays.copyOfRange(CHAR_CODE, 0, CHAR_CODE.length);
        }
        }

        int n = code.length;

        if (len > n && isCanRepeat == false) {
            throw new RuntimeException(String.format(
                    "调用SecurityCode.getSecurityCode(%1$s,%2$s,%3$s)出现异常，" + "当isCanRepeat为%3$s时，传入参数%1$s不能大于%4$s", len,
                    level, isCanRepeat, n));
        }
        char[] result = new char[len];
        if (isCanRepeat) {
            for (int i = 0; i < result.length; i++) {
                int r = (int) (Math.random() * n);

                result[i] = code[r];
            }
        } else {
            for (int i = 0; i < result.length; i++) {
                int r = (int) (Math.random() * n);

                result[i] = code[r];

                code[r] = code[n - 1];
                n--;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(CodeUtil.getInstance().getSecurityCode(4));
    }
}
