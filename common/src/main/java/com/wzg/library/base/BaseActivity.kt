package com.wzg.library.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

/**
 * describe Activity基础类.
 *
 * @author wangzhangang
 * @date 2021/4/28 6:44 下午
 */
abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
        initObserver()
    }

    protected abstract fun initViews()
    protected abstract fun initData()
    protected abstract fun initObserver()

}