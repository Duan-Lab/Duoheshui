package az.summer.duoheshui.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import az.summer.duoheshui.module.CC
import az.summer.duoheshui.module.MainSettingItem
import az.summer.duoheshui.module.SettingItem
import az.summer.duoheshui.module.ShareUtil
import az.summer.duoheshui.module.UserPersistentStorage
import az.summer.duoheshui.module.enVcode
import az.summer.duoheshui.module.enMobile
import az.summer.duoheshui.module.enSetDrinkDevice
import az.summer.duoheshui.module.postmsg
import az.summer.duoheshui.module.posttty
import compose.icons.WeatherIcons
import compose.icons.weathericons.Sunrise


var phoneNum = mutableStateOf(TextFieldValue(""))
var encryptomobile = CC().encrypt(enMobile(phoneNum.component1().text))

var verifyCode = mutableStateOf(TextFieldValue(""))
var encryptocode = CC().encrypt((enVcode(verifyCode.component1().text, phoneNum.component1().text)))

var hotDevice = mutableStateOf(TextFieldValue(""))
var encryptoHotDevice = CC().encrypt(enSetDrinkDevice(hotDevice.component1().text))

var coldDevice = mutableStateOf(TextFieldValue(""))
var encryptoColdDevice = CC().encrypt(enSetDrinkDevice(coldDevice.component1().text))


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingPage() {

    val context = LocalContext.current

    var openDialog by remember { mutableStateOf(false) }
    var openSignIn by remember { mutableStateOf(UserPersistentStorage(context).get() == null) }
    var openDevices by remember { mutableStateOf(false) }
    var openAbout by remember { mutableStateOf(false) }
    var verifyButtonState by remember { mutableStateOf(true) }


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
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Column(
                            modifier = Modifier
                                .size(400.dp, 400.dp)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Log in",
                                modifier = Modifier,
                                style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(40.dp))
                            OutlinedTextField(
                                value = phoneNum.value,
                                label = { Text(text = "Phone Number") },
                                onValueChange = {
                                    phoneNum.value = it
                                    encryptomobile =
                                        CC().encrypt(enMobile(phoneNum.component1().text))

                                },
                                modifier = Modifier.width(280.dp),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(20.dp))

                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(modifier = Modifier.width(160.dp)) {
                                    TextField(
                                        value = verifyCode.value,
                                        label = { Text(text = "Verify Code") },
                                        onValueChange = {
                                            verifyCode.value = it
                                            encryptocode = CC().encrypt(
                                                (enVcode(
                                                    verifyCode.component1().text,
                                                    phoneNum.component1().text
                                                ))
                                            )
                                        },
                                        singleLine = true,
                                        colors = TextFieldDefaults.outlinedTextFieldColors(),

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
                                        },
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    {
                                        Text(text = "Verify")
                                    }
                                }

                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            ElevatedButton(onClick = {
                                posttty(
                                    "loginByCode",
                                    postmsg("loginByCode", encryptocode),
                                    context
                                ) {
                                    openSignIn = false
                                }
                            }) {
                                Text(text = "Let's Go")
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
                        color = MaterialTheme.colorScheme.primaryContainer
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
                                    if (UserPersistentStorage(context).get()?.mobile.isNullOrEmpty()) "Log in"
                                    else "Stu_" + (UserPersistentStorage(
                                        context
                                    ).get()?.mobile?.substring(7) ?: "")).let {
                                Text(
                                    text = it,
                                    modifier = Modifier,
                                    fontSize = 30.sp
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
                Column(
                    modifier = Modifier
                        .size(400.dp, 400.dp)
                        .background(MaterialTheme.colorScheme.inversePrimary),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = WeatherIcons.Sunrise,
                        contentDescription = "about",
                        modifier = Modifier.size(150.dp),
                    )
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
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    OutlinedTextField(
                        value = hotDevice.value,
                        label = {
                            Text(
                                text =
                                if (ShareUtil.getString("hot", context).toString()
                                        .isNullOrEmpty()
                                ) "Hot Water"
                                else if (ShareUtil.getString("hot", context)
                                        .toString().length < 9
                                ) "Hot Water" else ShareUtil.getString("hot", context).toString()
                            )
                        },
                        onValueChange = {
                            hotDevice.value = it
//                            encryptoHotDevice =
//                                CC().encrypt(enSetDrinkDevice(hotDevice.component1().text))
                            ShareUtil.putString("hot", hotDevice.value.text, context)
                        },
                        modifier = Modifier.width(280.dp),
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        value = coldDevice.value,
                        label = {
                            Text(
                                text =
                                if (ShareUtil.getString("cold", context).toString()
                                        .isNullOrEmpty()
                                ) "Cold Water"
                                else if (ShareUtil.getString("cold", context)
                                        .toString().length < 10
                                ) "Cold Water" else ShareUtil.getString("cold", context).toString()
                            )
                        },
                        onValueChange = {
                            coldDevice.value = it
                            ShareUtil.putString("cold", coldDevice.value.text, context)
                        },
                        modifier = Modifier.width(280.dp),
                        singleLine = true,

                        )
                    Spacer(modifier = Modifier.height(20.dp))
                    ElevatedButton(onClick = { openDevices = false }) {
                        Text(text = "Let's Go")
                    }

                }
            }
        }
        MainSettingItem(
            modifier = Modifier,
            enable = false,
            selected = true,
            title = "Setting",
            icon = Icons.Outlined.Settings,
            onClick = {
//                Toast.makeText(context, "Setting", Toast.LENGTH_SHORT).show()
            }

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
            title = "Color&Style",
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
            onClick = { }
        )

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