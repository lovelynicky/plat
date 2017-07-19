package com.xiangzhu.plat.utils;

import java.awt.*;

/**
 * Created by liluoqi on 15/12/5.
 */
public class LogoConfig {// logo默认边框颜色
    public static final Color DEFAULT_BORDERCOLOR = Color.WHITE;
    // logo默认边框宽度
    public static final int DEFAULT_BORDER = 2;
    // logo大小默认为照片的1/5
    public static final int DEFAULT_LOGOPART = 5;


    private final int border = DEFAULT_BORDER;
    private final Color borderColor;
    private final int logoPart;


    /**
     * Creates a default config with on color  and off color
     * generating normal black-on-white barcodes.
     */
    public LogoConfig() {
        this(DEFAULT_BORDERCOLOR, DEFAULT_LOGOPART);
    }


    public LogoConfig(Color borderColor, int logoPart) {
        this.borderColor = borderColor;
        this.logoPart = logoPart;
    }


    public Color getBorderColor() {
        return borderColor;
    }


    public int getBorder() {
        return border;
    }


    public int getLogoPart() {
        return logoPart;
    }

}
