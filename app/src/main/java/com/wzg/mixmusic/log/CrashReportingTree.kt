package com.wzg.mixmusic.log

import timber.log.Timber

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/5/13 6:28 下午
 */
class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        TODO("Not yet implemented")
    }
}