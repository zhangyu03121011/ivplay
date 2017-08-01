package com.common.util.GaussianBlur.filter;

public class ImageMath {
	public static final float PI = 3.141593F;
	public static final float HALF_PI = 1.570796F;
	public static final float QUARTER_PI = 0.7853982F;
	public static final float TWO_PI = 6.283185F;
	private static final float m00 = -0.5F;
	private static final float m01 = 1.5F;
	private static final float m02 = -1.5F;
	private static final float m03 = 0.5F;
	private static final float m10 = 1F;
	private static final float m11 = -2.5F;
	private static final float m12 = 2F;
	private static final float m13 = -0.5F;
	private static final float m20 = -0.5F;
	private static final float m21 = 0F;
	private static final float m22 = 0.5F;
	private static final float m23 = 0F;
	private static final float m30 = 0F;
	private static final float m31 = 1F;
	private static final float m32 = 0F;
	private static final float m33 = 0F;
	public ImageMath() {
	}

	public static float bias(float a, float b) {
		return a / ((1.0F / b - 2.0F) * (1.0F - a) + 1.0F);
	}

	public static float gain(float a, float b) {
		float c = (1.0F / b - 2.0F) * (1.0F - 2.0F * a);
		if ((double) a < 0.5D)
			return a / (c + 1.0F);
		else
			return (c - a) / (c - 1.0F);
	}

	public static float step(float a, float x) {
		return x >= a ? 1.0F : 0.0F;
	}

	public static float pulse(float a, float b, float x) {
		return x >= a && x < b ? 1.0F : 0.0F;
	}

	public static float smoothPulse(float a1, float a2, float b1, float b2,
			float x) {
		if (x < a1 || x >= b2)
			return 0.0F;
		if (x >= a2) {
			if (x < b1) {
				return 1.0F;
			} else {
				x = (x - b1) / (b2 - b1);
				return 1.0F - x * x * (3F - 2.0F * x);
			}
		} else {
			x = (x - a1) / (a2 - a1);
			return x * x * (3F - 2.0F * x);
		}
	}

	public static float smoothStep(float a, float b, float x) {
		if (x < a)
			return 0.0F;
		if (x >= b) {
			return 1.0F;
		} else {
			x = (x - a) / (b - a);
			return x * x * (3F - 2.0F * x);
		}
	}

	public static float circleUp(float x) {
		x = 1.0F - x;
		return (float) Math.sqrt(1.0F - x * x);
	}

	public static float circleDown(float x) {
		return 1.0F - (float) Math.sqrt(1.0F - x * x);
	}

	public static float clamp(float x, float a, float b) {
		return x >= a ? x <= b ? x : b : a;
	}

	public static int clamp(int x, int a, int b) {
		return x >= a ? x <= b ? x : b : a;
	}

	public static double mod(double a, double b) {
		int n = (int) (a / b);
		a -= (double) n * b;
		if (a < 0.0D)
			return a + b;
		else
			return a;
	}

	public static float mod(float a, float b) {
		int n = (int) (a / b);
		a -= (float) n * b;
		if (a < 0.0F)
			return a + b;
		else
			return a;
	}

	public static int mod(int a, int b) {
		int n = a / b;
		a -= n * b;
		if (a < 0)
			return a + b;
		else
			return a;
	}

	public static float triangle(float x) {
		float r = mod(x, 1.0F);
		return 2.0F * ((double) r >= 0.5D ? 1.0F - r : r);
	}

	public static float lerp(float t, float a, float b) {
		return a + t * (b - a);
	}

	public static int lerp(float t, int a, int b) {
		return (int) ((float) a + t * (float) (b - a));
	}

	public static int mixColors(float t, int rgb1, int rgb2) {
		int a1 = rgb1 >> 24 & 255;
		int r1 = rgb1 >> 16 & 255;
		int g1 = rgb1 >> 8 & 255;
		int b1 = rgb1 & 255;
		int a2 = rgb2 >> 24 & 255;
		int r2 = rgb2 >> 16 & 255;
		int g2 = rgb2 >> 8 & 255;
		int b2 = rgb2 & 255;
		a1 = lerp(t, a1, a2);
		r1 = lerp(t, r1, r2);
		g1 = lerp(t, g1, g2);
		b1 = lerp(t, b1, b2);
		return a1 << 24 | r1 << 16 | g1 << 8 | b1;
	}

