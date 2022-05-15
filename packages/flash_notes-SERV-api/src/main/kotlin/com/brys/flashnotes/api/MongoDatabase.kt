package com.brys.flashnotes.api


import com.mongodb.*
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import org.litote.kmongo.getCollection

class MongoDatabase(val username: String = "mongoadmin", val password: String = "mongoadmin", val host: String = "192.168.50.111", val port: Int = 27017) {
        private val creds = MongoCredential.createCredential(username, "admin", password.toCharArray())

        private val client = MongoClient(ServerAddress(host, port), creds, MongoClientOptions.builder().build())
        private val database: MongoDatabase = client.getDatabase("FlashNotes")
        private var codecRegistry: CodecRegistry = CodecRegistries.fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()),
        )

        fun getUserCollection(): MongoCollection<User> {
            return database.getCollection<User>().withCodecRegistry(codecRegistry)
        }

        fun getGroupCollection(): MongoCollection<CardGroup> {
            return database.getCollection<CardGroup>().withCodecRegistry(codecRegistry)
        }
}