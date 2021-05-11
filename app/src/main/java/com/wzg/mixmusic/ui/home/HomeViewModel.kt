package com.wzg.mixmusic.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import rxhttp.toStr
import rxhttp.wrapper.param.RxHttp

class HomeViewModel : ViewModel() , CoroutineScope by MainScope() {

    private val _loginInfo = MutableLiveData<String>()
    val loginInfo: LiveData<String> = _loginInfo
    fun phoneLogin(phoneNum: String, password: String) {
        launch {
            _loginInfo.value = RxHttp.postJson("login/cellphone")
                .add("phone", phoneNum)
                .add("password", password)
                .toStr()
                .await()
        }
    }
}