package com.xormedia.autofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.bing.autofit.ViewUtils;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    View view= LayoutInflater.from(this).inflate(R.layout.activity_main,null,false);
    long nowtime=System.currentTimeMillis();
    ViewUtils.autoFit(view);
    System.out.println(">>>>适配100个View所需时间:"+(System.currentTimeMillis()-nowtime));
    setContentView(view);
  }

  @Override
  public void setContentView(int layoutResID) {
    View view= LayoutInflater.from(this).inflate(layoutResID,null,false);
    ViewUtils.autoFit(view);
    super.setContentView(view);
  }
}
