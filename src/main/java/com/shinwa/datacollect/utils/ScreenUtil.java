package com.shinwa.datacollect.utils;

import java.awt.*;

/**
 * 获取显示器宽和高
 */
public class ScreenUtil {
    public static Dimension getScreenSize(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        return toolkit.getScreenSize();
    }
}
