package com.miquido.revoltsdk


/** Created by MiQUiDO on 19.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */

fun userProfileBuilder(): AppUserIdBuilder {
    return AppUserIdBuilder()
}


class AppUserIdBuilder {
    fun appUserId(appUserId: String): Builder {
        return Builder(appUserId)
    }
}

class Builder(appUserId: String) {
    private val properties: MutableMap<String, Any> = HashMap()

    init {
        properties["appUserId"] = appUserId
    }

    fun build(): RevoltEvent {
        return RevoltEvent("user.profile", properties)
    }

    fun birthYear(year: Int): Builder {
        properties["birthYear"] = year
        return this
    }

    fun gender(gender: Gender): Builder {
        properties["gender"] = gender.name
        return this
    }

    fun country(country: String): Builder {
        properties["country"] = country
        return this
    }

    fun city(city: String): Builder {
        properties["city"] = city
        return this
    }

    fun customProperty(key: String, value: Any): Builder {
        this.properties[key] = value
        return this
    }

    fun customProperties(vararg pairs: Pair<String, String>): Builder {
        pairs.forEach { properties[it.first] = it.second }
        return this
    }
}

enum class Gender {
    F, M
}
