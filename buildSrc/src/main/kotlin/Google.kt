/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/4/30 3:42 下午
 */
object Google {
    const val material = "com.google.android.material:material:1.3.0"

    val exoplayer = ExopPlayer

    object ExopPlayer {
        const val exoplayer_version = "2.14.0"
        const val core = "com.google.android.exoplayer:exoplayer-core:$exoplayer_version"
        const val ui = "com.google.android.exoplayer:exoplayer-ui:$exoplayer_version"
        const val mediasession =
            "com.google.android.exoplayer:extension-mediasession:$exoplayer_version"
        const val cast = "com.google.android.exoplayer:extension-cast:$exoplayer_version"
    }
}
