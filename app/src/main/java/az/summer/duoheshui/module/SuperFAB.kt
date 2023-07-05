package az.summer.duoheshui.module

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp

private val ExtendedFabStartIconPadding = 16.dp

private val ExtendedFabEndIconPadding = 12.dp

private val ExtendedFabTextPadding = 20.dp

private val ExtendedFabMinimumWidth = 80.dp

const val DurationExtraLong1 = 700.0
const val DurationExtraLong2 = 800.0
const val DurationExtraLong3 = 900.0
const val DurationExtraLong4 = 1000.0
const val DurationLong1 = 450.0
const val DurationLong2 = 500.0
const val DurationLong3 = 550.0
const val DurationLong4 = 600.0
const val DurationMedium1 = 250.0
const val DurationMedium2 = 300.0
const val DurationMedium3 = 350.0
const val DurationMedium4 = 400.0
const val DurationShort1 = 50.0
const val DurationShort2 = 100.0
const val DurationShort3 = 150.0
const val DurationShort4 = 200.0
val EasingEmphasizedCubicBezier = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)
val EasingEmphasizedAccelerateCubicBezier = CubicBezierEasing(0.3f, 0.0f, 0.8f, 0.15f)
val EasingEmphasizedDecelerateCubicBezier = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1.0f)
val EasingLegacyCubicBezier = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
val EasingLegacyAccelerateCubicBezier = CubicBezierEasing(0.4f, 0.0f, 1.0f, 1.0f)
val EasingLegacyDecelerateCubicBezier = CubicBezierEasing(0.0f, 0.0f, 0.2f, 1.0f)
val EasingLinearCubicBezier = CubicBezierEasing(0.0f, 0.0f, 1.0f, 1.0f)
val EasingStandardCubicBezier = CubicBezierEasing(0.2f, 0.0f, 0.0f, 1.0f)
val EasingStandardAccelerateCubicBezier = CubicBezierEasing(0.3f, 0.0f, 1.0f, 1.0f)
val EasingStandardDecelerateCubicBezier = CubicBezierEasing(0.0f, 0.0f, 0.0f, 1.0f)
private val ExtendedFabCollapseAnimation = fadeOut(
    animationSpec = tween(
        durationMillis = DurationShort2.toInt(),
        easing = EasingLinearCubicBezier,
    )
) + shrinkHorizontally(
    animationSpec = tween(
        durationMillis = DurationLong2.toInt(),
        easing = EasingEmphasizedCubicBezier,
    ),
    shrinkTowards = Alignment.Start,
)

private val ExtendedFabExpandAnimation = fadeIn(
    animationSpec = tween(
        durationMillis = DurationShort4.toInt(),
        delayMillis = DurationShort2.toInt(),
        easing = EasingLinearCubicBezier,
    ),
) + expandHorizontally(
    animationSpec = tween(
        durationMillis = DurationLong2.toInt(),
        easing = EasingEmphasizedCubicBezier,
    ),
    expandFrom = Alignment.Start,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SuperFloatingActionButton(
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    expanded: Boolean = true,
    shape: Shape = FloatingActionButtonDefaults.extendedFabShape,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    contentColor: Color = contentColorFor(containerColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    FloatingActionButton(
        onClick = {},
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        interactionSource = interactionSource,
    ) {
        val startPadding = if (expanded) ExtendedFabStartIconPadding else 0.dp
        val endPadding = if (expanded) ExtendedFabTextPadding else 0.dp

        // SuperFAB LongClick
        Box(
            modifier = Modifier
                .combinedClickable(
                    onLongClick =  onLongClick
                ) { onClick() }
                .height(56.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .sizeIn(
                        minWidth = if (expanded) ExtendedFabMinimumWidth
                        else 56.0.dp
                    )
                    .padding(start = startPadding, end = endPadding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = if (expanded) Arrangement.Start else Arrangement.Center
            ) {
                icon()
                AnimatedVisibility(
                    visible = expanded,
                    enter = ExtendedFabExpandAnimation,
                    exit = ExtendedFabCollapseAnimation,
                ) {
                    Row(Modifier.clearAndSetSemantics {}) {
                        Spacer(Modifier.width(ExtendedFabEndIconPadding))
                        text()
                    }
                }
            }
        }
    }
}