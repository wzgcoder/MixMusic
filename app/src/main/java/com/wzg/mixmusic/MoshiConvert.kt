package com.wzg.mixmusic

import com.drake.net.convert.JSONConvert
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/6/1 5:09 下午
 */
class MoshiConvert : JSONConvert(code = "code", message = "msg", success = "200") {
    val moshi = Moshi.Builder().build()

    override fun <S> String.parseBody(succeed: Type): S? {
        return moshi.adapter<S>(succeed).fromJson(this)
    }
}