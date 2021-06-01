/**
 * describe Java类作用描述.
 *
 * @author wangzhangang
 * @date 2021/6/1 11:04 上午
 */
object Compose {
    const val composeVerion = "1.0.0-beta07"

    //工具支持（预览等）
    const val ui = "androidx.compose.ui:ui:$composeVerion"

    //基础(边框，背景，框，图像，滚动，形状，动画等)
    const val foundation = "androidx.compose.foundation:foundation:$composeVerion"

    //Material Design
    const val material = "androidx.compose.material:material:$composeVerion"

    //Material design icons
    const val material_icons_core = "androidx.compose.material:material-icons-core:$composeVerion"
    const val material_icons_extended =
        "androidx.compose.material:material-icons-extended:$composeVerion"

    //与可观察框架集成
    const val runtime_livedata = "androidx.compose.runtime:runtime-livedata:$composeVerion"
    const val runtime_rxjava2 = "androidx.compose.runtime:runtime-rxjava2:$composeVerion"
}