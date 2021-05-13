package com.wzg.mixmusic.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wzg.mixmusic.data.entity.PlayListResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import rxhttp.toClass
import rxhttp.toStr
import rxhttp.wrapper.param.RxHttp

class HomeViewModel : ViewModel(), CoroutineScope by MainScope() {

    private val _loginInfo = MutableLiveData<String>()
    val loginInfo: LiveData<String> = _loginInfo

    private val _playList = MutableLiveData<PlayListResult>()
    val playListData: LiveData<PlayListResult> = _playList

    fun phoneLogin(phoneNum: String, password: String) {
        launch {
            _loginInfo.value = RxHttp.postJson("login/cellphone")
                .add("phone", phoneNum)
                .add("password", password)
                .toStr()
                .await()
        }
    }

    fun loadPlayList(id: String) {
        viewModelScope.launch {
            //获取歌单详情
            _playList.value = RxHttp.get("/playlist/detail")
                .add("id",id)
                .toClass<PlayListResult>()
                .await()
            Log.i(
                "TAG", "onLoadChildren: \n" + _playList.value
            )

        }
    }
}