package az.summer.duoheshui.ui.theme.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun Navigation(
    currentScreenId: String,
    handleSelected: (Screen) -> Unit
) {
    val items = Screen.Items.list

    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
            .padding(12.dp, 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            NavItem(item = item, isSelected = item.id == currentScreenId) {
                handleSelected(item)
            }

        }
    }
}

@Composable
fun NavItem(
    item: Screen,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val selectedBackgroundColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
    val background = if (isSelected) selectedBackgroundColor else Color.Transparent
    val contentColorFir = MaterialTheme.colorScheme.primary.copy(0.75f)
    val contentColor = if (isSelected) contentColorFir else MaterialTheme.colorScheme.onBackground

    //弹簧效果动画
    val navigationAnimationSpring = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMediumLow,
        visibilityThreshold = IntSize.VisibilityThreshold
    )

    Row(
        modifier = Modifier
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = null,
            tint = contentColor
        )

        AnimatedVisibility(
            visible = isSelected,
            //弹簧动画
            enter = expandHorizontally(navigationAnimationSpring),
            exit = shrinkHorizontally()

        ) {
            Text(
                text = item.title,
                modifier = Modifier.padding(start = 4.dp),
                color = contentColor
            )
        }
    }

}

@Composable
@Preview
fun Prev1() {
    Navigation(currentScreenId = Screen.Home.id) {

    }
}

@Composable
@Preview
fun Prev2() {
    NavItem(item = Screen.Home, isSelected = true) {

    }
}