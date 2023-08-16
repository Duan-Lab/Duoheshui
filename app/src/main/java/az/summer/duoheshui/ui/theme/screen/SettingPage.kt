package az.summer.duoheshui.ui.theme.screen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat.startActivity
import az.summer.duoheshui.R
import az.summer.duoheshui.module.CC
import az.summer.duoheshui.module.MainSettingItem
import az.summer.duoheshui.module.SettingItem
import az.summer.duoheshui.module.ShareUtil
import az.summer.duoheshui.module.UserPersistentStorage
import az.summer.duoheshui.module.enMobile
import az.summer.duoheshui.module.enSetDrinkDevice
import az.summer.duoheshui.module.enVcode
import az.summer.duoheshui.module.postmsg
import az.summer.duoheshui.module.posttty
import az.summer.duoheshui.ui.theme.SansFamily
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.brands.Github
import compose.icons.fontawesomeicons.brands.Telegram
import compose.icons.fontawesomeicons.solid.Camera
import compose.icons.fontawesomeicons.solid.Globe
import compose.icons.fontawesomeicons.solid.Wallet

var phoneNum = mutableStateOf(TextFieldValue(""))
var encryptomobile = CC().encrypt(enMobile(phoneNum.component1().text))

var verifyCode = mutableStateOf(TextFieldValue(""))
var encryptocode = CC().encrypt((enVcode(verifyCode.component1().text, phoneNum.component1().text)))

var hotDevice = mutableStateOf(TextFieldValue(""))
var encryptoHotDevice = CC().encrypt(enSetDrinkDevice(hotDevice.component1().text))

