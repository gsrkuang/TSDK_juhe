

# 一套开源Android游戏SDK（An Open AndroidGame Channel SDK）

TSDK地址：
https://github.com/gsrkuang/TSDK

## 项目简介（Directions for use）：

大家好，我是一名Android开发。</br>
前不久入职于一家游戏研发公司，公司部门给我的开发需求是研发H5游戏SDK和对接H5渠道SDK（对于只有App研发的我来说，其中经历和酸甜苦辣那可是一把鼻涕一把泪。</br>
不乏笔者牺牲周末时间去熟悉该业务的朋友公司向他取经讨论技术方案，画UML图等。要命的是各大搜素引擎可查询SDK资料几乎就是打广告打广告，
免费提供解决技术方案（也就是源代码）的参考实在太少，这根本不能符合我大Android集思广益忠于开源的灵魂好嘛。 </br>
所以，本项目不仅是针对未来有类似研发需求的朋友传道解惑，节约时间少走弯路，而且更主要的是揭开Android游戏SDK的神秘面纱。
另外：旧项目已经被作者删除了。新项目是基于 MVP架构来进行SDK编码设计，抛弃了传统代码布局那种繁琐冗余且费时费力的写法！</br>
关于进一步细节可以参考文字说明以及源码，谢谢！</br>
 
想说的话：
 
* 本项目仅做学习分析交流、请勿做商业使用、谢谢
 

[项目文字说明](https://www.jianshu.com/p/8b9d82560a67)

Ps：

* 本项目的商业接口只有 登录 与 注册 （且来源于 玩Android 开放API）、 请放心使用 ！这里在次感谢鸿洋大神

* 对本项目有任何问题，首先请阅读源码、然后建议通过自己的技术栈去分析和思考问题。拒绝毫无目的的质疑以及不看源码直怼细节等现象！
 
* 如果有一些自己的技术想法或者对项目有任何意见请直接联系笔者或者在评论区（非诚勿扰）

* 著作权归作者所有,转载请注明作者, 商业转载请联系作者获得授权，非商业转载请注明出处(开头或结尾请添加转载出处，添加原文url地址)

* 文章请勿滥用,也希望大家尊重笔者的劳动成果,谢谢。
   
联系方式：

* QQ：2172410367


最后：

千里之行，始于足下。

希望和大家一起学习成长共同进步,早日达到技术之巅！








# 记录下来SDK的开发步骤以及参考文章

##### 游戏SDK效果图，后续的界面UI均会修改
![在这里插入图片描述](https://img-blog.csdnimg.cn/92a7b538415b42a69b27c259fa101df8.png)

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



