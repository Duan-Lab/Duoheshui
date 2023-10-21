package az.summer.duoheshui.module

import android.content.Context
import az.summer.duoheshui.ui.theme.screen.encryptomobile
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.json.JSONObject

data class User(
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("wallet")
    val wallet: Wallet,
)

data class Wallet(
    @SerializedName("balance")
    val balance: String,
)

data class Order(
    @SerializedName("order_sn")
    val order_sn: String,
)

fun getUserInfo(data: String, context: Context, onSuccessCallback: (() -> Unit)? = null) {
    val url = "http://newxiaotian.tianji-inc.com/api/v1/UserInfoApi/getUserInfo"
    //添加post请求参数
    val requestBody = FormBody.Builder()
        .add("gptechMsg", data)
        .build()

    //创建request请求对象
    val request = Request.Builder()
        .url(url)
        .post(requestBody)
        .build()


    //创建call并调用enqueue()方法实现网络请求
    OkHttpClient().newCall(request)
        .enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    response.body?.let {
                        val json = JSONObject(it.string())
                        var value = json.getString("data")
                        value = value.replace("\\", "").replace("\"", "")
                        val updateUserInfo = CC().decrypt(value)
                        ShareUtil.putString("balance", updateUserInfo, context)
                        onSuccessCallback?.invoke()
                    }
                }
            }
        })
}

fun getUserInfoBlocking(data: String, context: Context) {
    val url = "http://newxiaotian.tianji-inc.com/api/v1/UserInfoApi/getUserInfo"
    //添加post请求参数
    val requestBody = FormBody.Builder()
        .add("gptechMsg", data)
        .build()

    //创建request请求对象
    val request = Request.Builder()
        .url(url)
        .post(requestBody)
        .build()

    //创建call并调用enqueue()方法实现网络请求
    val response = OkHttpClient().newCall(request).execute()
    if (response.isSuccessful) {
        response.body?.let {
            val json = JSONObject(it.string())
            var value = json.getString("data")
            value = value.replace("\\", "").replace("\"", "")
            val updateUserInfo = CC().decrypt(value)
            ShareUtil.putString("balance", updateUserInfo, context)
        }
    }
}

fun userInfoPostmsg(encryptodata: String, token: String): String {
    var gpteach = JSONObject()

    var header = JSONObject()
    header.put("act", "getUserInfo")
    header.put("device_type", "android")
    header.put("msg_id", currentTime)
    header.put("source_model", "lg_LM-G820")
    header.put("source_sys_version", "rkq1.210420.001")
    header.put("source_version", "1.4.1")
    header.put("token", token)
    header.put("uuid", "")

    gpteach.put("data", encryptodata)
    gpteach.put("header", header)
    return gpteach.toString()
}

class UserBalanceStorage(private val context: Context) {
    fun get(): User? {
        if (encryptomobile.isEmpty() || UserPersistentStorage(context).get()?.token.isNullOrEmpty()) {
            return null
        }
        getUserInfo(
            userInfoPostmsg(
                encryptomobile,
                UserPersistentStorage(context).get()?.token.toString()
            ),
            context
        )
        val preferenceString = ShareUtil.getString("balance", context)
        return try {
            Gson().fromJson(preferenceString, User::class.java)
        } catch (error: Error) {
            println(error.localizedMessage)
            null
        }
    }

    fun clear() {
        ShareUtil.putString("balance", null, context)
    }
}