package az.summer.duoheshui.ui.theme.screen

import android.content.Context
import android.content.SharedPreferences
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.core.content.edit
import az.summer.duoheshui.R
import az.summer.duoheshui.module.CC
import az.summer.duoheshui.module.ShareUtil
import az.summer.duoheshui.module.SuperFloatingActionButton
import az.summer.duoheshui.module.UserPersistentStorage
import az.summer.duoheshui.module.VerticalSlider
import az.summer.duoheshui.module.drinkingPost
import az.summer.duoheshui.module.drinkpostmsg
import az.summer.duoheshui.module.enSetDrinkDevice
import az.summer.duoheshui.numberOfDays
import az.summer.duoheshui.ui.theme.SansFamily
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import compose.icons.WeatherIcons
import compose.icons.weathericons.Thermometer
import compose.icons.weathericons.ThermometerExterior
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun HomePage() {
    //for Toast
    val context = LocalContext.current

    var showPopup by remember { mutableStateOf(false) }
    var sliderValue by remember { mutableStateOf(0f) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.size(400.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Loader()
        }
        Spacer(modifier = Modifier.height(75.dp))
        Row(modifier = Modifier) {
            SuperFloatingActionButton(
                modifier = Modifier.pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = { // 长按时设置showPopup为true
                            showPopup = true
                            Modifier
                        },
                        onDragEnd = { // 松开时设置showPopup为false
                            Toast.makeText(
                                context,
                                "${sliderValue.toInt()} seconds later",
                                Toast.LENGTH_SHORT
                            ).show()
                            showPopup = false
                            GlobalScope.launch(context = Dispatchers.IO) {
                                delay(sliderValue.toLong() * 1000)
                                encryptoHotDevice =
                                    CC().encrypt(
                                        enSetDrinkDevice(
                                            ShareUtil.getString("hot", context).toString()
                                        )
                                    )
                                drinkingPost(
                                    "send_command/send",
                                    drinkpostmsg(
                                        "send",
                                        encryptoHotDevice,
                                        UserPersistentStorage(context).get()?.token.toString()
                                    ),
                                    context
                                )
                                Looper.prepare()
                                Toast.makeText(context, "Di", Toast.LENGTH_SHORT).show()
                                Looper.loop()
                            }
                        },
                        onDrag = { change, dragAmount -> // 拖动时更新sliderValue的值
                            change.consume()
                            sliderValue =
                                (sliderValue - dragAmount.y / 48f).coerceIn(0f, 30f)
                        },
                    )
                },
                icon = { Icon(imageVector = WeatherIcons.Thermometer, contentDescription = "hot") },
                text = {
                    Text(
                        " HOT ",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp),
                        fontFamily = SansFamily,
                        fontWeight = FontWeight.Medium
                    )
                },
                onClick = {
                    if (UserPersistentStorage(context).get() == null) Toast.makeText(
                        context,
                        "Please login",
                        Toast.LENGTH_SHORT
                    ).show()
                    else {
                        Toast.makeText(context, "Wait...", Toast.LENGTH_SHORT).show()
                        encryptoHotDevice =
                            CC().encrypt(
                                enSetDrinkDevice(
                                    ShareUtil.getString("hot", context).toString()
                                )
                            )
                        drinkingPost(
                            "send_command/send",
                            drinkpostmsg(
                                "send",
                                encryptoHotDevice,
                                UserPersistentStorage(context).get()?.token.toString()
                            ),
                            context
                        )
                    }

                },

                )
            Spacer(modifier = Modifier.width(75.dp))
            SuperFloatingActionButton(
                modifier = Modifier.pointerInput(Unit) {
                    detectDragGestures(
                        onDragStart = {
                            showPopup = true
                            Modifier
                        },
                        onDragEnd = {
                            Toast.makeText(
                                context,
                                "${sliderValue.toInt()} seconds later",
                                Toast.LENGTH_SHORT
                            ).show()
                            showPopup = false

                            GlobalScope.launch(context = Dispatchers.IO) {
                                delay(sliderValue.toLong() * 1000)
                                encryptoColdDevice =
                                    CC().encrypt(
                                        enSetDrinkDevice(
                                            ShareUtil.getString("cold", context).toString()
                                        )
                                    )
                                drinkingPost(
                                    "send_command/send",
                                    drinkpostmsg(
                                        "send",
                                        encryptoColdDevice,
                                        UserPersistentStorage(context).get()?.token.toString()
                                    ),
                                    context
                                )
                                Looper.prepare()
                                Toast.makeText(context, "Di", Toast.LENGTH_SHORT).show()
                                Looper.loop()
                            }
                            Thread.sleep(1000)

                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            sliderValue =
                                (sliderValue - dragAmount.y / 48f).coerceIn(0f, 30f)
                        },
                    )
                },
                icon = {
                    Icon(
                        imageVector = WeatherIcons.ThermometerExterior,
                        contentDescription = "cold"
                    )
                },
                text = {
                    Text(
                        "COLD",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp),
                        fontFamily = SansFamily,
                        fontWeight = FontWeight.Medium
                    )
                },
                onClick = {
                    if (UserPersistentStorage(context).get()?.token == null) Toast.makeText(
                        context,
                        "Please login",
                        Toast.LENGTH_SHORT
                    ).show()
                    else {
                        Toast.makeText(context, "Wait...", Toast.LENGTH_SHORT).show()
                        encryptoColdDevice =
                            CC().encrypt(
                                enSetDrinkDevice(
                                    ShareUtil.getString("cold", context).toString()
                                )
                            )
                        drinkingPost(
                            "send_command/send",
                            drinkpostmsg(
                                "send",
                                encryptoColdDevice,
                                UserPersistentStorage(context).get()?.token.toString()
                            ),
                            context
                        )
                    }
                },
                onLongClick = {
                    val sharedToken = UserPersistentStorage(context).get()?.wallet?.balance
                    if (sharedToken.isNullOrEmpty()) {
                        Toast.makeText(context, "Token void", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, sharedToken, Toast.LENGTH_SHORT).show()
                    }
                },

                )
        }
    }
    if (showPopup) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Surface(
                modifier = Modifier.size(270.dp, 380.dp),
                shape = RoundedCornerShape(48.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                content = {
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${sliderValue.toInt()}″",
                            modifier = Modifier,
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 86.sp),
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = SansFamily,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(30.dp))
                        Popup(
                            alignment = Alignment.Center,
                            onDismissRequest = { showPopup = false },
                        ) {
                            VerticalSlider(
                                value = sliderValue,
                                onValueChange = { sliderValue = it },
                                min = 0,
                                max = 30,
                                onFinished = { value ->
                                    Toast.makeText(context, "Wait...$value", Toast.LENGTH_SHORT)
                                        .show()
                                    showPopup = false

                                },
                                modifier = Modifier
                                    .height(380.dp)
                                    .blur(36.dp)
                            )
                        }
                    }

                })
        }
    }
}

@Preview
@Composable
fun Loader() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.clock))
    val progress by animateLottieCompositionAsState(composition)
    LottieAnimation(
        composition = composition,
        progress = { progress },
    )
}