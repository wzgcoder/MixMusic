/**
 * describe 第三方库.
 *
 * @author wangzhangang
 * @date 2021/4/30 3:48 下午
 */
object Deps {

    /*
    * https://github.com/drakeet/MultiType
    * 更轻松，更灵活地为Android RecyclerView创建多种类型。
    * */
    const val multitype = "com.drakeet.multitype:multitype:4.2.0"

    const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.21"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"


    /* RxHttp https://github.com/liujingxing/RxHttp */
    const val rxHttp = "com.ljx.rxhttp:rxhttp:2.5.7"
    const val rxHttp_compiler = "com.ljx.rxhttp:rxhttp-compiler:2.5.7"
    const val rxlift_rxjava = "com.ljx.rxlife2:rxlife-rxjava:2.0.0"

    val squareup = Squareup

    object Squareup {

        const val okHttp3 = "com.squareup.okhttp3:okhttp:5.0.0-alpha.2"

        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"

        /* 天生对Kotlin友好的json 工具 */
        const val moshi = "com.squareup.moshi:moshi:1.12.0"
    }

    //viewBinding 和 DataBinding 简化封装
    const val binding = "com.hi-dhl:binding:1.1.3"

    /*JakeWharton 的库*/
    //Log打印库
    const val timber = "com.jakewharton.timber:timber:4.7.1"

    // Net
    const val Net = "com.github.liangjingkanji:Net:3.0.5"

    //支持自动下拉刷新和缺省页的
    const val BRV = "com.github.liangjingkanji:BRV:1.3.20"

    //kolin图片加载框架
    const val coil = "io.coil-kt:coil:1.2.1"

    //BaseRecyclerViewAdapterHelper
    const val BRVAH = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4"

}