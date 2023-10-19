package az.summer.duoheshui.ui.theme.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import az.summer.duoheshui.R
import az.summer.duoheshui.module.ShareUtil
import az.summer.duoheshui.module.SuperFloatingActionButton
import az.summer.duoheshui.module.TapDeviceWithAction
import az.summer.duoheshui.module.VerticalSlider
import az.summer.duoheshui.module.mix
import az.summer.duoheshui.ui.theme.SansFamily
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import compose.icons.WeatherIcons
import compose.icons.weathericons.Hail
import compose.icons.weathericons.Thermometer
import compose.icons.weathericons.ThermometerExterior
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlin.math.roundToInt


@OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomePage() {
    //for Toast
    val context = LocalContext.current

    val maxDelay = 20f
    var sliderValue by remember { mutableStateOf(ShareUtil.getString("delay", context)?.toFloat() ?: 0f) }

    val coldTap = TapDeviceWithAction(context, ShareUtil.TapDeviceType.COLD)
    val hotTap = TapDeviceWithAction(context, ShareUtil.TapDeviceType.HOT)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = if (sliderValue.equals(0f)) "延时开启" else "延时开启 ${sliderValue.toInt()} 秒",
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.secondary,
                textDecoration = if (sliderValue.equals(0f)) TextDecoration.LineThrough else TextDecoration.None
            ),
            modifier = Modifier.alpha(if (sliderValue.equals(0f)) 0.2f else 1f)
        )
        Slider(
            modifier = Modifier.padding(horizontal = 64.dp),
            value = sliderValue,
            onValueChange = {
                sliderValue = it
                ShareUtil.putString("delay", sliderValue.toString(), context)
            },
            valueRange = 0f..maxDelay,
            steps = maxDelay.roundToInt(),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 64.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "立即",
                style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.tertiary)
            )
            Text(
                text = "${maxDelay.roundToInt()}s",
                style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.tertiary)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Row(modifier = Modifier) {
            SuperFloatingActionButton(
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    focusedElevation = 0.dp,
                    hoveredElevation = 0.dp,
                ),
                disabled = !hotTap.available(),
                icon = { Icon(imageVector = WeatherIcons.Thermometer, contentDescription = "hot") },
                text = {
                    Text(
                        "热水",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp),
                        fontFamily = SansFamily,
                        fontWeight = FontWeight.Medium
                    )
                },
                onClick = { hotTap.on(timeout = sliderValue) },
            )
            Spacer(Modifier.width(25.dp))
            SuperFloatingActionButton(elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                focusedElevation = 0.dp,
                hoveredElevation = 0.dp,
            ), disabled = !hotTap.available() || !coldTap.available(), icon = {
                Icon(
                    imageVector = WeatherIcons.Hail, contentDescription = "mixed"
                )
            }, text = {
                Text(
                    "兑水",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp),
                    fontFamily = SansFamily,
                    fontWeight = FontWeight.Medium
                )
            }, onClick = {
                mix(hotTap, coldTap, timeout = sliderValue)
            })
            Spacer(Modifier.width(25.dp))
            SuperFloatingActionButton(elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                focusedElevation = 0.dp,
                hoveredElevation = 0.dp,
            ), disabled = !coldTap.available(), icon = {
                Icon(
                    imageVector = WeatherIcons.ThermometerExterior, contentDescription = "cold"
                )
            }, text = {
                Text(
                    "凉水",
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 16.sp),
                    fontFamily = SansFamily,
                    fontWeight = FontWeight.Medium
                )
            }, onClick = {
                coldTap.on(timeout = sliderValue)
            })
        }
        Spacer(modifier = Modifier.height(32.dp))
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