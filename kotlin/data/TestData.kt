package ir.part.app.signal.data

import ir.part.app.signal.features.cryptoCurrency.domain.CryptoCurrency
import ir.part.app.signal.features.user.domain.models.response.User

object TestData {

    fun getMobile_fromRegisteredUser(): String {
        return "09155574578"
    }

    fun getPassword_fromRegisteredUser(): String {
        return "1111111"
    }

    fun getMobile_fromNewUser(): String {
        return "09351194285"
    }

    fun getPassword_fromNewUser(): String {
        return "X5j13\$#eCM1cG@Kdc%j8kr^Zfpr!Kf#ZjnGb"
    }

    val user = User(
        userName = "09155574578",
        firstName = "ساناز",
        lastName = "عباس زاده", token = "",
        roles = listOf("", ""), userId = "",
        uniqueKey = "", type = "real", idNumber = ""
    )

    val cryptoCurrency = CryptoCurrency(
        "300000",
        "Bitcoin",
        "بیت کوین",
        "BTC",
        "14020105",
        "10:53:00",
        "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png",
        27565.0,
        -2.29430308,
        532765849785.7,
        26061949711.9,
        null,
        null,
        1309338052.977915,
        null
    )
}