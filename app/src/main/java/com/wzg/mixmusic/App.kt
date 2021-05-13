package com.wzg.mixmusic

import android.app.Application
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

    }
}