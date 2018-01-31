# AutoFit
## 说明
这是一款Android屏幕适配方案。
你只需要在xml里按照设计图上的PX尺寸填写，此lib可以帮你自动适应各种屏幕。   
适配的方式是按照设计图尺寸和屏幕尺寸的比例适配，相当于百分比适配，只需要你对需要调用适配方法传入需要适配的View即可。
可以适配的属性目前有 View的宽高，magin，pading textview的size。
## 依赖方法
compile 'com.bing:autofit:1.0.0'
## 使用说明

### 1.初始化
在使用API前调用初始化：AutoFit.init()，参数需要传入设计图的尺寸，推荐在Applaction类的OnCreate()方法里

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
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    //.....
    AutoFit.fit(convertView);
    return convertView;
  }
```


    


    
