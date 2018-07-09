package com.miquido.revoltsdk.internal

import com.miquido.revoltsdk.internal.database.DatabaseRepository
import com.miquido.revoltsdk.internal.network.BackendRepository
import com.miquido.revoltsdk.internal.network.RevoltRequestModel

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class RevoltRepository(private val backendRepository: BackendRepository,
                       private val databaseRepository: DatabaseRepository) : EventsRepository {

    override fun addEvent(event: RevoltRequestModel) {
        backendRepository.addEvent(event)
    }
}
