package az.summer.duoheshui.module

import android.content.Context
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

var currentTime: Long = System.currentTimeMillis()
var userInfo: String = ""

@OptIn(DelicateCoroutinesApi::class)
fun posttty(postType: String, postData: String, context: Context) {
    val url = URL("http://newxiaotian.tianji-inc.com/api/v1/UserApi/$postType")
    val conn = url.openConnection() as HttpURLConnection
    conn.requestMethod = "POST"
    conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8")
    conn.setRequestProperty(
        "User-Agent",
        "Mozilla/5.0 (Linux; U; Android 11; zh-cn; LM-G820 Build/RKQ1.210420.001) AppleWebKit/533.1 (KHTML, like Gecko) Version/5.0 Mobile Safari/533.1"
    )
    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
    conn.setRequestProperty("Host", "newxiaotian.tianji-inc.com")
    conn.setRequestProperty("Connection", "Keep-Alive")
    conn.setRequestProperty("Accept-Encoding", "gzip")
    conn.setRequestProperty("Cache-Control", "no-cache")

    val postData = "gptechMsg=$postData"
    val postDataBytes = postData.toByteArray(StandardCharsets.UTF_8)

    GlobalScope.launch(Dispatchers.IO) {
        conn.doOutput = true
        val wr = DataOutputStream(conn.outputStream)
        wr.write(postDataBytes)
        wr.flush()
        wr.close()

        val responseCode = conn.responseCode
        val inputStream = conn.inputStream
        val response = inputStream.bufferedReader().use { it.readText() }

        UserPersistentStorage(context).set(response)
        println("\nSending 'POST' request to URL : $url")
        println("Post parameters : $postData")
        println("Response Code : $responseCode")

    }

}

fun enMobile(mobileNum: String): String {
    var jsonObject = JSONObject()
    jsonObject.put("mobile", mobileNum)
    jsonObject.put("type", "login")
    return jsonObject.toString()
}

fun postmsg(type: String, encryptodata: String): String {
    var gpteach = JSONObject()

    var header = JSONObject()
    header.put("act", type)
    header.put("device_type", "android")
    header.put("msg_id", currentTime)
    header.put("source_model", "lg_LM-G820")
    header.put("source_sys_version", "rkq1.210420.001")
    header.put("source_version", "1.4.1")
    header.put("token", "")
    header.put("uuid", "")

    gpteach.put("data", encryptodata)
    gpteach.put("header", header)

    return URLEncoder.encode(gpteach.toString(), "utf-8")
}

fun enVcode(code: String, mobileNum: String): String {
    var getCode = JSONObject()
    getCode.put("code", code)
    getCode.put("mobile", mobileNum)
    return getCode.toString()
}
