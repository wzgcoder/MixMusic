package com.wzg.mixmusic.data.repository

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/5/11 11:39 上午
 */
interface UserRepository {
    suspend fun phoneLogin(phone:String,password:String)
}