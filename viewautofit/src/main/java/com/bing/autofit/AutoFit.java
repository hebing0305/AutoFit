package com.bing.autofit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by bing.he on 2017/4/12.
 */

public class AutoFit {

  public enum FIT_TYPE{
    WIDTH,HEIGHT,WIDTH_HEIGHT
  }

  /**
   * 初始化
   * @param _context
   * @param _designWidth UI设计图的宽度
   * @param _designHeight UI设计图的高度
   */
  public static void Init(Context _context, int _designWidth, int _designHeight){
    new DisplayUtil(_context,_designWidth,_designHeight);
  }
  /**
   * @param view
   * @param width   传入小于0的数表示不做改变
   * @param height  传入小于0的数表示不做改变
   * @param magin   动态传入Magin参数 左上右下 可以不传
   * @param fitType 适配方式：按照宽度的比例适配或高度，或者宽按宽度，高按高度。
   * @return
   */
  public static void setViewLayoutParams(View view, int width, int height, FIT_TYPE fitType, float... magin) {
    if (view != null) {
      ViewGroup.LayoutParams params = view.getLayoutParams();
      if (params != null) {
        if (width >1) {
//          System.out.println("\n转换方式:"+fitType);
//          System.out.println(view.getId()+">>>>转换前宽："+params.width);
          if(fitType==FIT_TYPE.WIDTH||fitType==FIT_TYPE.WIDTH_HEIGHT) {
            params.width = (int) DisplayUtil.widthpx2px(width);
          }else{
            params.width = (int) DisplayUtil.heightpx2px(width);
          }
//          System.out.println(view.getId()+">>>>转换后宽："+params.width);
        }
        if (height >1) {
//          System.out.println("\n转换方式:"+fitType);
//          System.out.println(view.getId()+">>>>转换前height："+params.height);
          if (fitType==FIT_TYPE.HEIGHT||fitType==FIT_TYPE.WIDTH_HEIGHT) {
            params.height = (int) DisplayUtil.heightpx2px(height);
          } else {
            params.height = (int) DisplayUtil.widthpx2px(height);
          }
//          System.out.println(view.getId()+">>>>转换后height："+params.height);
        }

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
    }
  }
  /**
   * @param view
   * @param width   传入小于0的数表示不做改变
   * @param height  传入小于0的数表示不做改变
   * @param magin   动态传入Magin参数 左上右下 可以不传
   * @return
   */
  public static void setViewLayoutParams(View view, int width, int height, float... magin) {
    setViewLayoutParams(view, width, height, FIT_TYPE.WIDTH_HEIGHT, magin);
  }

  /**
   * 根据View之前设置的固定宽高,设计图和屏幕的比例进行缩放
   * @param view 需要适配的View
   * @param fitType 是否按照
   */
  public static void fit(View view, FIT_TYPE fitType) {
    if (view == null)
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
      view.setPadding((int) DisplayUtil.widthpx2px(left), (int) DisplayUtil.widthpx2px(top), (int) DisplayUtil.widthpx2px(right), (int) DisplayUtil.widthpx2px(bottom));
    }
    //如果View是textview
    if (view instanceof TextView) {
      TextView textView = (TextView) view;
      textView.setTextSize(DisplayUtil.px2sp(textView.getTextSize()));
    }
    //如果有儿子 继续适配儿子
    if (view instanceof ViewGroup) {
      ViewGroup viewGroup = (ViewGroup) view;
      for (int i = 0; i < viewGroup.getChildCount(); i++) {
        View child = viewGroup.getChildAt(i);
        fit(child,fitType);
      }
    }
  }

  /**
   * 根据View之前设置的固定宽高,设计图和屏幕的比例进行缩放
   * @param view
   */
  public static void fit(View view) {
    fit(view, FIT_TYPE.WIDTH_HEIGHT);
  }
}
