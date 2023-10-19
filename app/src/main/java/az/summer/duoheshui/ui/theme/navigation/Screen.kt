package az.summer.duoheshui.ui.theme.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    var id: String,
    val title: String,
    val icon: ImageVector,
) {

    object Home : Screen("home", "喝水", Icons.Outlined.Home)
    object Profile : Screen("profile", "记录", Icons.Outlined.List)
    object Setting : Screen("setting", "设置", Icons.Outlined.Settings)

    object Items {
        val list = listOf(
            Profile, Home, Setting
        )
    }

}
