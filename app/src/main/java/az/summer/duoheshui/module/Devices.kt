package az.summer.duoheshui.module

import android.content.Context
import az.summer.duoheshui.ui.theme.screen.encryptomobile
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONObject

fun enSetDrinkDevice(deviceUrl: String): String {
    val jsonObject = JSONObject()
    jsonObject.put("device_key", deviceUrl)
    return jsonObject.toString()
}

fun enStartDrink(deviceUrl: String, orderSn: String): String {
    val jsonObject = JSONObject()
    jsonObject.put("device_key", deviceUrl)
    jsonObject.put("order_sn", orderSn)
    return jsonObject.toString()
}


fun drinkingPost(type: String, data: String, context: Context) {
    val url = "http://iot.tianji-inc.com/index.php/drinking/$type"
    //添加post请求参数
    val json = data.toRequestBody()
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
                        val orderSn = CC().decrypt(value)
                        OrderSnStorage(context).set(orderSn)
                        println(OrderSnStorage(context).get()?.order_sn)
                    }
                    getUserInfo(
                        userInfoPostmsg(
                            encryptomobile,
                            UserPersistentStorage(context).get()?.token.toString()
                        ),
                        context
                    )
                }
            }

        })
}

fun drinkpostmsg(type: String, encryptodata: String, token: String): String {
    var gpteach = JSONObject()

    var header = JSONObject()
    header.put("act", type)
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

class OrderSnStorage(private val context: Context) {
    fun set(res: String) {
        ShareUtil.putString("order", res, context)
    }

    fun get(): Order? {
        val preferenceString = ShareUtil.getString("order", context)
        return try {
            Gson().fromJson(preferenceString, Order::class.java)
        } catch (error: Error) {
            println(error.localizedMessage)
            null
        }
    }
}
