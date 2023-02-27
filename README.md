

# 根据一套开源Android游戏SDK，基础上进一步开发的海外渠道SDK

TSDK地址：
https://github.com/gsrkuang/TSDK

想说的话：
 
* 本项目仅做学习分析交流、请勿做商业使用、谢谢
 

# 记录下来SDK的开发步骤以及参考文章

##### 游戏SDK效果图，后续的界面UI均会修改
![image](https://user-images.githubusercontent.com/13102787/201046561-c169b29c-62dd-4042-80af-f04d61bb30f7.png)


1、游戏SDK开源项目：[一套开源Android游戏SDK（An Open AndroidGame Channel SDK）](https://github.com/zuowutan/ShareGameSdk)
项目是根据上面的开源项目继续二次开发而来。

2、在开源项目上，集成google、facebook登陆，掘金上面的教程
[❤️Android Google 登录接入❤️](https://juejin.cn/post/7024375507978289166)
[❤️Android 集成 Facebook 登录❤️](https://juejin.cn/post/7024326268409610247)

3、上架Google Play需要隐私政策的神明，可以在此处解决隐私政策的问题[GOOGLE PLAY上架APP设置隐私政策声明问题](https://zhuanlan.zhihu.com/p/372435677)
为什么要上架到Google Play商店？因为需求游戏在接入完SDK后，可以上架海外的游戏平台，上架Google Play审核是比较容易的，上架Google Play商店的流程可以自行找资料解决下，注册开发者账号是需要使用信用卡并且支付25美金的

4、集成Taptap登陆
集成登录文档：[TapTap 登录功能介绍](https://developer.taptap.com/docs/sdk/taptap-login/features/)
注意先成为Taptap的开发者，注意需要应用配置好后，在游戏服务中得到Client ID和Server Secret
打包的时候注意报名与Taptap上配置的一致，否则会出现找不到类的情况

好了，以上是集成登录的详细文档资料，接下来是解决关于Taptap是aar包，在library工程中依赖aar不能嵌套aar生产sdk的问题。

5、[使用fat-aar-android解决library工程中依赖aar嵌套问题](https://github.com/kezong/fat-aar-android)
主要是解决了集成Taptap的aar包问题
按照上面5来集成fat-aar-android后，具体使用fat-aar-android来加载aar的写法，在library中的gradle
```bash
dependencies {
    implementation 'com.android.support:appcompat-v7:26.1.0'
    implementation files('libs/okhttp-3.10.0.jar')
    implementation files('libs/gson-2.2.2.jar')
    implementation files('libs/okio-1.14.1.jar')
    //使用fat-aar来加载aar
    embed (name:'TapLogin_3.15.0', ext:'aar')
    embed (name:'TapCommon_3.15.0', ext:'aar')
    //google sdk
    implementation 'com.google.android.gms:play-services-auth:19.2.0'
    //facebook sdk
    implementation 'com.facebook.android:facebook-android-sdk:12.0.1'
    implementation 'com.facebook.android:facebook-login:12.0.1'
}
```
6、悬浮窗按钮开发，游戏初始化成功后，在游戏Activity中添加悬浮窗view。
悬浮按钮有拖拽，自动隐藏，左右吸附的功能
[悬浮窗FloatIconView的开发过程](https://github.com/gsrkuang/FloatIconView)

7、悬浮按钮点击后，展示个人中心，底部导航的开发
目前底部导航栏有三个，首页，发现，个人中心。横屏和竖屏会分别展示不同的UI界面。
[超简单，几行代码搞定Android底部导航栏](http://www.jianshu.com/p/ade8485a16be)

8、发现问题，原因是登陆窗口（LoginActivity）被打开时候，游戏的activity会触发onPause，导致游戏画面被暂停
原因是intent一个LoginActivity，原本的activity就会处于onPause状态，所以LoginActivity需要使用自定义的Dialog展示.

9、采用Dialog展示解决了游戏画面被暂停的问题后，再次发现问题，Dialog不能加载Fragment，所以需要结合DialogFragment来加载Fragment，把LoginDialog和RegisterDialog都更换成了Fragment，
所有的getSupportFragmentManager都换成了getFragmentManager，解决了Dialog不能加载Fragment的问题
[Android Dialog加载Fragment（DialogFragment）带横竖屏动画](https://blog.csdn.net/u012246458/article/details/123060823)

10、国际版和国内版区分。
sdklibrary_international是国际版
sdklibrary是国内版本，国内版本目前新增了alipay支付方式

-------------------------------------------------------
国内版本的开发

11、添加了kotlin版本，接入时需要新增kotlin支持
implementation 'androidx.core:core-ktx:1.5.0'

apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'

12、网络访问架构修改，使用Retrofit + OKHttp，目的是简化对API的配置，版本需要对应一下版本
implementation files('libs/okhttp-3.14.9.jar')
implementation files('libs/retrofit-2.9.0.jar')
implementation files('libs/gson-2.8.0.jar')
implementation files('libs/okio-1.17.2.jar')
implementation files('libs/converter-gson-2.9.0.jar')
[Retrofit基本使用](https://www.jianshu.com/p/260570146c8c)
[Android-Retrofit完全解析，看完包你不会忘！](https://www.jianshu.com/p/8b61da4f80f0)
[Retrofit如何添加请求头](https://www.jianshu.com/p/e64c14939b39)

