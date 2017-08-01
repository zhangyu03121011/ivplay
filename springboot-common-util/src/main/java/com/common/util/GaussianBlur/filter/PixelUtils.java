package com.common.util.GaussianBlur.filter;

import java.awt.Color;
import java.util.Random;

public class PixelUtils {
	
	public static final int REPLACE = 0;
	public static final int NORMAL = 1;
	public static final int MIN = 2;
	public static final int MAX = 3;
	public static final int ADD = 4;
	public static final int SUBTRACT = 5;
	public static final int DIFFERENCE = 6;
	public static final int MULTIPLY = 7;
	public static final int HUE = 8;
	public static final int SATURATION = 9;
	public static final int VALUE = 10;
	public static final int COLOR = 11;
	public static final int SCREEN = 12;
	public static final int AVERAGE = 13;
	public static final int OVERLAY = 14;
	public static final int CLEAR = 15;
	public static final int EXCHANGE = 16;
	public static final int DISSOLVE = 17;
	public static final int DST_IN = 18;
	public static final int ALPHA = 19;
	public static final int ALPHA_TO_GRAY = 20;
	private static Random randomGenerator = new Random();
	private static final float hsb1[] = new float[3];
	private static final float hsb2[] = new float[3];
	
	public PixelUtils() {
	}

	public static int clamp(int c) {
		if (c < 0)
			return 0;
		if (c > 255)
			return 255;
		else
			return c;
	}

	public static int interpolate(int v1, int v2, float f) {
		return clamp((int) ((float) v1 + f * (float) (v2 - v1)));
	}

	public static int brightness(int rgb) {
		int r = rgb >> 16 & 255;
		int g = rgb >> 8 & 255;
		int b = rgb & 255;
		return (r + g + b) / 3;
	}

	public static boolean nearColors(int rgb1, int rgb2, int tolerance) {
		int r1 = rgb1 >> 16 & 255;
		int g1 = rgb1 >> 8 & 255;
		int b1 = rgb1 & 255;
		int r2 = rgb2 >> 16 & 255;
		int g2 = rgb2 >> 8 & 255;
		int b2 = rgb2 & 255;
		return Math.abs(r1 - r2) <= tolerance && Math.abs(g1 - g2) <= tolerance
				&& Math.abs(b1 - b2) <= tolerance;
	}

	public static int combinePixels(int rgb1, int rgb2, int op) {
		return combinePixels(rgb1, rgb2, op, 255);
	}

	public static int combinePixels(int rgb1, int rgb2, int op, int extraAlpha,
			int channelMask) {
		return rgb2 & ~channelMask
				| combinePixels(rgb1 & channelMask, rgb2, op, extraAlpha);
	}

	public static int combinePixels(int rgb1, int rgb2, int op, int extraAlpha) {
		if (op == 0)
			return rgb1;
		int a1 = rgb1 >> 24 & 255;
		int r1 = rgb1 >> 16 & 255;
		int g1 = rgb1 >> 8 & 255;
		int b1 = rgb1 & 255;
		int a2 = rgb2 >> 24 & 255;
		int r2 = rgb2 >> 16 & 255;
		int g2 = rgb2 >> 8 & 255;
		int b2 = rgb2 & 255;
		switch (op) {
		case 2: // '\002'
			r1 = Math.min(r1, r2);
			g1 = Math.min(g1, g2);
			b1 = Math.min(b1, b2);
			break;

		case 3: // '\003'
			r1 = Math.max(r1, r2);
			g1 = Math.max(g1, g2);
			b1 = Math.max(b1, b2);
			break;

		case 4: // '\004'
			r1 = clamp(r1 + r2);
			g1 = clamp(g1 + g2);
			b1 = clamp(b1 + b2);
			break;

		case 5: // '\005'
			r1 = clamp(r2 - r1);
			g1 = clamp(g2 - g1);
			b1 = clamp(b2 - b1);
			break;

		case 6: // '\006'
			r1 = clamp(Math.abs(r1 - r2));
			g1 = clamp(Math.abs(g1 - g2));
			b1 = clamp(Math.abs(b1 - b2));
			break;

		case 7: // '\007'
			r1 = clamp((r1 * r2) / 255);
			g1 = clamp((g1 * g2) / 255);
			b1 = clamp((b1 * b2) / 255);
			break;

		case 17: // '\021'
			if ((randomGenerator.nextInt() & 255) <= a1) {
				r1 = r2;
				g1 = g2;
				b1 = b2;
			}
			break;

		case 13: // '\r'
			r1 = (r1 + r2) / 2;
			g1 = (g1 + g2) / 2;
			b1 = (b1 + b2) / 2;
			break;

		case 8: // '\b'
		case 9: // '\t'
		case 10: // '\n'
		case 11: // '\013'
			Color.RGBtoHSB(r1, g1, b1, hsb1);
			Color.RGBtoHSB(r2, g2, b2, hsb2);
			switch (op) {
			case 8: // '\b'
				hsb2[0] = hsb1[0];
				break;

			case 9: // '\t'
				hsb2[1] = hsb1[1];
				break;

			case 10: // '\n'
				hsb2[2] = hsb1[2];
				break;

			case 11: // '\013'
				hsb2[0] = hsb1[0];
				hsb2[1] = hsb1[1];
				break;
			}
			rgb1 = Color.HSBtoRGB(hsb2[0], hsb2[1], hsb2[2]);
			r1 = rgb1 >> 16 & 255;
			g1 = rgb1 >> 8 & 255;
			b1 = rgb1 & 255;
			break;

		case 12: // '\f'
			r1 = 255 - ((255 - r1) * (255 - r2)) / 255;
			g1 = 255 - ((255 - g1) * (255 - g2)) / 255;
			b1 = 255 - ((255 - b1) * (255 - b2)) / 255;
			break;

		case 14: // '\016'
			int s = 255 - ((255 - r1) * (255 - r2)) / 255;
			int m = (r1 * r2) / 255;
			r1 = (s * r1 + m * (255 - r1)) / 255;
			s = 255 - ((255 - g1) * (255 - g2)) / 255;
			m = (g1 * g2) / 255;
			g1 = (s * g1 + m * (255 - g1)) / 255;
			s = 255 - ((255 - b1) * (255 - b2)) / 255;
			m = (b1 * b2) / 255;
			b1 = (s * b1 + m * (255 - b1)) / 255;
			break;

		case 15: // '\017'
			r1 = g1 = b1 = 255;
			break;

		case 18: // '\022'
			r1 = clamp((r2 * a1) / 255);
			g1 = clamp((g2 * a1) / 255);
			b1 = clamp((b2 * a1) / 255);
			a1 = clamp((a2 * a1) / 255);
			return a1 << 24 | r1 << 16 | g1 << 8 | b1;

		case 19: // '\023'
			a1 = (a1 * a2) / 255;
			return a1 << 24 | r2 << 16 | g2 << 8 | b2;

		case 20: // '\024'
			int na = 255 - a1;
			return a1 << 24 | na << 16 | na << 8 | na;
		}
		if (extraAlpha != 255 || a1 != 255) {
			a1 = (a1 * extraAlpha) / 255;
			int a3 = ((255 - a1) * a2) / 255;
			r1 = clamp((r1 * a1 + r2 * a3) / 255);
			g1 = clamp((g1 * a1 + g2 * a3) / 255);
			b1 = clamp((b1 * a1 + b2 * a3) / 255);
			a1 = clamp(a1 + a3);
		}
		return a1 << 24 | r1 << 16 | g1 << 8 | b1;
	}
}
