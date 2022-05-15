package com.brys.flashnotes.api

import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.ExecutorService

class Cache(private val executor: ExecutorService, val db: MongoDatabase, private val snowflake: Snowflake) {
    val tags = mutableMapOf<String, MutableList<Long>>()
    val users = HashMap<Long, User>()
    val groups = HashMap<Long, CardGroup>()
    private val userColl = db.getUserCollection()
    private val groupColl = db.getGroupCollection()
    private val logger: Logger = LoggerFactory.getLogger("FlashNotes -> CACHE")
    fun saveOrReplaceUser(user: User, saveToDB: Boolean = true) {
        users[user.identifier] = user
        if (saveToDB) {
            executor.submit {
                if (userColl.findOne(User::email eq user.email) == null) {
                    userColl.insertOne(user)
                } else {
                    userColl.findOneAndReplace(User::email eq user.email, user)
                }
            }
        }
    }
    fun saveOrReplaceGroup(group: CardGroup, saveToDB: Boolean) {
        groups[group.identifier] = group
        if (saveToDB) {
            executor.submit {
                if (groupColl.findOne(CardGroup::identifier eq group.identifier) == null) {
                    groupColl.insertOne(group)
                } else {
                    groupColl.findOneAndReplace(CardGroup::identifier eq group.identifier, group)
                }
            }
        }
    }
    init {
        logger.info("Initialized")
        userColl.find().forEach { user ->
            user.cache = this
            println("Found existing user: ${user.name} ${user.identifier}")
            user.jsonId = user.identifier.toString()
            users[user.identifier] = user
        }
        groupColl.find().forEach { group ->
            group.created_by = group.created_from.toString()
            group.jsonId = group.identifier.toString()
            groups[group.identifier] = group
            group.tags.forEach { tag ->
                val existingIds = tags[tag]
                if (existingIds == null) {
                    tags[tag] = mutableListOf(group.identifier)
                } else {
                    existingIds.add(group.identifier)
                    tags[tag] = existingIds
                }
            }
        }
    }
}