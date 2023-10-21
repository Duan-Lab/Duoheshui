package az.summer.duoheshui.ui.theme.screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import az.summer.duoheshui.module.About
import az.summer.duoheshui.module.CC
import az.summer.duoheshui.module.MainSettingItem
import az.summer.duoheshui.module.SettingItem
import az.summer.duoheshui.module.ShareUtil
import az.summer.duoheshui.module.UserBalanceStorage
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
import compose.icons.FeatherIcons
import compose.icons.FontAwesomeIcons
import compose.icons.feathericons.CreditCard
import compose.icons.feathericons.Settings
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Camera
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var phoneNum = mutableStateOf(TextFieldValue(""))
var encryptomobile = CC().encrypt(enMobile(phoneNum.component1().text))
var verifyCode = mutableStateOf(TextFieldValue(""))
var encryptocode = CC().encrypt((enVcode(verifyCode.component1().text, phoneNum.component1().text)))
var hotDevice = mutableStateOf(TextFieldValue(""))
var encryptoHotDevice = CC().encrypt(enSetDrinkDevice(hotDevice.component1().text))
var coldDevice = mutableStateOf(TextFieldValue(""))
var encryptoColdDevice = CC().encrypt(enSetDrinkDevice(coldDevice.component1().text))

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class,
    DelicateCoroutinesApi::class
)
@Composable
fun SettingPage() {

    val context = LocalContext.current

    var openDialog by remember { mutableStateOf(false) }
    var openSignIn by remember { mutableStateOf(UserPersistentStorage(context).get()?.token.isNullOrEmpty()) }
    var openDevices by remember { mutableStateOf(false) }
    var openAbout by remember { mutableStateOf(false) }
    var verifyButtonState by remember { mutableStateOf(true) }
    val focusRequester = remember { FocusRequester() }

    @OptIn(ExperimentalPermissionsApi::class) val cameraPermissionState =
        rememberPermissionState(android.Manifest.permission.CAMERA)

    val scanLauncherhotDevice =
        rememberLauncherForActivityResult(contract = ScanContract(), onResult = { result ->
            run {
                if (result.contents == null) {
                    Toast.makeText(
                        context, "null", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    hotDevice.value = TextFieldValue(text = result.contents)
                    ShareUtil.putString("hot", hotDevice.value.text, context)
                }
            }
        })
    val scanLaunchercoldDevice =
        rememberLauncherForActivityResult(contract = ScanContract(), onResult = { result ->
            run {
                if (result.contents == null) {
                    Toast.makeText(
                        context, "null", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    coldDevice.value = TextFieldValue(text = result.contents)
                    ShareUtil.putString("cold", coldDevice.value.text, context)
                }
            }
        })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        if (openDialog) {
            if (openSignIn) {
                AlertDialog(onDismissRequest = { openDialog = false },
                    title = { Text("登录") },
                    dismissButton = {
                        TextButton(onClick = { openDialog = false }) {
                            Text(
                                text = "关闭"
                            )
                        }
                    },
                    confirmButton = {
                        Button(enabled = !verifyCode.component1().text.isEmpty(), onClick = {
                            posttty(
                                "loginByCode",
                                postmsg("loginByCode", encryptocode),
                                context,
                                cbOnLogin = {
                                    openDialog = false
                                }
                            )
                        }) {
                            Text(
                                text = "启动", fontFamily = SansFamily, fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    text = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            OutlinedTextField(
                                value = phoneNum.value,
                                label = {
                                    Text(
                                        text = "手机号码",
                                        fontFamily = SansFamily,
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                onValueChange = {
                                    if (it.text.length > 11) {
                                        return@OutlinedTextField
                                    }
                                    phoneNum.value = it
                                    encryptomobile =
                                        CC().encrypt(enMobile(it.text))
                                    verifyButtonState = true

                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                verticalAlignment = CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = verifyCode.value,
                                    label = {
                                        Text(
                                            text = "验证码",
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
                                    modifier = Modifier.focusRequester(focusRequester).weight(2f),
                                    singleLine = true,
                                    colors = TextFieldDefaults.outlinedTextFieldColors(),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                FilledTonalButton(
                                    modifier = Modifier
                                        .height(52.dp)
                                        .offset(y = 3.dp)
                                        .weight(1f),
                                    enabled = verifyButtonState && phoneNum.value.text.length == 11,
                                    onClick = {
                                        posttty(
                                            "sendCode",
                                            postmsg("sendCode", encryptomobile),
                                            context
                                        ) {
                                            verifyButtonState = false
                                            GlobalScope.launch(context = Dispatchers.IO) {
                                                delay(5000)
                                                verifyButtonState = true
                                            }
                                        }
                                        focusRequester.requestFocus()
                                    },
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text(
                                        text = "发送",
                                        fontFamily = SansFamily,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    })
            } else {
                Dialog(
                    onDismissRequest = { openDialog = false },
                ) {
                    Surface(
                        color = AlertDialogDefaults.containerColor,
                        tonalElevation = AlertDialogDefaults.TonalElevation,
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Column(
                            modifier = Modifier.size(400.dp, 400.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "main",
                                modifier = Modifier.size(150.dp),
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            ("Stu " + (UserPersistentStorage(
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
                            Spacer(modifier = Modifier.height(20.dp))
                            FilledTonalButton(onClick = {
                                UserPersistentStorage(context).clear()
                                UserBalanceStorage(context).clear()
                                openSignIn =
                                    UserPersistentStorage(context).get()?.token.isNullOrEmpty()
                            }) {
                                Text(text = "退出登录")
                            }
                        }
                    }
                }
            }
        }
        if (openAbout) {
            Dialog(
                onDismissRequest = { openAbout = false },
            ) {
                About()
            }
        }
        if (openDevices) {
            AlertDialog(title = { Text("水龙头") }, confirmButton = {
                TextButton(onClick = { openDevices = false }) {
                    Text(
                        text = "保存"
                    )
                }
            }, onDismissRequest = { openDevices = false }, text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(modifier = Modifier) {
                        TextField(
                            value = hotDevice.value,
                            label = {
                                Text(
                                    text = "热水",
                                    fontFamily = SansFamily,
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            onValueChange = {
                                hotDevice.value = it
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
                                            context, "请提供相机权限", Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }, modifier = Modifier
                                .size(48.dp)
                                .padding(start = 16.dp)
                        ) {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Camera,
                                contentDescription = "camera",
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(top = 18.dp),
                                tint = MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.9f
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(modifier = Modifier) {
                        TextField(
                            value = coldDevice.value,
                            label = {
                                Text(
                                    text = "凉水",
                                    fontFamily = SansFamily,
                                    fontWeight = FontWeight.Medium
                                )
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
                            }, modifier = Modifier
                                .size(48.dp)
                                .padding(start = 16.dp)
                        ) {
                            Icon(
                                imageVector = FontAwesomeIcons.Solid.Camera,
                                contentDescription = "camera",
                                modifier = Modifier
                                    .size(48.dp)
                                    .padding(top = 18.dp),
                                tint = MaterialTheme.colorScheme.primary.copy(
                                    alpha = 0.9f
                                )
                            )
                        }
                    }
                }
            })
        }
        if (
            (UserBalanceStorage(
                context
            ).get()?.wallet?.balance.isNullOrEmpty())
        ) {
            MainSettingItem(
                selected = true,
                title = "设置",
                icon = FeatherIcons.Settings,
                baselineShift = BaselineShift(0.05f)
            )
        } else {
            MainSettingItem(
                selected = true,
                title = UserBalanceStorage(
                    context
                ).get()?.wallet!!.balance + " CNY",
                icon = FeatherIcons.CreditCard
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        SettingItem(modifier = Modifier,
            true,
            false,
            title = "账户",
            icon = Icons.Outlined.AccountCircle,
            desc = "登录账户",
            onClick = {
                openDialog = true
                openSignIn = UserPersistentStorage(context).get() == null
            }

        )
        SettingItem(modifier = Modifier,
            true,
            false,
            title = "水龙头",
            icon = Icons.Outlined.Build,
            desc = "添加和修改凉水/热水设备",
            onClick = { openDevices = true })
        SettingItem(modifier = Modifier,
            true,
            false,
            title = "关于",
            icon = Icons.Outlined.Info,
            desc = "What could we do?",
            onClick = { openAbout = true })
    }
}