	public static int bilinearInterpolate(float x, float y, int p[]) {
		int a0 = p[0] >> 24 & 255;
		int r0 = p[0] >> 16 & 255;
		int g0 = p[0] >> 8 & 255;
		int b0 = p[0] & 255;
		int a1 = p[1] >> 24 & 255;
		int r1 = p[1] >> 16 & 255;
		int g1 = p[1] >> 8 & 255;
		int b1 = p[1] & 255;
		int a2 = p[2] >> 24 & 255;
		int r2 = p[2] >> 16 & 255;
		int g2 = p[2] >> 8 & 255;
		int b2 = p[2] & 255;
		int a3 = p[3] >> 24 & 255;
		int r3 = p[3] >> 16 & 255;
		int g3 = p[3] >> 8 & 255;
		int b3 = p[3] & 255;
		float cx = 1.0F - x;
		float cy = 1.0F - y;
		float m0 = cx * (float) a0 + x * (float) a1;
		float m1 = cx * (float) a2 + x * (float) a3;
		int a = (int) (cy * m0 + y * m1);
		m0 = cx * (float) r0 + x * (float) r1;
		m1 = cx * (float) r2 + x * (float) r3;
		int r = (int) (cy * m0 + y * m1);
		m0 = cx * (float) g0 + x * (float) g1;
		m1 = cx * (float) g2 + x * (float) g3;
		int g = (int) (cy * m0 + y * m1);
		m0 = cx * (float) b0 + x * (float) b1;
		m1 = cx * (float) b2 + x * (float) b3;
		int b = (int) (cy * m0 + y * m1);
		return a << 24 | r << 16 | g << 8 | b;
	}

	public static int brightnessNTSC(int rgb) {
		int r = rgb >> 16 & 255;
		int g = rgb >> 8 & 255;
		int b = rgb & 255;
		return (int) ((float) r * 0.299F + (float) g * 0.587F + (float) b * 0.114F);
	}

	public static float spline(float x, int numKnots, float knots[]) {
		int numSpans = numKnots - 3;
		if (numSpans < 1)
			throw new IllegalArgumentException("Too few knots in spline");
		x = clamp(x, 0.0F, 1.0F) * (float) numSpans;
		int span = (int) x;
		if (span > numKnots - 4)
			span = numKnots - 4;
		x -= span;
		float k0 = knots[span];
		float k1 = knots[span + 1];
		float k2 = knots[span + 2];
		float k3 = knots[span + 3];
		float c3 = -0.5F * k0 + 1.5F * k1 + -1.5F * k2 + 0.5F * k3;
		float c2 = 1.0F * k0 + -2.5F * k1 + 2.0F * k2 + -0.5F * k3;
		float c1 = -0.5F * k0 + 0.0F * k1 + 0.5F * k2 + 0.0F * k3;
		float c0 = 0.0F * k0 + 1.0F * k1 + 0.0F * k2 + 0.0F * k3;
		return ((c3 * x + c2) * x + c1) * x + c0;
	}

	public static float spline(float x, int numKnots, int xknots[],
			int yknots[]) {
		int numSpans = numKnots - 3;
		if (numSpans < 1)
			throw new IllegalArgumentException("Too few knots in spline");
		int span;
		for (span = 0; span < numSpans; span++)
			if ((float) xknots[span + 1] > x)
				break;

		if (span > numKnots - 3)
			span = numKnots - 3;
		float t = (x - (float) xknots[span])
				/ (float) (xknots[span + 1] - xknots[span]);
		if (--span < 0) {
			span = 0;
			t = 0.0F;
		}
		float k0 = yknots[span];
		float k1 = yknots[span + 1];
		float k2 = yknots[span + 2];
		float k3 = yknots[span + 3];
		float c3 = -0.5F * k0 + 1.5F * k1 + -1.5F * k2 + 0.5F * k3;
		float c2 = 1.0F * k0 + -2.5F * k1 + 2.0F * k2 + -0.5F * k3;
		float c1 = -0.5F * k0 + 0.0F * k1 + 0.5F * k2 + 0.0F * k3;
		float c0 = 0.0F * k0 + 1.0F * k1 + 0.0F * k2 + 0.0F * k3;
		return ((c3 * t + c2) * t + c1) * t + c0;
	}

	public static int colorSpline(float x, int numKnots, int knots[]) {
		int numSpans = numKnots - 3;
		if (numSpans < 1)
			throw new IllegalArgumentException("Too few knots in spline");
		x = clamp(x, 0.0F, 1.0F) * (float) numSpans;
		int span = (int) x;
		if (span > numKnots - 4)
			span = numKnots - 4;
		x -= span;
		int v = 0;
		for (int i = 0; i < 4; i++) {
			int shift = i * 8;
			float k0 = knots[span] >> shift & 255;
			float k1 = knots[span + 1] >> shift & 255;
			float k2 = knots[span + 2] >> shift & 255;
			float k3 = knots[span + 3] >> shift & 255;
			float c3 = -0.5F * k0 + 1.5F * k1 + -1.5F * k2 + 0.5F * k3;
			float c2 = 1.0F * k0 + -2.5F * k1 + 2.0F * k2 + -0.5F * k3;
			float c1 = -0.5F * k0 + 0.0F * k1 + 0.5F * k2 + 0.0F * k3;
			float c0 = 0.0F * k0 + 1.0F * k1 + 0.0F * k2 + 0.0F * k3;
			int n = (int) (((c3 * x + c2) * x + c1) * x + c0);
			if (n < 0)
				n = 0;
			else if (n > 255)
				n = 255;
			v |= n << shift;
		}

		return v;
	}

