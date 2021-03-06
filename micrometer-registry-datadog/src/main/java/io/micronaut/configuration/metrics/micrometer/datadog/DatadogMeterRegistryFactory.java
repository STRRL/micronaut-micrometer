/*
 * Copyright 2017-2019 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.micronaut.configuration.metrics.micrometer.datadog;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.datadog.DatadogConfig;
import io.micrometer.datadog.DatadogMeterRegistry;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;

import javax.inject.Singleton;

import static io.micronaut.configuration.metrics.micrometer.MeterRegistryFactory.MICRONAUT_METRICS_ENABLED;
import static io.micronaut.configuration.metrics.micrometer.MeterRegistryFactory.MICRONAUT_METRICS_EXPORT;

/**
 * The DatadogMeterRegistryFactory that will configure and create a datadog meter registry.
 *
 * @author thiagolocatelli
 * @since 1.2.0
 */
@Factory
public class DatadogMeterRegistryFactory {

    public static final String DATADOG_CONFIG = MICRONAUT_METRICS_EXPORT + ".datadog";
    public static final String DATADOG_ENABLED = DATADOG_CONFIG + ".enabled";

    private final DatadogConfig datadogConfig;

    /**
     * Sets the underlying datadog meter registry properties.
     *
     * @param influxDbConfigurationProperties prometheus properties
     */
    public DatadogMeterRegistryFactory(DatadogConfigurationProperties influxDbConfigurationProperties) {
        this.datadogConfig = influxDbConfigurationProperties;
    }

    /**
     * Create a DatadogMeterRegistry bean if global metrics are enables
     * and the datadog is enabled.  Will be true by default when this
     * configuration is included in project.
     *
     * @return A DatadogMeterRegistry
     */
    @Bean
    @Primary
    @Singleton
    @Requires(property = MICRONAUT_METRICS_ENABLED, value = StringUtils.TRUE, defaultValue = StringUtils.TRUE)
    @Requires(property = DATADOG_ENABLED, value = StringUtils.TRUE, defaultValue = StringUtils.TRUE)
    @Requires(beans = CompositeMeterRegistry.class)
    DatadogMeterRegistry datadogConfig() {
        return new DatadogMeterRegistry(datadogConfig, Clock.SYSTEM);
    }


}
