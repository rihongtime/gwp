package com.kidaristudio.gwp.backend.front.config.r2dbc.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "r2dbc.pool")
data class R2dbcPoolProperties(
    val initialSize: Int,
    val maxSize: Int,
    val maxLife: Long,
    val maxCreateConnectionTime: Long,
    val maxIdleTime: Long,
)