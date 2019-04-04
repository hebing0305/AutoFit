package com.xormedia.autofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bing.autofit.ViewUtils;

/**
 * Created by Bing on 2018/1/31.
 */

public class MyFragment extends Fragment{
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.activity_main,container);
    ViewUtils.autoFit(view);
    return view;
  }
}
