package az.summer.duoheshui.module

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.charts.plotwizard.animation.AnimationType
import com.charts.plotwizard.chartdata.ChartEntry
import com.charts.plotwizard.chartstyle.ChartStyle
import com.charts.plotwizard.ui.Chart
import com.charts.plotwizard.ui.theme.Pink40
import com.charts.plotwizard.ui.theme.Purple80

@Composable
fun Char() {
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
var day = 1.2F
fun MockRangeList() = listOf(
    ChartEntry.RangeBar(0F, 0.5F),
    ChartEntry.RangeBar(0F, 0.7F),
    ChartEntry.RangeBar(0F, 2F),
    ChartEntry.RangeBar(0F, 1.6F),
    ChartEntry.RangeBar(0F, 1.8F),
    ChartEntry.RangeBar(0F, 1F),
    ChartEntry.RangeBar(0F, 1.8F),
    ChartEntry.RangeBar(0F, 1F),
    ChartEntry.RangeBar(0F, day),
)