	public static int colorSpline(int x, int numKnots, int xknots[],
			int yknots[]) {
		int numSpans = numKnots - 3;
		if (numSpans < 1)
			throw new IllegalArgumentException("Too few knots in spline");
		int span;
		for (span = 0; span < numSpans; span++)
			if (xknots[span + 1] > x)
				break;

		if (span > numKnots - 3)
			span = numKnots - 3;
		float t = (float) (x - xknots[span])
				/ (float) (xknots[span + 1] - xknots[span]);
		if (--span < 0) {
			span = 0;
			t = 0.0F;
		}
		int v = 0;
		for (int i = 0; i < 4; i++) {
			int shift = i * 8;
			float k0 = yknots[span] >> shift & 255;
			float k1 = yknots[span + 1] >> shift & 255;
			float k2 = yknots[span + 2] >> shift & 255;
			float k3 = yknots[span + 3] >> shift & 255;
			float c3 = -0.5F * k0 + 1.5F * k1 + -1.5F * k2 + 0.5F * k3;
			float c2 = 1.0F * k0 + -2.5F * k1 + 2.0F * k2 + -0.5F * k3;
			float c1 = -0.5F * k0 + 0.0F * k1 + 0.5F * k2 + 0.0F * k3;
			float c0 = 0.0F * k0 + 1.0F * k1 + 0.0F * k2 + 0.0F * k3;
			int n = (int) (((c3 * t + c2) * t + c1) * t + c0);
			if (n < 0)
				n = 0;
			else if (n > 255)
				n = 255;
			v |= n << shift;
		}

		return v;
	}

	public static void resample(int source[], int dest[], int length,
			int offset, int stride, float out[]) {
		int srcIndex = offset;
		int destIndex = offset;
		int lastIndex = source.length;
		float in[] = new float[length + 1];
		int i = 0;
		for (int j = 0; j < length; j++) {
			while (out[i + 1] < (float) j)
				i++;
			in[j] = (float) i + ((float) j - out[i]) / (out[i + 1] - out[i]);
		}

		in[length] = length;
		float inSegment = 1.0F;
		float outSegment = in[1];
		float sizfac = outSegment;
		float rSum;
		float gSum;
		float bSum;
		float aSum = rSum = gSum = bSum = 0.0F;
		int rgb = source[srcIndex];
		int a = rgb >> 24 & 255;
		int r = rgb >> 16 & 255;
		int g = rgb >> 8 & 255;
		int b = rgb & 255;
		srcIndex += stride;
		rgb = source[srcIndex];
		int nextA = rgb >> 24 & 255;
		int nextR = rgb >> 16 & 255;
		int nextG = rgb >> 8 & 255;
		int nextB = rgb & 255;
		srcIndex += stride;
		for (i = 1; i < length;) {
			float aIntensity = inSegment * (float) a + (1.0F - inSegment)
					* (float) nextA;
			float rIntensity = inSegment * (float) r + (1.0F - inSegment)
					* (float) nextR;
			float gIntensity = inSegment * (float) g + (1.0F - inSegment)
					* (float) nextG;
			float bIntensity = inSegment * (float) b + (1.0F - inSegment)
					* (float) nextB;
			if (inSegment < outSegment) {
				aSum += aIntensity * inSegment;
				rSum += rIntensity * inSegment;
				gSum += gIntensity * inSegment;
				bSum += bIntensity * inSegment;
				outSegment -= inSegment;
				inSegment = 1.0F;
				a = nextA;
				r = nextR;
				g = nextG;
				b = nextB;
				if (srcIndex < lastIndex)
					rgb = source[srcIndex];
				nextA = rgb >> 24 & 255;
				nextR = rgb >> 16 & 255;
				nextG = rgb >> 8 & 255;
				nextB = rgb & 255;
				srcIndex += stride;
			} else {
				aSum += aIntensity * outSegment;
				rSum += rIntensity * outSegment;
				gSum += gIntensity * outSegment;
				bSum += bIntensity * outSegment;
				dest[destIndex] = (int) Math.min(aSum / sizfac, 255F) << 24
						| (int) Math.min(rSum / sizfac, 255F) << 16
						| (int) Math.min(gSum / sizfac, 255F) << 8
						| (int) Math.min(bSum / sizfac, 255F);
				destIndex += stride;
				rSum = gSum = bSum = 0.0F;
				inSegment -= outSegment;
				outSegment = in[i + 1] - in[i];
				sizfac = outSegment;
				i++;
			}
		}

	}
}
