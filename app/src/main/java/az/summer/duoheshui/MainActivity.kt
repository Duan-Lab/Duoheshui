package az.summer.duoheshui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import az.summer.duoheshui.ui.theme.DuoheshuiTheme
import az.summer.duoheshui.ui.theme.navigation.Navigation
import az.summer.duoheshui.ui.theme.navigation.Screen
import az.summer.duoheshui.ui.theme.screen.HomePage
import az.summer.duoheshui.ui.theme.screen.ProfilePage
import az.summer.duoheshui.ui.theme.screen.SettingPage
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.AllIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.brands.Github
import compose.icons.fontawesomeicons.brands.Telegram

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentScreen = mutableStateOf(Screen.Home.id)

        setContent {
            DuoheshuiTheme {
                var openLoveDialog by remember { mutableStateOf(false) }
                // A surface container using the 'background' color from the theme
                val context = LocalContext.current

                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Scaffold(
                        topBar = {
                            if (openLoveDialog) {
                                Dialog(
                                    onDismissRequest = { openLoveDialog = false },
                                ) {
                                    Toast.makeText(
                                        context,
                                        "Please give me a star",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Surface(
                                        modifier = Modifier,
                                        shape = RoundedCornerShape(24.dp),
                                        onClick = { }
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(MaterialTheme.colorScheme.surfaceVariant),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Spacer(modifier = Modifier.height(28.dp))
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_pure),
                                                null,
                                                tint = Color.Yellow.copy(alpha = 0.99f)
                                            )
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = "Duoheshui",
                                                textAlign = TextAlign.Right,
                                                modifier = Modifier.align(CenterHorizontally),
                                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                                                style = MaterialTheme.typography.titleLarge.copy(
                                                    fontSize = 36.sp
                                                ),
                                            )
                                            Spacer(modifier = Modifier.height(60.dp))
                                            Row() {
                                                IconButton(
                                                    onClick = {
                                                        Toast.makeText(
                                                            context,
                                                            "Give me a star",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    },
                                                    modifier = Modifier.size(80.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = FontAwesomeIcons.Regular.AllIcons[6],
                                                        contentDescription = "like",
                                                        modifier = Modifier.size(48.dp),
                                                        tint = MaterialTheme.colorScheme.primary.copy(
                                                            alpha = 0.9f
                                                        )
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(16.dp))
                                                IconButton(
                                                    onClick = {
                                                        startActivity(
                                                            Intent(
                                                                Intent.ACTION_VIEW,
                                                                Uri.parse("https://github.com/aixiao0621/Duoheshui")
                                                            )
                                                        )
                                                    },
                                                    modifier = Modifier.size(80.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = FontAwesomeIcons.Brands.Github,
                                                        contentDescription = "github",
                                                        modifier = Modifier.size(48.dp),
                                                        tint = MaterialTheme.colorScheme.primary.copy(
                                                            alpha = 0.9f
                                                        )
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(16.dp))
                                                IconButton(
                                                    onClick = {
                                                        startActivity(
                                                            Intent(
                                                                Intent.ACTION_VIEW,
                                                                Uri.parse("https://t.me/mmnia1")
                                                            )
                                                        )
                                                    },
                                                    modifier = Modifier.size(80.dp)
                                                ) {
                                                    Icon(
                                                        imageVector = FontAwesomeIcons.Brands.Telegram,
                                                        contentDescription = "telegram",
                                                        modifier = Modifier.size(48.dp),
                                                        tint = MaterialTheme.colorScheme.primary.copy(
                                                            alpha = 0.9f
                                                        )
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(24.dp))
                                        }
                                    }

                                }
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
    