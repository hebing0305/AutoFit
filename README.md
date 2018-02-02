# AutoFit
[![API](https://img.shields.io/badge/API-15%2B-green.svg?style=flat)](https://android-arsenal.com/api?level=15)
## 说明
这是一款Android屏幕适配方案。
你只需要在xml里按照设计图上的PX尺寸填写，此lib可以帮你自动适应各种屏幕。   
适配的方式是按照设计图尺寸和屏幕尺寸的比例适配，相当于百分比适配，只需要你对需要调用适配方法传入需要适配的View即可。
可以适配的属性目前有 View的宽高，magin，pading textview的size。
## 依赖方法
    compile 'com.bing:AutoFit:1.0.0'
## 使用说明

### 1.初始化
在使用API前调用初始化一次：AutoFit.init()，参数需要传入设计图的尺寸等，推荐在Applaction类的OnCreate()方法里

### 2.单个View适配
获取到需要适配的View，调用AutoFit.fit(view)即可。
### 3.在Fragment适配

```java
public class MyFragment extends Fragment{
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.activity_main,container);
    AutoFit.fit(view);
    return view;
  }
} 
```
### 4.在Activity适配
```java
public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    View view= LayoutInflater.from(this).inflate(R.layout.activity_main,null,false);
    AutoFit.fit(view);
    setContentView(view);
  }
}
```
### 5.在Adapter适配
在Adapter的getview方法返回之前调用AutoFit.fit(convertView);
```java
public class MyAdapter extends BaseAdapter{
  //...
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    //.....
    AutoFit.fit(convertView);
    return convertView;
  }
 }
```
### 6.一些特殊情况
1.有人可能发现有时候设置一个宽高相等的正方形View却变成了长方形，这是因为设计图的宽高和实际屏幕宽高比列不一样造成的，解决方法就是只按照宽或者高的比列适配(推荐按照宽度比例适配，宽度比例一般误差很小)，一般情况只需要修改单个View的适配方式，方法如下：
```java
AutoFit.setViewLayoutParams(view,宽度，高度,FIT_TYPE.WIDTH);
```
```java
/**
   * @param view
   * @param width   传入小于0的数表示不做改变
   * @param height  传入小于0的数表示不做改变
   * @param magin   动态传入Magin参数 左上右下 可以不传
   * @param fitType 适配方式：按照宽度的比例适配或高度，或者宽按宽度，高按高度。
   * @return
   */
  public static void setViewLayoutParams(View view, int width, int height, FIT_TYPE fitType, float... magin) {}
```
全部View按照宽度适配:
```java
AutoFit.fit(view,FIT_TYPE.WIDTH);
```

    


    
