package az.summer.duoheshui.ui.theme.screen

import android.view.SoundEffectConstants
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import az.summer.duoheshui.R
import az.summer.duoheshui.module.CC
import az.summer.duoheshui.module.ShareUtil
import az.summer.duoheshui.module.SuperFloatingActionButton
import az.summer.duoheshui.module.UserPersistentStorage
import az.summer.duoheshui.module.drinkingPost
import az.summer.duoheshui.module.drinkpostmsg
import az.summer.duoheshui.module.enSetDrinkDevice
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import compose.icons.WeatherIcons
import compose.icons.weathericons.Thermometer
import compose.icons.weathericons.ThermometerExterior

@Composable
fun HomePage() {

    //for Toast
    val context = LocalContext.current

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
                icon = { Icon(imageVector = WeatherIcons.Thermometer, contentDescription = "hot") },
                text = {
                    Text(
                        " HOT ",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp)
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
                onLongClick = { Toast.makeText(context, "LongClick", Toast.LENGTH_SHORT).show() }
            )
            Spacer(modifier = Modifier.width(75.dp))
            SuperFloatingActionButton(
                icon = {
                    Icon(
                        imageVector = WeatherIcons.ThermometerExterior,
                        contentDescription = "cold"
                    )
                },
                text = {
                    Text("COLD", style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp))
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