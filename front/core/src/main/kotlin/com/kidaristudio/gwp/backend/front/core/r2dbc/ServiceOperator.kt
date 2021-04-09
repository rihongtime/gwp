package com.kidaristudio.gwp.backend.front.core.r2dbc

import com.kidaristudio.gwp.backend.front.core.model.request.client.ClientDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class ServiceOperator(
    @Qualifier("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
) {

    companion object {
        private val CONTEXT = ThreadLocal<ServiceId>()

         fun getDatabaseServiceId(): ServiceId? = CONTEXT.get()

        private fun set(serviceId: ServiceId) {
            CONTEXT.set(serviceId)
        }

        private fun clear() {
            CONTEXT.remove()
        }
    }

    suspend fun <T> executeIn(
        serviceId: ServiceId,
        dispatcher: CoroutineDispatcher = ioDispatcher,
        block: suspend () -> T?
    ): T? {
        set(serviceId)
        val result = withContext(dispatcher) {
            block()
        }
        clear()
        return result
    }

    suspend fun <T> executeIn(
        clientDetails: ClientDetails,
        dispatcher: CoroutineDispatcher = ioDispatcher,
        block: suspend () -> T?
    ) = executeIn(clientDetails.serviceId, dispatcher, block)

    suspend fun <T> execute(
        serviceId: ServiceId,
        block: suspend () -> T?
    ): T? {
        set(serviceId)
        val result = block()
        clear()
        return result
    }

    suspend fun <T> execute(
        clientDetails: ClientDetails,
        block: suspend () -> T?
    ) = execute(clientDetails.serviceId, block)
}