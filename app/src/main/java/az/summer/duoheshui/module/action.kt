package az.summer.duoheshui.module

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import az.summer.duoheshui.ui.theme.screen.encryptoColdDevice
import az.summer.duoheshui.ui.theme.screen.encryptoHotDevice
import az.summer.duoheshui.ui.theme.screen.encryptomobile
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TapDeviceWithAction(val context: Context, private val type: ShareUtil.TapDeviceType) {
    private val looper = Looper.getMainLooper()

    fun available(): Boolean {
        return UserPersistentStorage(context).get()?.token != null &&
                ShareUtil.getString(type.type, context) != null
    }

    fun onSync() {
        Handler(looper).post {
            Toast.makeText(context, "Wait...", Toast.LENGTH_SHORT).show()
        }
        encryptoHotDevice =
            CC().encrypt(
                enSetDrinkDevice(
                    ShareUtil.getString("hot", context).toString()
                )
            )
        encryptoColdDevice =
            CC().encrypt(
                enSetDrinkDevice(
                    ShareUtil.getString("cold", context).toString()
                )
            )
        drinkingPost(
            "send_command/send",
            drinkpostmsg(
                "send",
                if (type == ShareUtil.TapDeviceType.COLD) encryptoColdDevice else encryptoHotDevice,
                UserPersistentStorage(context).get()?.token.toString()
            ),
            context
        )
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun on() {
        if (UserPersistentStorage(context).get()?.token == null) {
            Handler(looper).post {
                Toast.makeText(
                    context,
                    "Please login",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else if (ShareUtil.getString(type.type, context) == null) {
            Handler(looper).post {
                Toast.makeText(
                    context,
                    "Please set ${type.type} tap device",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            GlobalScope.launch(context = Dispatchers.IO) {
                this@TapDeviceWithAction.onSync()
            }
        }
    }
}

fun waitTillTolled(context: Context, maxTries: Int = 40, retryInterval: Int = 500) =
    runBlocking<Boolean> {
        val initialBalance = UserBalanceStorage(context).get()?.wallet?.balance?.toFloatOrNull()
        if (initialBalance == null) {
            println("initialBalance is null")
            return@runBlocking false
        }
        repeat(maxTries) {
            getUserInfoBlocking(
                userInfoPostmsg(
                    encryptomobile,
                    UserPersistentStorage(context).get()?.token.toString()
                ), context
            )
            val currentBalance = UserBalanceStorage(context).get()?.wallet?.balance?.toFloatOrNull()
            Log.v("TapAction", "currentBalance is $currentBalance")
            if (currentBalance != null && currentBalance < initialBalance) {
                return@runBlocking true
            }
            delay(retryInterval.toLong())
        }
        return@runBlocking false
    }

@OptIn(DelicateCoroutinesApi::class)
fun mix(hotTap: TapDeviceWithAction, coldTap: TapDeviceWithAction) {
    GlobalScope.launch(context = Dispatchers.IO) {
        hotTap.onSync()
        if (waitTillTolled(hotTap.context)) {
            coldTap.onSync()
        }
    }
}