package com.common.util.gif;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.common.util.gif.Scalr.Mode;

/**
 * @Title: GifTest.java
 * @Package test
 * @author Administrator
 * @date 2012-7-12 下午1:44:25
 * @version V1.0
 */
public class GifTest {
	/**
	 * @Title: main
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author Administrator
	 * @param @param
	 *            args 设定文件
	 * @return void 返回类型
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		GifDecoder gd = new GifDecoder();
		int status = gd.read(new FileInputStream(new File("D:/aa.gif")));
		if (status != GifDecoder.STATUS_OK) {
			return;
		}
		//

		AnimatedGifEncoder ge = new AnimatedGifEncoder();
		ge.start(new FileOutputStream(new File("D:/aa_1.gif")));
		ge.setRepeat(0);

		for (int i = 0; i < gd.getFrameCount(); i++) {
			BufferedImage frame = gd.getFrame(i);
			int width = frame.getWidth();
			int height = frame.getHeight();
			// 80%
			width = (int) (width * 1);
			height = (int) (height * 1);
			//
			BufferedImage rescaled = Scalr.resize(frame, Mode.FIT_EXACT, 360, 360);
			//
			int delay = gd.getDelay(i);
			//

			ge.setDelay(delay);
			ge.addFrame(rescaled);
		}

		ge.finish();
		//
	}

}
