package com.kidaristudio.gwp.backend.front.config.r2dbc

import com.kidaristudio.gwp.backend.front.config.r2dbc.converter.LocalDateTimeReadConverter
import com.kidaristudio.gwp.backend.front.config.r2dbc.converter.LocalDateTimeWriteConverter
import com.kidaristudio.gwp.backend.front.config.r2dbc.converter.ZonedDateTimeReadConverter
import com.kidaristudio.gwp.backend.front.config.r2dbc.converter.ZonedDateTimeWriteConverter
import com.kidaristudio.gwp.backend.front.config.r2dbc.properties.ConnectionProperty
import com.kidaristudio.gwp.backend.front.config.r2dbc.properties.ConnectionProperty.MYSQL_DRIVER
import com.kidaristudio.gwp.backend.front.config.r2dbc.properties.EuropePoolProperties
import com.kidaristudio.gwp.backend.front.config.r2dbc.properties.KoreaPoolProperties
import com.kidaristudio.gwp.backend.front.config.r2dbc.properties.R2dbcPoolProperties
import com.kidaristudio.gwp.backend.front.core.r2dbc.ServiceId
import io.r2dbc.pool.PoolingConnectionFactoryProvider.*
import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.convert.MappingR2dbcConverter
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions
import org.springframework.data.r2dbc.dialect.MySqlDialect
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import java.time.Duration
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.set

@Configuration
@EnableR2dbcRepositories(
    basePackages = ["com.kidaristudio.gwp.backend.persistence.*.repository"]
)
class R2dbcConfig(
    val r2dbcPoolProperties: R2dbcPoolProperties,
    val koreaPoolProperties: KoreaPoolProperties,
    val europePoolProperties: EuropePoolProperties
) : AbstractR2dbcConfiguration() {

    @Bean
    override fun r2dbcCustomConversions(): R2dbcCustomConversions {
        val converters = ArrayList<Any>().apply {
            addAll(MySqlDialect.INSTANCE.converters)
            addAll(R2dbcCustomConversions.STORE_CONVERTERS)
            add(ZonedDateTimeReadConverter())
            add(ZonedDateTimeWriteConverter())
            add(LocalDateTimeReadConverter())
            add(LocalDateTimeWriteConverter())
        }
        return R2dbcCustomConversions(converters)
    }

    @Bean
    override fun r2dbcConverter(
        mappingContext: R2dbcMappingContext,
        r2dbcCustomConversions: R2dbcCustomConversions
    ): MappingR2dbcConverter {
        return super.r2dbcConverter(mappingContext, r2dbcCustomConversions)
    }

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        val factories = HashMap<ServiceId, ConnectionFactory>()
        // TODO 서비스가 추가되면 이곳에 커넥션 팩토리 설정을 추가한다.
        factories[ServiceId.KOREA] = koreaConnectionFactory()
        factories[ServiceId.EUROPE] = europeConnectionFactory()

        val multiTenantRoutingConnectionFactory = MultiTenantRoutingConnectionFactory()
        multiTenantRoutingConnectionFactory.setDefaultTargetConnectionFactory(koreaConnectionFactory())
        multiTenantRoutingConnectionFactory.setTargetConnectionFactories(factories)
        return multiTenantRoutingConnectionFactory
    }

    @Bean
    fun koreaConnectionFactory() =
        makeConnectionFactory(
            hashMapOf(
                ConnectionProperty.HOST to koreaPoolProperties.host,
                ConnectionProperty.USERNAME to koreaPoolProperties.username,
                ConnectionProperty.PASSWORD to koreaPoolProperties.password,
                ConnectionProperty.PORT to koreaPoolProperties.port.toString(),
                ConnectionProperty.DB to koreaPoolProperties.db,
            )
        )

    @Bean
    fun europeConnectionFactory() =
        makeConnectionFactory(
            hashMapOf(
                ConnectionProperty.HOST to europePoolProperties.host,
                ConnectionProperty.USERNAME to europePoolProperties.username,
                ConnectionProperty.PASSWORD to europePoolProperties.password,
                ConnectionProperty.PORT to europePoolProperties.port.toString(),
                ConnectionProperty.DB to europePoolProperties.db,
            )
        )

    fun makeConnectionFactory(connectionProperties: Map<String, String>): ConnectionFactory =
        ConnectionFactories.get(
            builder()
                .option(DRIVER, POOLING_DRIVER)
                .option(PROTOCOL, MYSQL_DRIVER)
                .option(HOST, connectionProperties[ConnectionProperty.HOST]!!)
                .option(USER, connectionProperties[ConnectionProperty.USERNAME]!!)
                .option(PORT, connectionProperties[ConnectionProperty.PORT]!!.toInt())
                .option(PASSWORD, connectionProperties[ConnectionProperty.PASSWORD]!!)
                .option(DATABASE, connectionProperties[ConnectionProperty.DB]!!)
                .option(MAX_SIZE, r2dbcPoolProperties.maxSize)
                .option(INITIAL_SIZE, r2dbcPoolProperties.initialSize)
                .option(MAX_IDLE_TIME, Duration.ofSeconds(r2dbcPoolProperties.maxIdleTime))
                .option(MAX_CREATE_CONNECTION_TIME, Duration.ofSeconds(r2dbcPoolProperties.maxCreateConnectionTime))
                .option(MAX_LIFE_TIME, Duration.ofMinutes(r2dbcPoolProperties.maxLife))
                .build()
        )
}