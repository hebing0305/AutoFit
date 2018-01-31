package com.xormedia.autofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.bing.autofit.AutoFit;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    AutoFit.Init(this,1080,1920);
    View view= LayoutInflater.from(this).inflate(R.layout.activity_main,null,false);
    AutoFit.fit(view);
    setContentView(view);
  }
}
