package com.kidaristudio.gwp.backend.front.config.resolver

import com.kidaristudio.gwp.backend.front.core.model.request.client.ClientDetails
import com.kidaristudio.gwp.backend.front.core.r2dbc.ServiceId
import com.kidaristudio.gwp.backend.front.service.common.ClientService
import kotlinx.coroutines.reactor.mono
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class ClientDetailsResolver(
    val clientService: ClientService
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        ClientDetails::class.java.isAssignableFrom(parameter.parameterType)

    override fun resolveArgument(parameter: MethodParameter, bindingContext: BindingContext, exchange: ServerWebExchange): Mono<Any> {
        return mono {
            val request = exchange.request
            // TODO JWT payload 영역에 serviceId, userId가 있으면 될 것으로 보임
            val serviceId = ServiceId.of(request.headers["X-LZ-Service-Id"]?.first()?.toString())
            val userId = request.headers["X-LZ-User-Id"]?.first()?.toLong()
            ClientDetails(serviceId, userId)
        }
    }
}
