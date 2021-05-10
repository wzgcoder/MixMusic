package com.wzg.library.binding

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * describe viewBinding 封装类.
 *
 * @author wangzhangang
 * @date 2021/4/29 3:25 下午
 */


/**
 * activity 封装viewbinding
 */
inline fun <reified VB : ViewBinding> ComponentActivity.binding() = lazy {
    inflateBinding<VB>(layoutInflater).apply {
        setContentView(root)
    }
}

/**
 * activity 封装viewbinding
 */
inline fun <reified VB : ViewBinding> Dialog.binding() = lazy {
    inflateBinding<VB>(layoutInflater).apply {
        setContentView(root)
    }
}

inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java).invoke(null, layoutInflater) as VB

inline fun <reified VB : ViewBinding> inflateBinding(parent: ViewGroup) =
    inflateBinding<VB>(LayoutInflater.from(parent.context), parent, false)

inline fun <reified VB : ViewBinding> inflateBinding(
    layoutInflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean
) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        .invoke(null, layoutInflater, parent, attachToParent) as VB

inline fun <reified VB : ViewBinding> View.bind() =
    VB::class.java.getMethod("bind", View::class.java).invoke(null, this) as VB
/**
 * 是使用属性委托方式封装Fragment的viewBinding
 */
inline fun <reified VB : ViewBinding> Fragment.binding() = FragmentBindingDelegate<VB>{
    requireView().bind()
}

class FragmentBindingDelegate<VB>(
    private val block: ()->VB
) : ReadOnlyProperty<Fragment, VB> {
    private var binding: VB? = null
    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (binding == null) {
            binding = block().also {
                if (it is ViewDataBinding) it.lifecycleOwner = thisRef.viewLifecycleOwner
            }
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroyView() {
                    binding = null
                }
            })
        }
        return binding!!
    }

}