package com.miquido.revoltsdk.internal

import com.miquido.revoltsdk.RevoltEvent
import com.miquido.revoltsdk.internal.database.EventEntity
import com.miquido.revoltsdk.internal.model.EventModel
import com.miquido.revoltsdk.internal.model.MetaDataModel

/** Created by MiQUiDO on 18.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal fun EventModel.toEntity(): EventEntity {
    return EventEntity(this.createJson().toString(), this.getTimestamp(), this.getType())
}

internal fun EventEntity.toModel(): EventModel {
    return EventModel(RevoltEvent(this.type, this.eventData.asJsonObject()), MetaDataModel(this.type, this.timestamp))
}
