package com.bing.autofit;

import android.content.Context;
import android.util.DisplayMetrics;

public class DisplayUtil {
  /**
   * 将px值转换为dip值，保证尺寸大小不变
   *
   * @param pxValue
   * 传入图片像素
   */
  private static int designWidth;
  private static int designHeight;
  private static Context context;


  private static class WidthHeight {
    int designWidth, designHeight;
    float displayWidth, displayHeight;

    WidthHeight(int _designWidth, int _designHeight, float display_width, float display_height) {
      designWidth = _designWidth;
      designHeight = _designHeight;
      displayWidth = display_width;
      displayHeight = display_height;

      if (designWidth > designHeight && displayWidth > displayHeight) {
        float designScale = Float.valueOf(designWidth) / Float.valueOf(designHeight);
        float displayScale = displayWidth / displayHeight;
        if (displayScale > designScale) {
          displayWidth = display_height * designWidth / designHeight;
        } else {
          displayHeight = display_width * designHeight / designWidth;
        }
      } else if (designWidth < designHeight && displayWidth < displayHeight) {
        float designScale = Float.valueOf(designHeight) / Float.valueOf(designWidth);
        float displayScale = displayHeight / displayWidth;
        if (displayScale > designScale) {
          displayHeight = display_width * designHeight / designWidth;
        } else {
          displayWidth = display_height * designWidth / designHeight;
        }
      }
    }
  }

  public DisplayUtil(Context _context, int _designWidth, int _designHeight) {
    context = _context;
    designWidth = _designWidth;
    designHeight = _designHeight;
  }

  public static int widthpx2px(float pxValue) {
    float ret = 0;
    if (context != null) {
      DisplayMetrics dm = context.getResources().getDisplayMetrics();
      float display_width = dm.widthPixels;
      float display_height = dm.heightPixels;
      ret = widthpx2px(designWidth, designHeight, display_width, display_height, pxValue);
    }
    return (int) ret;
  }

  public static float widthpx2px(int _designWidth, int _designHeight, float pxValue) {
    float ret = 0;
    if (context != null) {
      DisplayMetrics dm = context.getResources().getDisplayMetrics();
      float display_width = dm.widthPixels;
      float display_height = dm.heightPixels;
      ret = widthpx2px(_designWidth, _designHeight, display_width, display_height, pxValue);
    }
    return ret;
  }

  public static float widthpx2px(int _designWidth, int _designHeight, float display_width, float display_height, float pxValue) {
    WidthHeight widthHeight = new WidthHeight(_designWidth, _designHeight, display_width, display_height);
    int rootWidth;
    if (widthHeight.displayWidth > widthHeight.displayHeight) {
      rootWidth = Math.max(widthHeight.designHeight, widthHeight.designWidth);
    } else {
      rootWidth = Math.min(widthHeight.designHeight, widthHeight.designWidth);
    }
    float div = rootWidth / widthHeight.displayWidth;
    return pxValue / div;
  }

  public static float widthpx2dip(float pxValue) {
    float ret = widthpx2px(pxValue);
    if (context != null) {
      DisplayMetrics dm = context.getResources().getDisplayMetrics();
      float scale = dm.density;
      ret = ret / scale + 0.5f;
    }
    return ret;
  }

  public static float heightpx2px(int _designWidth, int _designHeight, float display_width, float display_height, float pxValue) {
    WidthHeight widthHeight = new WidthHeight(_designWidth, _designHeight, display_width, display_height);
    int rootHeight;
    if (widthHeight.displayWidth > widthHeight.displayHeight) {
      rootHeight = Math.min(widthHeight.designHeight, widthHeight.designWidth);
    } else {
      rootHeight = Math.max(widthHeight.designHeight, widthHeight.designWidth);
    }
    float div = rootHeight / widthHeight.displayHeight;
    return pxValue / div;
  }

  public static int heightpx2px(float pxValue) {
    float ret = 0;
    if (context != null) {
      DisplayMetrics dm = context.getResources().getDisplayMetrics();
      float display_width = dm.widthPixels;
      float display_height = dm.heightPixels;
      ret = heightpx2px(designWidth, designHeight, display_width, display_height, pxValue);
    }
    return (int) ret;
  }

  public static float heightpx2px(int _designWidth, int _designHeight, float pxValue) {
    float ret = 0;
    if (context != null) {
      DisplayMetrics dm = context.getResources().getDisplayMetrics();
      float display_width = dm.widthPixels;
      float display_height = dm.heightPixels;
      ret = heightpx2px(_designWidth, _designHeight, display_width, display_height, pxValue);
    }
    return ret;
  }

  public static float heightpx2dip(float pxValue) {
    float ret = heightpx2px(pxValue);
    if (context != null) {
      DisplayMetrics dm = context.getResources().getDisplayMetrics();
      float scale = dm.density;
      ret = ret / scale + 0.5f;
    }
    return ret;
  }

  /**
   * 将dip值转换为px值，保证尺寸大小不变
   *
   * @param dipValue 传入屏幕尺寸值
   */
  public static float dip2px(float dipValue) {
    float ret = 0;
    if (context != null) {
      DisplayMetrics dm = context.getResources().getDisplayMetrics();
      float scale = dm.density;
      ret = dipValue * scale + 0.5f;
    }
    return ret;
  }

  /**
   * 将px值转换为sp值，保证文字大小不变
   *
   * @param pxValue 传入图片像素
   */
  public static float px2sp(float pxValue) {
    return px2sp(context, designWidth, designHeight, pxValue);
  }

  public static float px2sp(Context _context, int _designWidth, int _designHeight, float pxValue) {
    float ret = 0;
    if (_context != null) {
      DisplayMetrics dm = _context.getResources().getDisplayMetrics();
      float display_width = dm.widthPixels;
      float display_height = dm.heightPixels;
      ret = px2sp(context, _designWidth, _designHeight, display_width, display_height, pxValue);
    }
    return ret;
  }

  public static float px2sp(Context _context, int _designWidth, int _designHeight, float display_width, float display_height, float pxValue) {
    float ret = 0;
    if (_context != null) {
      DisplayMetrics dm = _context.getResources().getDisplayMetrics();
      float fontScale = dm.scaledDensity;
      pxValue = widthpx2px(_designWidth, _designHeight, display_width, display_height, pxValue);
      ret = pxValue / fontScale + 0.5f;
    }
    return ret;
  }

//  public static float fixPx2sp(float pxValue) {
//    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
//    // float div = (float) (720.0/display_width);
//    // Log.i("==============div", div+"");
//    // pxValue = pxValue/fontScale;
//    // Log.i("==============", fontScale+"");
//    // Log.i("==============", (pxValue / fontScale + 0.5f)+"");
//    // float baseDis = dip2px(720);
//    // Log.i("==============baseDis", baseDis+"");
//    // float nowDis = 480/fontScale;
//    // Log.i("==============nowDis", nowDis+"");
//    // float value = baseDis*pxValue/fontScale/nowDis;
//    // Log.i("==============value", (value / fontScale + 0.5f)+"");
//    pxValue = pxValue * 480 / 720;
//    return pxValue / fontScale + 0.5f;
//    // return pxValue / fontScale;
//  }

  /**
   * 将sp值转换为px值，保证文字大小不变
   *
   * @param spValue 传入字体尺寸值
   */
  public static int sp2px(float spValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (spValue * fontScale + 0.5f);
  }
}
