package com.gubarev.movieland.dao

import net.ttddyy.dsproxy.listener.ChainListener
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder
import org.flywaydb.core.Flyway
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.PropertySource
import org.testcontainers.containers.PostgreSQLContainer

import javax.sql.DataSource

@PropertySource("classpath:test-application.properties")
@Configuration
class TestConfiguration {

    @Value('${database.docker.image.name:postgres:12}')
    private String dockerImageName

    @Value('${db.migration.location:classpath:db.migration}')
    private String dbMigrationLocation

    @Bean
    @Primary
    DataSource createDataSource() {
        PostgreSQLContainer POSTGRESQL_CONTAINER =
                new PostgreSQLContainer(dockerImageName)
        POSTGRESQL_CONTAINER.start()
        DataSource dataSource = new PGSimpleDataSource()
        dataSource.setUrl(POSTGRESQL_CONTAINER.getJdbcUrl())
        dataSource.setUser(POSTGRESQL_CONTAINER.getUsername())
        dataSource.setPassword(POSTGRESQL_CONTAINER.getPassword())

        Flyway flyway = Flyway.configure().dataSource(dataSource).locations(dbMigrationLocation).load()
        flyway.migrate()

        ChainListener listener = new ChainListener()
        listener.addListener(new DataSourceQueryCountListener())
        return ProxyDataSourceBuilder
                .create(dataSource)
                .name("DS-Proxy")
                .listener(listener)
                .build()
    }
}
