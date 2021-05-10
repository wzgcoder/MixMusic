package com.wzg.library.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * describe Activity基础类.
 *
 * @author wangzhangang
 * @date 2021/4/28 6:44 下午
 */
abstract class BaseActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initData()
        initListener()
    }

    protected abstract fun initViews()
    protected abstract fun initData()
    protected abstract fun initListener()
}