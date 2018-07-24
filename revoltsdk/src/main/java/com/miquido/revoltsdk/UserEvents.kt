@file:JvmName("UserEvents")
package com.miquido.revoltsdk
/** Created by MiQUiDO on 19.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */

fun buildUserProfileEvent(appUserId: String) : UserProfileEventBuilder =
        UserProfileEventBuilder(appUserId)

class UserProfileEventBuilder(appUserId: String) {
    private val properties: MutableMap<String, Any> = HashMap()

    init {
        properties["appUserId"] = appUserId
    }

    fun build(): Event {
        return RevoltEvent("user.profile", properties)
    }

    fun birthYear(year: Int): UserProfileEventBuilder {
        properties["birthYear"] = year
        return this
    }

    fun gender(gender: Gender): UserProfileEventBuilder {
        properties["gender"] = gender.name
        return this
    }

    fun country(country: String): UserProfileEventBuilder {
        properties["gender"] = country
        return this
    }

    fun city(city: String): UserProfileEventBuilder {
        properties["city"] = city
        return this
    }

    fun customProperties(vararg customProperties: Pair<String, Any>): UserProfileEventBuilder {
        properties.putAll(customProperties)
        return this
    }
}

enum class Gender {
    F, M
}

fun buildUserSignInEvent(appUserId: String) : Event =
        RevoltEvent("user.signedIn", "appUserId" to appUserId)

fun buildUserSignOutEvent(appUserId: String) : Event =
        RevoltEvent("user.signedOut", "appUserId" to appUserId)

