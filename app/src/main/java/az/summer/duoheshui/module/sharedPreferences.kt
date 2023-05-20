package az.summer.duoheshui.module

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import org.json.JSONObject

object ShareUtil {

    private var sps: SharedPreferences? = null

    private fun getSps(context: Context): SharedPreferences {
        if (sps == null) {
            sps = context.getSharedPreferences("default", Context.MODE_PRIVATE)
        }
        return sps!!
    }

    fun putString(key: String, value: String?, context: Context) {
        if (!value.isNullOrBlank()) {
            val editor: SharedPreferences.Editor = getSps(context).edit()
            editor.putString(key, value)
            editor.apply()
        }
    }

    fun getString(key: String, context: Context): String? {
        if (key.isNotBlank()) {
            val sps: SharedPreferences = getSps(context)
            return sps.getString(key, null)
        }
        return null
    }
}


class UserPersistentStorage(private val context: Context) {
    fun set(response: String) {
        val json = JSONObject(response)
        var value = json.getString("data")
        value = value.replace("\\", "").replace("\"", "")
        userInfo = CC().decrypt(value)
        ShareUtil.putString("user", userInfo, context)
    }

    fun get(): User? {
        val preferenceString = ShareUtil.getString("user", context)
        return try {
            Gson().fromJson(preferenceString, User::class.java)
        } catch (error: Error) {
            println(error.localizedMessage)
            null
        }
    }
}