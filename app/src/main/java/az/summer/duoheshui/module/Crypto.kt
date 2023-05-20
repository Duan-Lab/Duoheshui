package az.summer.duoheshui.module

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class CC {
    private val key = "5yoOxt9w"
    private val iv: String = "20190829"

    @Throws(Exception::class)
    fun encrypt(content: String, slatKey: String = key, vectorKey: String = iv): String {
        val cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")
        val secretKey: SecretKey = SecretKeySpec(slatKey.toByteArray(), "DES")
        val iv = IvParameterSpec(vectorKey.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv)
        val encrypted = cipher.doFinal(content.toByteArray())
        return Base64.getEncoder().encodeToString(encrypted)
    }

    @Throws(Exception::class)
    fun decrypt(base64Content: String, slatKey: String = key, vectorKey: String = iv): String {
        val cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")
        val secretKey: SecretKey = SecretKeySpec(slatKey.toByteArray(), "DES")
        val iv = IvParameterSpec(vectorKey.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv)
        val content = Base64.getDecoder().decode(base64Content)
        val encrypted = cipher.doFinal(content)
        return String(encrypted)
    }
}