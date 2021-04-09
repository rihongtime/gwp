package com.kidaristudio.gwp.backend.front.core.r2dbc

import com.kidaristudio.gwp.backend.front.core.exception.UnsupportedServiceException

enum class ServiceId {
    KOREA, EUROPE;

    companion object {
        fun of(serviceId: String?) = try {
            if (serviceId.isNullOrBlank()) throw UnsupportedServiceException()
            else valueOf(serviceId.toUpperCase().trim())
        } catch (ignore: Exception) {
            throw UnsupportedServiceException()
        }
    }
}