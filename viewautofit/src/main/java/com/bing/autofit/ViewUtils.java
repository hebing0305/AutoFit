package com.bing.autofit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bing.he on 2017/4/12.
 */

public class ViewUtils {
  private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

  /**
   * 适配方式 ：
   * WIDTH：View的宽高都根据比列（屏幕宽度和设计图宽度的比列）适配 （主要用于正方形等需要保证宽高相等的View使用）
   * WIDTH_HEIGHT：View的宽根据 宽度的比列适配，View的高根据高的比列适配（默认方式）
   */
  public enum FIT_TYPE {
    WIDTH, WIDTH_HEIGHT
  }

  public static void init(Context context,int width,int height){
    new DisplayUtil(context,width,height);
  }

  /**
   * @param view
   * @param width   传入小于0的数表示不做改变
   * @param height  传入小于0的数表示不做改变
   * @param magin   动态传入Magin参数 左上右下 可以不传
   * @param fitType 适配方式。
   * @return
   */
  public static void setViewLayoutParams(View view, int width, int height, FIT_TYPE fitType, float... magin) {
    if (view != null) {
      ViewGroup.LayoutParams params = view.getLayoutParams();
      if (params != null) {
        if (width > 0) {
          width = DisplayUtil.widthpx2px(width);
          //如果按照比列转换后小于1 那么像素最小也需要是1
          if (width < 1) {
            width = 1;
          }
          params.width = width;
        } else if (width == 0) {
          params.width = width;
        }
        if (height > 0) {
          if (fitType == FIT_TYPE.WIDTH_HEIGHT) {
            height = DisplayUtil.heightpx2px(height);
          } else {
            height = DisplayUtil.widthpx2px(height);
          }
          //如果按照比列转换后小于1 那么像素最小也需要是1
          if (height <= 0) {
            height = 1;
          }
          params.height = height;
        } else if (height == 0) {
          params.height = height;
        }
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams && magin.length > 0) {
          ViewGroup.MarginLayoutParams maginParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
          int leftMagin = magin.length > 0 ? (int) DisplayUtil.widthpx2px(magin[0]) : 0;
          int topMagin = magin.length > 1 ? (int) DisplayUtil.heightpx2px(magin[1]) : 0;
          int rightMagin = magin.length > 2 ? (int) DisplayUtil.widthpx2px(magin[2]) : 0;
          int bottomMagin = magin.length > 3 ? (int) DisplayUtil.heightpx2px(magin[3]) : 0;
          maginParams.leftMargin = leftMagin;
          maginParams.topMargin = topMagin;
          maginParams.rightMargin = rightMagin;
          maginParams.bottomMargin = bottomMagin;
        }
      } else {
        throw new NullPointerException("适配的View必须先有父亲!动态new出的View需要先add后适配");
      }
    }
  }

  public static void setViewLayoutParams(View view, int width, int height, float... magin) {
    setViewLayoutParams(view, width, height, FIT_TYPE.WIDTH_HEIGHT, magin);
  }

  /**
   * 设置View的focus移动规则，左上右下的ViewId
   */
  public static void setFocusMoveRule(View view, View leftView, View upView, View rightView, View downView) {
    view.setNextFocusLeftId(leftView == null ? 0 : leftView.getId());
    view.setNextFocusUpId(upView == null ? 0 : upView.getId());
    view.setNextFocusRightId(rightView == null ? 0 : rightView.getId());
    view.setNextFocusDownId(downView == null ? 0 : downView.getId());
  }

  public static String dataFomat(Date date, String fomatStr) {
    SimpleDateFormat sdf = new SimpleDateFormat(fomatStr);
    return sdf.format(date);
  }

  /**
   * 根据View之前设置的固定宽高，依据设计图和屏幕的比例进行缩放
   *
   * @param view    需要适配的View
   * @param fitType 是否按照
   */
  public static void autoFit(View view, FIT_TYPE fitType) {
    if (view == null || view.getTag(Integer.MAX_VALUE) != null)
      return;
    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
    if (layoutParams != null) {
      setViewLayoutParams(view, layoutParams.width, layoutParams.height, fitType);
      //如果有magin
      if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
        setViewLayoutParams(view, -1, -1, fitType, marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
      }
    }
    //如果有pading
    int left = view.getPaddingLeft();
    int right = view.getPaddingRight();
    int top = view.getPaddingTop();
    int bottom = view.getPaddingBottom();
    if (left != 0 || top != 0 || right != 0 || bottom != 0) {
      view.setPadding(
          DisplayUtil.widthpx2px(left),
          fitType == FIT_TYPE.WIDTH ? DisplayUtil.widthpx2px(top) : DisplayUtil.heightpx2px(top),
          DisplayUtil.widthpx2px(right),
          fitType == FIT_TYPE.WIDTH ? DisplayUtil.widthpx2px(bottom) : DisplayUtil.heightpx2px(bottom)
      );
    }
    //如果View是textview
    if (view instanceof TextView) {
      TextView textView = (TextView) view;
      textView.setTextSize(DisplayUtil.px2sp(textView.getTextSize()));
    }
    if (view instanceof ViewGroup) {
      ViewGroup viewGroup = (ViewGroup) view;
      for (int i = 0; i < viewGroup.getChildCount(); i++) {
        View child = viewGroup.getChildAt(i);
        autoFit(child, fitType);
      }
    }
    view.setTag(Integer.MAX_VALUE, Integer.MAX_VALUE);//保存一个Tag 代表已经适配过这个View，不再重新适配
  }

  public static void autoFit(View view) {
    autoFit(view, FIT_TYPE.WIDTH_HEIGHT);
  }

  public static void autoFit(Context _context, int _designWidth, int _designHeight, View view) {
    new DisplayUtil(_context, _designWidth, _designHeight);
    autoFit(view, FIT_TYPE.WIDTH_HEIGHT);
  }

  /**
   * 生成一个ViewId 系统View类其实有此方法 不过必须SDK>17 所以复制了系统方法 用于兼容低版本
   *
   * @return
   */
  public static int generateViewId() {
    for (; ; ) {
      final int result = sNextGeneratedId.get();
      // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
      int newValue = result + 1;
      if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
      if (sNextGeneratedId.compareAndSet(result, newValue)) {
        return result;
      }
    }
  }
}
