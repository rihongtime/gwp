package com.kidaristudio.gwp.backend.front.core.model.request.client

import com.kidaristudio.gwp.backend.front.core.r2dbc.ServiceId

data class ClientDetails(
    val serviceId: ServiceId = ServiceId.KOREA,
    val userId: Long? = null
) {
}