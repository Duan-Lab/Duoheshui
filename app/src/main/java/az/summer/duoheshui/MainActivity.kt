package az.summer.duoheshui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import az.summer.duoheshui.ui.theme.DuoheshuiTheme
import az.summer.duoheshui.ui.theme.navigation.Navigation
import az.summer.duoheshui.ui.theme.screen.HomePage
import az.summer.duoheshui.ui.theme.screen.ProfilePage
import az.summer.duoheshui.ui.theme.navigation.Screen
import az.summer.duoheshui.ui.theme.screen.SettingPage
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val currentScreen = mutableStateOf(Screen.Home.id)

        setContent {
            DuoheshuiTheme {
                var openLoveDialog by remember { mutableStateOf(false) }
                rememberSystemUiController().setStatusBarColor(
                    Color.Transparent, darkIcons = isSystemInDarkTheme()
                )
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        if (openLoveDialog) {
                            AlertDialog(
                                onDismissRequest = { openLoveDialog = false },
                                title = { Text(text = "谢谢") },
                                text = {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = "关注永雏塔菲喵",
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.align(CenterHorizontally),
                                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp),
                                        )
                                        Text(
                                            text = "关注永雏塔菲谢谢喵",
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.align(CenterHorizontally),
                                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp),
                                        )
                                    }
                                },
                                confirmButton = {
                                    TextButton(
                                        onClick = {
                                            openLoveDialog = false
                                        }
                                    ) {
                                        Text(text = "喵喵")
                                    }
                                },
                                icon = { Icons.Outlined.FavoriteBorder }
                            )
                        }
                        TopAppBar(
                            title = {
                                Text(
                                    modifier = Modifier
                                        .padding(0.dp),
                                    text = "Duoheshui",
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1,
                                    style = TextStyle(
                                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                        fontWeight = FontWeight.Normal
                                    )
                                )
                            },
                            actions = {
                                IconButton(onClick = {
                                    openLoveDialog = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.FavoriteBorder,
                                        contentDescription = "LOVE"
                                    )

                                }
                            }
                        )

                    },
                    bottomBar = {
//                        AppNavigationBar(selectedId = currentScreen)

                        Navigation(currentScreenId = currentScreen.value) {
                            currentScreen.value = it.id
                        }
                    }) {
                    it.calculateBottomPadding()
                    Box(modifier = Modifier.padding(it)) {
                        when (currentScreen.value) {
                            Screen.Home.id -> HomePage()
                            Screen.Profile.id -> ProfilePage()
                            Screen.Setting.id -> SettingPage()
                        }
                    }
                }

            }
        }
    }
}


@Composable
fun AppNavigationBar(selectedId: MutableState<String>) {
    NavigationBar {
        Screen.Items.list.forEach { item ->
            NavigationBarItem(
                selected = item.id == selectedId.value,
                onClick = { selectedId.value = item.id },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) }
            )
        }
    }
}

