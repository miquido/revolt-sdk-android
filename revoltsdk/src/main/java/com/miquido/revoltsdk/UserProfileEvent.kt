package com.miquido.revoltsdk

import com.google.gson.JsonObject

/** Created by MiQUiDO on 19.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class UserProfileEvent(private val appUserId: String,
                       private var birthYear: Int? = null,
                       private var gender: Gender? = null,
                       private var country: String? = null,
                       private var city: String? = null,
                       private var pairs: List<Pair<String, String>>? = null) : Event {

    override fun getJson(): JsonObject {
        val jsonObject = JsonObject().apply {
            addProperty("appUserId", appUserId)
            addProperty("birthYear", birthYear)
            addProperty("gender", gender?.name)
            addProperty("country", country)
            addProperty("city", city)

        }
        pairs?.forEach {
            jsonObject.addProperty(it.first, it.second)
        }
        return jsonObject
    }

    override fun getType(): String {
        return "user.profile"
    }

    companion object {
        fun builder(): AppUserIdBuilder {
            return AppUserIdBuilder()
        }
    }

    class AppUserIdBuilder {
        fun appUserId(appUserId: String): Builder {
            return Builder(appUserId)
        }
    }

    class Builder(private val appUserId: String) {
        private var birthYear: Int? = null
        private var gender: Gender? = null
        private var country: String? = null
        private var city: String? = null
        private var pairs: ArrayList<Pair<String, String>>? = ArrayList()

        fun build(): UserProfileEvent {
            return UserProfileEvent(appUserId, birthYear, gender, country, city, pairs)
        }

        fun birthYear(year: Int): Builder {
            this.birthYear = year
            return this
        }

        fun gender(gender: Gender): Builder {
            this.gender = gender
            return this
        }

        fun country(country: String): Builder {
            this.country = country
            return this
        }

        fun city(city: String): Builder {
            this.city = city
            return this
        }

        fun pairs(vararg pairs: Pair<String, String>): Builder {
            this.pairs?.addAll(pairs.toList())
            return this
        }
    }

    enum class Gender {
        F, M
    }
}
