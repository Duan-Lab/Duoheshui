package az.summer.duoheshui.module

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import az.summer.duoheshui.R
import az.summer.duoheshui.Stars
import az.summer.duoheshui.ui.theme.SansFamily
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.AllIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.brands.Github
import compose.icons.fontawesomeicons.brands.Telegram
import compose.icons.fontawesomeicons.solid.Globe

@Composable
fun AboutStar() {
    val context = LocalContext.current

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = AlertDialogDefaults.containerColor,
        tonalElevation = AlertDialogDefaults.TonalElevation
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .size(240.dp)
                    .fillMaxSize(), propagateMinConstraints = false
            ) {
                Stars()
            }
            Text(
                text = "多喝水",
                textAlign = TextAlign.Right,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 36.sp
                ),
                fontFamily = SansFamily,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(60.dp))
            Row() {
                IconButton(
                    onClick = {
                        Toast.makeText(
                            context, "Bo~", Toast.LENGTH_SHORT
                        ).show()
                    }, modifier = Modifier.size(80.dp)
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
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/aixiao0621/Duoheshui")
                            )
                        )
                    }, modifier = Modifier.size(80.dp)
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
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW, Uri.parse("https://t.me/mmnia1")
                            )
                        )
                    }, modifier = Modifier.size(80.dp)
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

@Composable
fun About() {
    val context = LocalContext.current

    Surface(
        shape = RoundedCornerShape(24.dp),
        color = AlertDialogDefaults.containerColor,
        tonalElevation = AlertDialogDefaults.TonalElevation
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo), contentDescription = ""
            )
            Text(
                text = "多喝水",
                textAlign = TextAlign.Right,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 38.sp
                ),
                fontFamily = SansFamily,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(60.dp))
            Row() {
                IconButton(
                    onClick = {
                        ContextCompat.startActivity(
                            context, Intent(
                                Intent.ACTION_VIEW, Uri.parse("https://az0x01-blog.vercel.app/")
                            ), null
                        )
                    }, modifier = Modifier.size(80.dp)
                ) {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Globe,
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
                        ContextCompat.startActivity(
                            context, Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/aixiao0621/Duoheshui")
                            ), null
                        )
                    }, modifier = Modifier.size(80.dp)
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
                        ContextCompat.startActivity(
                            context, Intent(
                                Intent.ACTION_VIEW, Uri.parse("https://t.me/mmnia1")
                            ), null
                        )
                    }, modifier = Modifier.size(80.dp)
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