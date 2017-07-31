package com.common.util.GaussianBlur.filter;
/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.



// Referenced classes of package filter:
//            PointFilter

public class FillFilter extends PointFilter
{

    public FillFilter()
    {
        this(-16777216);
    }

    public FillFilter(int color)
    {
        fillColor = color;
    }

    public void setFillColor(int fillColor)
    {
        this.fillColor = fillColor;
    }

    public int getFillColor()
    {
        return fillColor;
    }

    public int filterRGB(int x, int y, int rgb)
    {
        return fillColor;
    }

    private int fillColor;
}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\Users\Administrator\Desktop\yanhui-blur-1.0.jar
	Total time: 32 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/