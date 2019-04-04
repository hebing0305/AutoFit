package com.xormedia.autofit;

import android.app.Application;

import com.bing.autofit.DisplayUtil;

/**
 * Created by Bing on 2018/2/1.
 */

public class MyApplaction extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    new DisplayUtil(this,1920,1080);
  }
}
