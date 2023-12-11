1.在项目的根目录下的build.gradle中引入mavenCentral公用仓库
```
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
```

2.在app目录下的build.gradle中引入SDK库
```
dependencies {
    implementation 'io.github.youmi-obg:new-offerswall:1.1.0'
}
```

3.SDK的接入方式，在项目的Application类中的onCreate( )方法内，
使用 YoumiOffersSdk.getInstance().init(this,"your_APPID")
"your_APPID"为你在有米官网注册成功后的渠道APPID，该APPID不能为空，如果为空无法正常使用SDK功能
```
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        YoumiOffersWallSdk.getInstance().init(this,"your_APPID")
    }
 }
 ```

4.SDK广告墙的启动方式，在需要跳转到SDK的地方（Activity的打开方式），添加代码 YoumiOffersSdk.getInstance().startOffersWall(context，userId) context为Context类的实例，userId为String类型，userId为该APP用户的唯一Id （userId非必传参数，没有userId可传空字符串）
```
btn_test.setOnClickListener {
    YoumiOffersWallSdk.getInstance().startOffersWall(context,"userId")
}
```

5.SDK广告墙的启动方式，（Fragment的打开方式），添加代码 YoumiMainFragment fragment = YoumiMainFragment.newInstance("userId"); context为Context类的实例，userId为String类型，userId为该APP用户的唯一Id （userId非必传参数，没有userId可传空字符串）
```
btn_test.setOnClickListener {
    YoumiMainFragment fragment = YoumiMainFragment.newInstance("userId");
}
```
