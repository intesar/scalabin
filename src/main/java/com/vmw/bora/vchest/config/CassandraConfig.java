package com.vmw.bora.vchest.config;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories(basePackages = "com.vmw.bora.vchest.repo.cassandra")
public class CassandraConfig extends AbstractCassandraConfiguration {

    // run the below commends on cql cli.
    // create keyspace vchest with replication = {'class':'SimpleStrategy', 'replication_factor':1} ;
    // use demo ;
    /*
     CREATE COLUMNFAMILY emp ( id varchar PRIMARY KEY, username varchar, joinDate timestamp, storageSize double, content blob);
	 CREATE COLUMNFAMILY users ( id varchar PRIMARY KEY, username varchar, password varchar, enabled boolean, tenantId varchar);
	 CREATE COLUMNFAMILY obj ( id varchar PRIMARY KEY, bucketName varchar, kind  varchar, locationUri varchar, size  varchar, 
	 parent varchar, dateModified varchar, chunkCount varchar, owner varchar, tenant varchar, group varchar, shared varchar);
     CREATE COLUMNFAMILY activity( id varchar PRIMARY KEY, user varchar, date varchar, activity varchar, objId varchar, size varchar);
     CREATE COLUMNFAMILY authority( userName varchar PRIMARY KEY, authority varchar );
     CREATE COLUMNFAMILY stats( id varchar PRIMARY KEY, user varchar, year varchar, month varchar, storage varchar, uploadedBytes varchar,
     downloadedBytes varchar);
     CREATE COLUMNFAMILY blob( id varchar PRIMARY KEY, objId varchar, content blob);
     */	

    @Value("${keyspace}")
    private String keyspace;

    @Override
    public String getKeyspaceName() {
        return this.keyspace;
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
