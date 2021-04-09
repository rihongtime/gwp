package com.kidaristudio.gwp.backend.front.config.r2dbc

import com.kidaristudio.gwp.backend.front.core.r2dbc.ServiceOperator
import com.kidaristudio.gwp.backend.persistence.common.logger.Logger
import kotlinx.coroutines.reactor.mono
import org.springframework.r2dbc.connection.lookup.AbstractRoutingConnectionFactory
import reactor.core.publisher.Mono

class MultiTenantRoutingConnectionFactory : AbstractRoutingConnectionFactory() {

    private val logger by Logger()

    override fun determineCurrentLookupKey(): Mono<Any> = mono {
        val serviceId = ServiceOperator.getDatabaseServiceId()
        logger.debug("current-thread-name : {}, serviceId : {}", Thread.currentThread().name, serviceId)
        serviceId
    }
}