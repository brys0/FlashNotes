package com.brys.flashnotes.api

import java.math.BigInteger
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*

object Utils {
    val RANDOM = SecureRandom()
    fun getSHA512(input: String, salt: String):String{
        val md: MessageDigest = MessageDigest.getInstance("SHA-512")
        val messageDigest = md.digest("${salt}${input}".toByteArray())
        // Convert byte array into signum representation
        val no = BigInteger(1, messageDigest)
        // Convert message digest into hex value
        var hashtext: String = no.toString(16)
        // Add preceding 0s to make it 128 chars long
        while (hashtext.length < 128) {
            hashtext = "0$hashtext"
        }
        // return the HashText
        return hashtext
    }
    fun getNextSalt(): String {
        val salt = ByteArray(16)
        RANDOM.nextBytes(salt)
        return Base64.getEncoder().encodeToString(salt);
    }
}