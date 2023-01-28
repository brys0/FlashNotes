package com.brys.flashnotes.api

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.bson.codecs.pojo.annotations.BsonIgnore
import org.bson.codecs.pojo.annotations.BsonProperty

data class CardGroup constructor(
    @JsonIgnore @BsonIgnore var cache: Cache? = null,
    var title: String = "Example Group",
    var sentences: MutableMap<String, String> = mutableMapOf(),
    var words: MutableMap<String, String> = mutableMapOf(),
    var tags: MutableList<String> = mutableListOf(),
    @JsonIgnore @BsonProperty("identifier") var identifier: Long = 0,
    @JsonIgnore var created_from: Long = 0L,
) {
    @BsonIgnore @JsonProperty("created_by") var created_by = created_from.toString()
    @BsonIgnore @JsonProperty("id") var jsonId = identifier.toString()

    @BsonIgnore
    fun save(saveToDB: Boolean) {
        this.cache?.saveOrReplaceGroup(this, saveToDB)
    }

    fun addData() {
        created_by = created_from.toString()
        jsonId = identifier.toString()
    }
}


