package az.summer.duoheshui.module

import androidx.compose.foundation.layout.width
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp


@Composable
fun VerticalSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    min: Int,
    max: Int,
    onFinished: (Int) -> Unit,
    modifier: Modifier
) {
    Slider(
        modifier = modifier
            .graphicsLayer {
                rotationZ = 270f
                transformOrigin = TransformOrigin(0f, 0f)
            }
            .blur(12.dp)    //visibility
            .width(270.dp)
            .layout { measurable, constraints ->
                val placeable = measurable.measure(
                    Constraints(
                        minWidth = constraints.minHeight,
                        maxWidth = constraints.maxHeight,
                        minHeight = constraints.minWidth,
                        maxHeight = constraints.maxWidth,
                    )
                )
                layout(placeable.height, placeable.width) {
                    placeable.place(-placeable.width, 0)
                }
            },
        value = value,
        valueRange = min.toFloat()..max.toFloat(),
        steps = 5,
        onValueChange = onValueChange,
        onValueChangeFinished = { onFinished(value.toInt()) },

        )
}


