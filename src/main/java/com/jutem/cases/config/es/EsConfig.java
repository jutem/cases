package com.jutem.cases.config.es;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class EsConfig {
    @Bean
    public TransportClient esClient(@Value("${es.cluster.name}") String clusterName,
                                    @Value("${es.transport.port}") int port,
                                    @Value("${es.transport.host}") String host) throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
                .build();
        return new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
    }
}
