package com.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.common.base.common.BaseLogger;

/**
 * 视频工具类
 * 
 * @author: HuiJunLuo
 * @date:2015年12月28日
 * @Copyright:Copyright (c) xxxx有限公司 2014 ~ 2015 版权所有
 */
public class VideoUtil extends BaseLogger {

	private static VideoUtil videoUtil = null;

	public synchronized static VideoUtil getInstance() {
		if (videoUtil == null) {
			videoUtil = new VideoUtil();
		}
		return videoUtil;
	}

	private VideoUtil() {
	}

	public static void main(String[] args) {

		// String fromFilePath1 = basePath + "aa.wmv";
		// getInstance().formatConvert(fromFilePath1, "flv");

		// String fromFilePath2 = basePath + "aa.wmv";
		// getInstance().formatConvert(fromFilePath2, "flv");

		String str = "a/b/c.m";
		System.out.println(str.substring(0, str.lastIndexOf(".") + 1) + "avi");

	}

	/**
	 * 视频格式转换
	 * 
	 * @param filePath
	 * @param suffix
	 */
	public boolean formatConvert(String sourceVideoPath, String targetVideoPath, int width, int heigh) {
		boolean result = false;
		if (!checkInput(sourceVideoPath)) {
			logger.error("文件" + sourceVideoPath + "不存在");
		} else {
			result = process(sourceVideoPath, targetVideoPath, width, heigh);
			if (result) {
				logger.info("转换成功");
			} else {
				logger.error("转换失败");
			}
		}
		return result;
	}

	/**
	 * 进行转换
	 * 
	 * @return
	 */
	private boolean process(String sourceVideoPath, String targetVideoPath, int width, int heigh) {
		boolean status = false;
		int type = checkContentType(sourceVideoPath);
		if (type == 0) {
			status = convertDiscern(sourceVideoPath, targetVideoPath, width, heigh);
		} else if (type == 1) {

			targetVideoPath = targetVideoPath.trim();
			String tmpTargetVideoPath = (targetVideoPath.substring(0, targetVideoPath.lastIndexOf(".") + 1) + "avi");

			String aviFilePath = convertNotDiscern(sourceVideoPath, tmpTargetVideoPath);
			if (aviFilePath == null) {
				return false;
			}
			status = convertDiscern(aviFilePath, targetVideoPath, width, heigh);
		}
		return status;
	}

	/**
	 * 判断源视频的种类（主流格式 or 奇葩格式）
	 */
	private int checkContentType(String fromFilePath) {
		String type = fromFilePath.substring(fromFilePath.lastIndexOf(".") + 1, fromFilePath.length()).toLowerCase();
		// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
		if (type.equals("avi")) {
			return 0;
		} else if (type.equals("mpg")) {
			return 0;
		} else if (type.equals("wmv")) {
			return 0;
		} else if (type.equals("3gp")) {
			return 0;
		} else if (type.equals("mov")) {
			return 0;
		} else if (type.equals("mp4")) {
			return 0;
		} else if (type.equals("asf")) {
			return 0;
		} else if (type.equals("asx")) {
			return 0;
		} else if (type.equals("flv")) {
			return 0;
		}
		// 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
		// 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
		else if (type.equals("wmv9")) {
			return 1;
		} else if (type.equals("rm")) {
			return 1;
		} else if (type.equals("rmvb")) {
			return 1;
		}
		return 9;
	}

