package com.wzg.mixmusic

import android.app.Application
import com.drake.net.initNet
import com.drake.net.okhttp.setConverter
import com.drake.net.okhttp.setLog
import com.wzg.mixmusic.log.CrashReportingTree
import timber.log.Timber

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/5/13 6:25 下午
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        initNet("https://www.wangzhangang.com/") {
            setLog(BuildConfig.DEBUG) // 作用域发生异常是否打印
        }
    }
}