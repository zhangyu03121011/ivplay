package com.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.common.base.common.BaseLogger;

/**
 * IP工具类
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class IpUtil extends BaseLogger {

    private static IpUtil ipUtil = null;

    public synchronized static IpUtil getInstance() {
        if (ipUtil == null) {
            ipUtil = new IpUtil();
        }
        return ipUtil;
    }

    private IpUtil() {
    }

    /**
     * 获取实际用户的访问地址
     * 
     * @param request
     * @return
     */
    public String getIpAddr(HttpServletRequest request) {
        String clientIP = null;
        try {
            String ips = request.getHeader("x-forwarded-for");
            if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
                ips = request.getHeader("Proxy-Client-IP");
            }
            if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
                ips = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
                ips = request.getRemoteAddr();
            }

            String[] ipArray = ips.split(",");
            for (String ip : ipArray) {
                if (!("unknown".equalsIgnoreCase(ip))) {
                    clientIP = ip;
                    break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return clientIP;
    }

    /**
     * 获取当前操作系统名称. return 操作系统名称 例如:windows,Linux,Unix等.
     */
    public static String getOSName() {
        return System.getProperty("os.name").toLowerCase();
    }

    /**
     * 获取Unix网卡的mac地址.
     * 
     * @return mac地址
     */
    private static String getUnixMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            /**
             * Unix下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
             */
            process = Runtime.getRuntime().exec("ifconfig eth0");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                /**
                 * 寻找标示字符串[hwaddr]
                 */
                index = line.toLowerCase().indexOf("hwaddr");
                /**
                 * 找到了
                 */
                if (index != -1) {
                    /**
                     * 取出mac地址并去除2边空格
                     */
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    /**
     * 获取Linux网卡的mac地址.
     * 
     * @return mac地址
     */
    private static String getLinuxMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            /**
             * linux下的命令，一般取eth0作为本地主网卡 显示信息中包含有mac地址信息
             */
            process = Runtime.getRuntime().exec("ifconfig eth0");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                index = line.toLowerCase().indexOf("硬件地址");
                /**
                 * 找到了
                 */
                if (index != -1) {
                    /**
                     * 取出mac地址并去除2边空格
                     */
                    mac = line.substring(index + 4).trim();
                    break;
                }

                /**
                 * 寻找标示字符串[hwaddr]
                 */
                index = line.toLowerCase().indexOf("hwaddr");
                /**
                 * 找到了
                 */
                if (index != -1) {
                    /**
                     * 取出mac地址并去除2边空格
                     */
                    mac = line.substring(index + "hwaddr".length() + 1).trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    /**
     * 获取widnows网卡的mac地址.
     * 
     * @return mac地址
     */
    private static String getWindowsMACAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            /**
             * windows下的命令，显示信息中包含有mac地址信息
             */
            process = Runtime.getRuntime().exec("ipconfig /all");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                /**
                 * 寻找标示字符串[physical address]
                 */
                index = line.toLowerCase().indexOf("physical address");
                if (index != -1) {
                    index = line.indexOf(":");
                    if (index != -1) {
                        /**
                         * 取出mac地址并去除2边空格
                         */
                        mac = line.substring(index + 1).trim();
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }

        return mac;
    }

    /**
     * 获取Mac地址
     * 
     * @return @throws
     */
    public String getMacAddr() {
        String os = getOSName();
        String mac = null;
        if (os.startsWith("windows")) {
            mac = getWindowsMACAddress();
        } else if (os.startsWith("linux")) {
            mac = getLinuxMACAddress();
        } else {
            mac = getUnixMACAddress();
        }
        return mac;
    }

    /**
     * 测试用的main方法.
     * 
     * @param argc
     *            运行参数.
     */
    public static void main(String[] argc) {
        String os = getOSName();
        System.out.println(os);
        if (os.startsWith("windows")) {
            String mac = getWindowsMACAddress();
            System.out.println("本地是windows:" + mac);
        } else if (os.startsWith("linux")) {
            String mac = getLinuxMACAddress();
            System.out.println("本地是Linux系统,MAC地址是:" + mac);
        } else {
            String mac = getUnixMACAddress();
            System.out.println("本地是Unix系统 MAC地址是:" + mac);
        }

        try {
            // 获得ＩＰ
            NetworkInterface netInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            if (netInterface != null) {
                // 获得Mac地址的byte数组
                byte[] macAddr = netInterface.getHardwareAddress();
                System.out.print("MAC Addr:\t");
                // 循环输出
                for (byte b : macAddr) {
                    // 这里的toHexString()是自己写的格式化输出的方法，见下步。
                    System.out.print(toHexString(b) + " ");
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static String toHexString(int integer) {
        // 将得来的int类型数字转化为十六进制数
        String str = Integer.toHexString((int) (integer & 0xff));
        // 如果遇到单字符，前置0占位补满两格
        if (str.length() == 1) {
            str = "0" + str;
        }
        return str;
    }
}
