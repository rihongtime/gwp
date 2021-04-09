package com.kidaristudio.gwp.backend.front.config

import com.kidaristudio.gwp.backend.front.config.resolver.ClientDetailsResolver
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer

@Configuration
@ComponentScan(basePackages = ["com.kidaristudio.gwp.backend.*"])
@ConfigurationPropertiesScan(basePackages = ["com.kidaristudio.gwp.backend.front.config.*"])
class WebFluxConfig(
    val clientDetailsResolver: ClientDetailsResolver,
) : WebFluxConfigurer {


    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        super.configureArgumentResolvers(configurer)
        configurer.addCustomResolver(clientDetailsResolver)
    }
}