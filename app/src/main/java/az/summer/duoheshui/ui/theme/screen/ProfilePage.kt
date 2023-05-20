package az.summer.duoheshui.ui.theme.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import az.summer.duoheshui.module.Char

@Composable
fun ProfilePage (){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        var count by remember {
            mutableStateOf(0)
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
                onClick = { count += 100 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "+100",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.width(45.dp))
            Button(
                onClick = { count += 300 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "+300",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )
            }
        }
        Column(modifier = Modifier) {
            Char()
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ClickAdd(
    count: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    color: Color = MaterialTheme.colorScheme.primary
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
            AnimatedContent(targetState = char,
                transitionSpec = {
                    slideInVertically { it } with slideOutVertically { -it }
                }
            ) { char ->
                Text(
                    text = char.toString(),
                    style = style,
                    softWrap = false,
                    fontSize = 60.sp,
                    color = color
                )

            }
        }
    }
}