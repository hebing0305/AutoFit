package com.xormedia.autofit;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bing.autofit.AutoFit;

/**
 * Created by Bing on 2018/1/31.
 */

public class MyAdapter extends BaseAdapter{
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    //.....
    AutoFit.fit(convertView);
    return convertView;
  }
  @Override
  public int getCount() {
    return 0;
  }

  @Override
  public Object getItem(int position) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return 0;
  }


}
