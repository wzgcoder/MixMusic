package com.wzg.library.base

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/5/11 11:16 上午
 */
abstract class BaseFragment : Fragment(){

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
        initObserver()
        initData()
    }

    open fun initView() {

    }

    open fun initData() {

    }

    open fun initObserver() {

    }
}