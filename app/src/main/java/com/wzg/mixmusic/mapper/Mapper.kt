package com.wzg.mixmusic.mapper

/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/6/2 10:36 上午
 */
interface Mapper<I, O> {
    fun map(i: I): O
}