var coldDevice = mutableStateOf(TextFieldValue(""))
var encryptoColdDevice = CC().encrypt(enSetDrinkDevice(coldDevice.component1().text))

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun SettingPage() {

    val context = LocalContext.current

    var openDialog by remember { mutableStateOf(false) }
    var openSignIn by remember { mutableStateOf(UserPersistentStorage(context).get()?.token.isNullOrEmpty()) }
    var openDevices by remember { mutableStateOf(false) }
    var openAbout by remember { mutableStateOf(false) }
    var verifyButtonState by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }
    var openLanguages by remember { mutableStateOf(false) }

    @OptIn(ExperimentalPermissionsApi::class)
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    val scanLauncherhotDevice = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result ->
            run {
                if (result.contents == null) {
                    println("null")
                } else {
                    hotDevice.value = TextFieldValue(text = result.contents)
                }
            }
        }
    )
    val scanLaunchercoldDevice = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result ->
            run {
                if (result.contents == null) {
                    println("null")
                } else {
                    coldDevice.value = TextFieldValue(text = result.contents)
                }
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (openDialog) {
            if (openSignIn) {
                Dialog(onDismissRequest = { openDialog = false }) {
                    Surface(
                        modifier = Modifier,
                        shape = RoundedCornerShape(24.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        onClick = { }
                    ) {
                        Column(
                            modifier = Modifier
                                .size(400.dp, 400.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Login",
                                modifier = Modifier,
                                style = MaterialTheme.typography.titleLarge.copy(fontSize = 42.sp),
                                color = MaterialTheme.colorScheme.primary,
                                fontFamily = SansFamily,
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(40.dp))
                            OutlinedTextField(
                                value = phoneNum.value,
                                label = {
                                    Text(
                                        text = "Phone Number",
                                        fontFamily = SansFamily,
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                onValueChange = {
                                    phoneNum.value = it
                                    encryptomobile =
                                        CC().encrypt(enMobile(phoneNum.component1().text))

                                },
                                modifier = Modifier.width(280.dp),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                modifier = Modifier,
                                verticalAlignment = CenterVertically
                            ) {
                                Box(modifier = Modifier.width(160.dp)) {
                                    TextField(
                                        value = verifyCode.value,
                                        label = {
                                            Text(
                                                text = "Verify Code",
                                                fontFamily = SansFamily,
                                                fontWeight = FontWeight.Medium
                                            )
                                        },
                                        onValueChange = {
                                            verifyCode.value = it
                                            encryptocode = CC().encrypt(
                                                (enVcode(
                                                    verifyCode.component1().text,
                                                    phoneNum.component1().text
                                                ))
                                            )
                                        },
                                        modifier = Modifier.focusRequester(focusRequester),
                                        singleLine = true,
                                        colors = TextFieldDefaults.outlinedTextFieldColors(),
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                    )
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                                Box(modifier = Modifier) {

                                    Button(
                                        enabled = verifyButtonState,
                                        onClick = {
                                            posttty(
                                                "sendCode",
                                                postmsg("sendCode", encryptomobile),
                                                context
                                            ) {
                                                verifyButtonState = false
                                            }
                                            focusRequester.requestFocus()
                                        },
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    {
                                        Text(
                                            text = "Verify",
                                            fontFamily = SansFamily,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }

                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            ElevatedButton(onClick = {
                                posttty(
                                    "loginByCode",
                                    postmsg("loginByCode", encryptocode),
                                    context
                                ) {
                                    openSignIn = false
                                }
                            }) {
                                Text(
                                    text = "Let's Go",
                                    fontFamily = SansFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                        }
                    }
                }
            } else {
                Dialog(
                    onDismissRequest = { openDialog = false },
                ) {
                    Surface(
                        modifier = Modifier,
                        shape = RoundedCornerShape(24.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        onClick = { }
                    ) {
                        Column(
                            modifier = Modifier
                                .size(400.dp, 400.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "main",
                                modifier = Modifier.size(150.dp),
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            (
                                    if (UserPersistentStorage(context).get()?.mobile.isNullOrEmpty()) "Login"
                                    else "Stu " + (UserPersistentStorage(
                                        context
                                    ).get()?.mobile?.substring(7) ?: "")).let {
                                Text(
                                    text = it,
                                    modifier = Modifier,
                                    fontSize = 30.sp,
                                    fontFamily = SansFamily,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                        }
                    }

                }
            }
        }
        if (openAbout) Dialog(
            onDismissRequest = { openAbout = false },
        ) {
            Surface(
                modifier = Modifier,
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                onClick = { openAbout = true }
            ) {
                Surface(
                    modifier = Modifier,
                    shape = RoundedCornerShape(24.dp),
                    onClick = { }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(painter = painterResource(R.drawable.logo), contentDescription = "")
                        Text(
                            text = "Duoheshui",
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
                                    startActivity(
                                        context, Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://az0x01-blog.vercel.app/")
                                        ), null
                                    )
                                },
                                modifier = Modifier.size(80.dp)
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
                                    startActivity(
                                        context, Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://github.com/aixiao0621/Duoheshui")
                                        ), null
                                    )
                                },
                                modifier = Modifier.size(80.dp)
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
                                    startActivity(
                                        context,
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("https://t.me/mmnia1")
                                        ), null
                                    )
                                },
                                modifier = Modifier.size(80.dp)
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
        }
        if (openDevices) Dialog(
            onDismissRequest = { openDevices = false },
        ) {
            Surface(
                modifier = Modifier,
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                onClick = { openDevices = true }
            ) {
                Column(
                    modifier = Modifier
                        .size(400.dp, 400.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Devices",
                        modifier = Modifier,
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 36.sp),
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = SansFamily,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Row(modifier = Modifier) {
                        TextField(
                            value = hotDevice.value,
                            label = {
                                Text(text = "Hot Water",
                                     fontFamily = SansFamily,
                                     fontWeight = FontWeight.Medium)
                            },
                            onValueChange = {
                                hotDevice.value = it
//                            encryptoHotDevice =
//                                CC().encrypt(enSetDrinkDevice(hotDevice.component1().text))
                                ShareUtil.putString("hot", hotDevice.value.text, context)
                            },
                            modifier = Modifier.width(230.dp),
                            singleLine = true,
                            colors = TextFieldDefaults.outlinedTextFieldColors()
                        )
                        IconButton(
                            onClick = {
                                cameraPermissionState.launchPermissionRequest()
                                when (cameraPermissionState.status) {
                                    PermissionStatus.Granted -> {
                                        scanLauncherhotDevice.launch(ScanOptions())
                                    }
                                    is PermissionStatus.Denied -> {
                                        Toast.makeText(
                                            context,
                                            "Please grant the camera permission",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }, modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Camera,
                                contentDescription = "camera",
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(top = 18.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(modifier = Modifier) {
                        TextField(
                            value = coldDevice.value,
                            label = {
                                Text(text = "Cold Water",
                                     fontFamily = SansFamily,
                                     fontWeight = FontWeight.Medium)
                            },
                            onValueChange = {
                                coldDevice.value = it
                                ShareUtil.putString("cold", coldDevice.value.text, context)
                            },
                            modifier = Modifier.width(230.dp),
                            singleLine = true,
                            colors = TextFieldDefaults.outlinedTextFieldColors()
                        )
                        IconButton(
                            onClick = {
                                cameraPermissionState.launchPermissionRequest()
                                when (cameraPermissionState.status) {
                                    PermissionStatus.Granted -> {
                                        val options = ScanOptions()
                                        options.setOrientationLocked(true)
                                        options.runCatching { }
                                        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
                                        options.setBeepEnabled(true);
                                        options.setBarcodeImageEnabled(true);

                                        scanLaunchercoldDevice.launch(ScanOptions())
                                    }
                                    is PermissionStatus.Denied -> {
                                        Toast.makeText(
                                            context,
                                            "Please grant camera permission",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }, modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Camera,
                                contentDescription = "camera",
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(top = 18.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(35.dp))
                    ElevatedButton(onClick = { openDevices = false }) {
                        Text(
                            text = "Save",
                            fontFamily = SansFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }
            }
        }
        MainSettingItem(
            modifier = Modifier,
            enable = false,
            selected = true,
            title = if (UserPersistentStorage(context).get()?.token == null) "Setting" else UserPersistentStorage(
                context
            ).get()?.wallet?.balance.toString() + " CNY",
            icon = if (UserPersistentStorage(context).get()?.token == null) Icons.Outlined.Settings else FontAwesomeIcons.Solid.Wallet
        )
        Spacer(modifier = Modifier.height(25.dp))

        SettingItem(
            modifier = Modifier,
            true,
            false,
            title = "Accounts",
            icon = Icons.Outlined.AccountCircle,
            desc = "What could we do?",
            onClick = {
                openDialog = true
                openSignIn = UserPersistentStorage(context).get() == null
            }

        )
        SettingItem(
            modifier = Modifier,
            true,
            false,
            title = "Devices",
            icon = Icons.Outlined.Build,
            desc = "What could we do?",
            onClick = { openDevices = true }
        )
        SettingItem(
            modifier = Modifier,
            true,
            false,
            title = "Color & Style",
            icon = Icons.Outlined.Edit,
            desc = "What could we do?",
            onClick = { }
        )

        SettingItem(
            modifier = Modifier,
            true,
            false,
            title = "Languages",
            icon = Icons.Outlined.Place,
            desc = "What could we do?",
            onClick = { openLanguages = true }
        )

        DropdownMenu(
            expanded = openLanguages,
            offset = DpOffset(30.dp, (-200).dp),
            onDismissRequest = {
                openLanguages = false
            }
        ) {
            DropdownMenuItem(
                text = { Text("English") },
                onClick = { /* Handle edit! */ },
            )
            DropdownMenuItem(
                text = { Text("简体中文") },
                onClick = { /* Handle edit! */ },
            )
            DropdownMenuItem(
                text = { Text("繁體中文") },
                onClick = { /* Handle settings! */ },
            )
        }

        SettingItem(
            modifier = Modifier,
            true,
            false,
            title = "About",
            icon = Icons.Outlined.Info,
            desc = "What could we do?",
            onClick = { openAbout = true }
        )

    }
}