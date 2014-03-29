package com.vmw.bora.vchest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.vmw.bora.vchest.repo.cassandra")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${cas.keyspace}")
    private String keyspace;
    
    @Value("${cas.hosts}")
    private String hosts;
    
    @Value("${cas.port}")
    private int port;

    @Override
    public String getKeyspaceName() {
        return this.keyspace;
    }
    
    @Override
    public String getContactPoints() {
    	return this.hosts;
    }
    
    @Override
    public int getPort() {
    	return this.port;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.NONE;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.vmw.bora.vchest.domain"};
    }

}
