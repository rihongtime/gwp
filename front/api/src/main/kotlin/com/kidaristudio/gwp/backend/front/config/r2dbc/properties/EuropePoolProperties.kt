package com.kidaristudio.gwp.backend.front.config.r2dbc.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "r2dbc.europe")
data class EuropePoolProperties(
    val host: String,
    val port: Int,
    val db: String,
    val username: String,
    val password: String,
)