	/**
	 * 判断源视频的种类（主流格式 or 奇葩格式）
	 */
	public boolean checkType(String filePath) {
		if (checkContentType(filePath) == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 检查指定的输入文件是否存在
	 */
	private boolean checkInput(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		}
		return true;
	}

	/**
	 * 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
	 */
	private String convertNotDiscern(String sourceVideoPath, String targetVideoPath) {

		// 预处理指令
		List<String> commend = new ArrayList<String>();
		commend.add("mencoder");
		commend.add(sourceVideoPath);
		// commend.add("-oac lavc");
		commend.add("-oac"); // 输出后的音频格式
		commend.add("mp3lame");
		commend.add("-lameopts");
		commend.add("preset=64");
		commend.add("-lavcopts");// 指定视频编码的设置。由于 Libavcodec 包含了多种视频编码，所以用
									// vcodec=mpeg4 来指定具体的使用 MPEG-4 编码，vbitrate
									// 是设定视频编码的码率为 1200kbps。
		commend.add("vcodec=mp3:vbitrate=64");
		commend.add("-ovc"); // -ovc xvid ：视频编码格式XVID
		commend.add("xvid");
		commend.add("-xvidencopts");// -xvidencopts bitrate=500：视频编码率500Kbps
		commend.add("bitrate=600");
		commend.add("-of");
		commend.add("avi");
		commend.add("-o"); // 输出文件 （output ）
		commend.add(targetVideoPath);

		try {
			// 预处理进程
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commend);
			builder.redirectErrorStream(true);

			// 进程信息输出到控制台
			Process p = builder.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				logger.debug(line);
			}
			p.waitFor();// 直到上面的命令执行完，才向下执行

			return targetVideoPath;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public boolean convertSimpleDiscern(String sourceVideoPath, String targetVideoPath, int width, int heigh) {

		if (!checkInput(sourceVideoPath)) {
			logger.error(sourceVideoPath + "不存在");
			return false;
		}

		// 转换格式命令
		List<String> commend = new ArrayList<String>();
		commend.add("ffmpeg");
		commend.add("-i");
		commend.add(sourceVideoPath);
		commend.add(targetVideoPath);

		try {

			// 转换格式进程
			ProcessBuilder builder = new ProcessBuilder(commend);
			builder.command(commend);
			builder.redirectErrorStream(true);

			// 进程信息输出到控制台
			Process p = builder.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				logger.debug(line);
			}
			p.waitFor();// 直到上面的命令执行完，才向下执行

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	 * 
	 * @param sourceVideoPath
	 * @param targetVideoPath
	 * @return
	 */
	private boolean convertDiscern(String sourceVideoPath, String targetVideoPath, int width, int heigh) {

		if (!checkInput(sourceVideoPath)) {
			logger.error(sourceVideoPath + "不存在");
			return false;
		}

		// 转换格式命令
		List<String> commend = new ArrayList<String>();
		commend.add("ffmpeg");
		commend.add("-i");
		commend.add(sourceVideoPath);
		commend.add("-ab"); // -ab 选项改变音频的比特率(bitrate)
		commend.add("56");
		commend.add("-ar"); // -ar 设定采样率。
		commend.add("22050");
		commend.add("-qscale");// -qscale 8 设定画面质量，值 越小越好
		commend.add("8");
		commend.add("-r");// -r 10 好像是设置频率
		commend.add("15");
		commend.add("-y");
		if (width > 0 && heigh > 0) {
			commend.add("-s");
			commend.add(width + "x" + heigh);
		}
		commend.add(targetVideoPath);

		try {

			// 转换格式进程
			ProcessBuilder builder = new ProcessBuilder(commend);
			builder.command(commend);
			builder.redirectErrorStream(true);

			// 进程信息输出到控制台
			Process p = builder.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				logger.debug(line);
			}
			p.waitFor();// 直到上面的命令执行完，才向下执行

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 截图
	 * 
	 * @param videoPath
	 * @param imagePath
	 * @param width
	 * @param cutTime
	 * @param heigh
	 * @return
	 */
	public boolean cutVideoImage(String videoPath, String imagePath, int cutTime, int width, int heigh) {

		if (!checkInput(videoPath)) {
			logger.error(videoPath + "不存在");
			return false;
		}

		// 截图
		List<String> commend = new ArrayList<String>();
		commend.add("ffmpeg");
		commend.add("-i");
		commend.add(videoPath);
		commend.add("-y");
		commend.add("-f");
		commend.add("image2");
		commend.add("-ss");
		if (cutTime > 0) {
			commend.add(String.valueOf(cutTime));
		} else {
			commend.add("8");
		}
		commend.add("-t");
		commend.add("0.001");
		if (width > 0 && heigh > 0) {
			commend.add("-s");
			commend.add(width + "x" + heigh);
		}
		commend.add(imagePath);

		try {

			// 转换格式进程
			ProcessBuilder builder = new ProcessBuilder(commend);
			builder.command(commend);
			builder.redirectErrorStream(true);

			// 进程信息输出到控制台
			Process p = builder.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				logger.debug(line);
			}
			p.waitFor();// 直到上面的命令执行完，才向下执行

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

}
