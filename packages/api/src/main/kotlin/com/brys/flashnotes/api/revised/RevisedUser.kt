package com.brys.flashnotes.api.revised

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.brys.flashnotes.api.Utils
import com.fasterxml.jackson.annotation.JsonIgnore
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.conscrypt.OpenSSLMac.HmacSHA256
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.SecureRandom
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.*

interface RevisedUser: GenericAny {
    var name: String
    var lessons: MutableMap<String, Any>
}

class User(override var id: String = "unknown", override var name: String = "not set", override var lessons: MutableMap<String, Any> = mutableMapOf()) : RevisedUser {

}

class PrivateUser(override var id: String, override var name: String, override var lessons: MutableMap<String, Any>, var email: String): RevisedUser {

}

class Authentication(@JsonIgnore var password: String = "not-set", var salt: String = Utils.getNextSalt()) {
    private val algorithm = Algorithm.HMAC256(password)
    val token = 
    @BsonIgnore
    fun createToken(expirationTime: Long): String? {
        val now = Date()
        val expiry = Date(now.time + expirationTime)
        return JWT.create()
            .withSubject("token")
            .withIssuedAt(now)
            .withExpiresAt(expiry)
            .sign(algorithm)
    }

    @BsonIgnore
    fun validateToken(token: String): Boolean {
        return try {
            JWT.require(algorithm)
                .build()
                .verify(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    var thirdParties: Map<String, ThirdPartyService> = mutableMapOf(

    )
}

interface ThirdPartyService {
    var token: String
    var expiry: Long
    var email: String
}
