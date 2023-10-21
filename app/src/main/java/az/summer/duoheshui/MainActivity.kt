package az.summer.duoheshui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import az.summer.duoheshui.module.AboutStar
import az.summer.duoheshui.ui.theme.DuoheshuiTheme
import az.summer.duoheshui.ui.theme.SansFamily
import az.summer.duoheshui.ui.theme.navigation.Navigation
import az.summer.duoheshui.ui.theme.navigation.Screen
import az.summer.duoheshui.ui.theme.screen.HomePage
import az.summer.duoheshui.ui.theme.screen.ProfilePage
import az.summer.duoheshui.ui.theme.screen.SettingPage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import java.text.ParseException
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter

var numberOfDays: Int = 0

class MainActivity : ComponentActivity() {
    lateinit var prefs: SharedPreferences
    override fun onDestroy() {
        super.onDestroy()
        val last = LocalDate.now()
        prefs.edit().putString("last_exit", last.toString()).apply()
        Log.d("last", last.toString())
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentScreen = mutableStateOf(Screen.Home.id)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        prefs = getSharedPreferences("cal", MODE_PRIVATE)
        val lastExit = prefs.getString("last_exit", "2023-06-01")
        try {
            val now = LocalDate.now()
            val diffNumber = Duration.between(
                LocalDate.parse(lastExit, DateTimeFormatter.ISO_DATE).atStartOfDay(),
                now.atStartOfDay()
            ).toDays()
            numberOfDays = diffNumber.toInt()
            Log.d("diff", numberOfDays.toString())
        } catch (e: ParseException) {
            // 处理异常
            e.printStackTrace()
        }

        setContent {
            DuoheshuiTheme {
                var openLoveDialog by remember { mutableStateOf(false) }
                // A surface container using the 'background' color from the theme
                val context = LocalContext.current
                val navController = rememberNavController()

                Surface(
                    color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(topBar = {
                        if (openLoveDialog) {
                            Dialog(
                                onDismissRequest = { openLoveDialog = false },
                            ) {
                                Toast.makeText(
                                    context, stringResource(R.string.star), Toast.LENGTH_SHORT
                                ).show()
                                AboutStar()
                            }
                        }
                        TopAppBar(title = {
                            Text(
                                modifier = Modifier.padding(0.dp),
                                text = "多喝水",
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                    fontFamily = SansFamily,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }, actions = {
                            IconButton(onClick = {
                                openLoveDialog = true
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.FavoriteBorder,
                                    contentDescription = "LOVE"
                                )

                            }
                        })

                    }, bottomBar = {
                        Navigation(navController)
                    }) { innerPadding ->
                        NavHost(
                            navController,
                            startDestination = Screen.Home.id,
                            Modifier.padding(innerPadding),
                            enterTransition = {
                                fadeIn(
                                    animationSpec = tween(
                                        120, easing = EaseIn
                                    )
                                )
                            },
                            exitTransition = {
                                fadeOut(
                                    animationSpec = tween(
                                        120, easing = EaseOut
                                    )
                                )
                            }
                        ) {
                            composable(Screen.Home.id) { HomePage() }
                            composable(Screen.Profile.id) { ProfilePage() }
                            composable(Screen.Setting.id) { SettingPage() }
                        }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun Stars() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.star))
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition = composition,
        progress = { progress },
    )
}

@Composable
fun AppNavigationBar(selectedId: MutableState<String>) {
    NavigationBar {
        Screen.Items.list.forEach { item ->
            NavigationBarItem(selected = item.id == selectedId.value,
                onClick = { selectedId.value = item.id },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(text = item.title) })
        }
    }
}