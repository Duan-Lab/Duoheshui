package az.summer.duoheshui.ui.theme.screen

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import az.summer.duoheshui.numberOfDays
import az.summer.duoheshui.ui.theme.SansFamily
import com.charts.plotwizard.animation.AnimationType
import com.charts.plotwizard.chartdata.ChartEntry
import com.charts.plotwizard.chartstyle.ChartStyle
import com.charts.plotwizard.ui.Chart
import com.charts.plotwizard.ui.theme.Pink40
import com.charts.plotwizard.ui.theme.Purple80
import kotlinx.coroutines.delay
import org.json.JSONArray

@Composable
fun ProfilePage() {
    val sp: SharedPreferences = LocalContext.current.getSharedPreferences("my_data", MODE_PRIVATE)

    val flag = sp.getBoolean("arr_flag", false)
    if (!flag) {
        val list = doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        sp.edit {
            putString("water_array", JSONArray(list).toString())
            putBoolean("arr_flag", true)
        }
    }

    val dayflag = sp.getBoolean("today_flag", false)
    if (!dayflag) {
        sp.edit {
            putInt("today", 0)
            putBoolean("today_flag", true)
        }
    }

    var temp = sp.getInt("today", 0)

    LocalContext.current

    var today = sp.getInt("today", 0)!!.toFloat() / 1000

    val List = sp.getString("water_array", null)
    var savedList = DoubleArray(JSONArray(List).length()) { JSONArray(List).getDouble(it) }

    Log.d("btwea", numberOfDays.toString())
    repeat(numberOfDays) {
        addDayData(today, savedList, sp)
        Log.d("today", numberOfDays.toString())
    }
    numberOfDays = 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        var count by remember {
            mutableStateOf(temp)
        }
        LaunchedEffect(temp) {
            delay(1000)
            count = sp.getInt("today", 0)
        }
        ClickAdd(
            count = count,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Row(
            modifier = Modifier
                .padding(0.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { count += 100;sp.edit { putInt("today", count) } },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Add,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = " 100",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontFamily = SansFamily,
                    fontWeight = FontWeight.Medium
                )
            }
            Spacer(modifier = Modifier.width(45.dp))
            Button(
                onClick = { count += 300;sp.edit { putInt("today", count) } },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Add,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = " 300",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp,
                    fontFamily = SansFamily,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Column(modifier = Modifier) {
            Char(savedList)
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ClickAdd(
    count: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    var oldCount by remember {
        mutableStateOf(count)
    }
    SideEffect {
        oldCount = count
    }
    Row(modifier = modifier) {
        val countString = "$count ml"
        val oldString = "$oldCount ml"
        for (i in countString.indices) {
            val oldChar = oldString.getOrNull(i)
            val newChar = countString[i]
            val char = if (oldChar == newChar) {
                oldString[i]
            } else {
                countString[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    slideInVertically { it } with slideOutVertically { -it }
                }
            ) { char ->
                Text(
                    text = char.toString(),
                    style = style,
                    softWrap = false,
                    fontSize = 68.sp,
                    color = color,
                    fontFamily = SansFamily,
                    fontWeight = FontWeight.Medium
                )

            }
        }
    }
}

@Composable
fun addDayData(num: Float, savedList: DoubleArray, sp: SharedPreferences) {
    for (i in savedList.size - 1 downTo 1) {
        savedList[i] = savedList[i - 1]
    }
    savedList[0] = num.toDouble()
    sp.edit { putString("water_array", JSONArray(savedList).toString()) }
    sp.edit { putInt("today", 0) }
}

@Composable
fun Char(savedList: DoubleArray) {
    fun MockRangeList() = listOf(
        ChartEntry.RangeBar(0F, savedList[8].toFloat()),
        ChartEntry.RangeBar(0F, savedList[7].toFloat()),
        ChartEntry.RangeBar(0F, savedList[6].toFloat()),
        ChartEntry.RangeBar(0F, savedList[5].toFloat()),
        ChartEntry.RangeBar(0F, savedList[4].toFloat()),
        ChartEntry.RangeBar(0F, savedList[3].toFloat()),
        ChartEntry.RangeBar(0F, savedList[2].toFloat()),
        ChartEntry.RangeBar(0F, savedList[1].toFloat()),
        ChartEntry.RangeBar(0F, savedList[0].toFloat()),
    )
    Row {
        Button(onClick = {
        }) {
            Icon(imageVector = Icons.TwoTone.DateRange, contentDescription = "")
            Text(
                text = "              ",
                textAlign = TextAlign.End,
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .size(325.dp)
    ) {
        Chart(
            chartListData = MockRangeList(),
            animationType = AnimationType.Bouncy(10F),
            chartStyle = ChartStyle.BarChartStyle(
                chartBrush = listOf(Pink40, Purple80),
                barCornerRadius = 20F,
                chartValueTextColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}
