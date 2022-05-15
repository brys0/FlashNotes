package com.brys.flashnotes.api

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bson.codecs.pojo.annotations.BsonProperty

data class User constructor(
    @JsonIgnore @BsonIgnore var cache: Cache? = null,
    @JsonIgnore @BsonProperty("identifier") var identifier: Long = 0,
    @BsonProperty("email") var email: String = "TestAccount@example.com",
    @BsonProperty("name") var name: String = "Test Account",
    @BsonProperty("salt") @JsonIgnore var salt: String = Utils.getNextSalt(),
    @BsonProperty("auth") var auth: String = Utils.getSHA512(email, salt)
) {
    @BsonIgnore @JsonProperty("id") var jsonId: String = identifier.toString()
    @BsonIgnore
    fun save(saveToDatabase: Boolean) {
        cache?.saveOrReplaceUser(this, saveToDatabase)
    }
}