package az.summer.duoheshui.module

import com.google.gson.annotations.SerializedName

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
    val balance: String
)

data class Order(
    @SerializedName("order_sn")
    val order_sn: String
)