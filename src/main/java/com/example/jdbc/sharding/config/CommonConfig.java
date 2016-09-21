package com.example.jdbc.sharding.config;

import com.example.jdbc.sharding.event.OrderCreatedEvent;
import org.nustaq.serialization.FSTConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Created by fangtao on 16/9/11.
 */
@Configuration
public class CommonConfig {
    @Bean
    public FSTConfiguration fstConfiguration() {
        FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
        conf.registerClass(OrderCreatedEvent.class);
        return conf;
    }
}
