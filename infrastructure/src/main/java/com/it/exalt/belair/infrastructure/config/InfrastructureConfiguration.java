package com.it.exalt.belair.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration Spring pour le module infrastructure.
 * Active le scan JPA des entités et des repositories de ce module.
 */
@Configuration
@EntityScan(basePackages = "com.it.exalt.belair.infrastructure")
@EnableJpaRepositories(basePackages = "com.it.exalt.belair.infrastructure")
public class InfrastructureConfiguration {
}
