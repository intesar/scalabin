package com.vmw.bora.vchest.config;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackages = {"com.vmw.bora.vchest.repo.solr"}, multicoreSupport = true)
public class SolrContext {

    @Value("${solr.host}")
    private String solrHost;

    @Bean
    public SolrServer solrServer() throws MalformedURLException,
            IllegalStateException {
        return new HttpSolrServer(solrHost);
    }

    /*
    Schema.xml content.
    [3/27/14 10:24:53 AM] Intesar Mohammed: <field name="username" type="text_general" indexed="true" stored="true"/>
   <field name="joinDate" type="date" indexed="true" stored="true"/>
   <field name="storageSize" type="float" indexed="true" stored="true"/>
[3/27/14 12:32:06 PM] sachin modak:     <field name="userName" type="text_general" indexed="true" stored="true"/>
    <field name="enabled" type="boolean" indexed="true" stored="true"/>
    <field name="tenantId" type="text_general" indexed="true" stored="true"/>
[3/27/14 12:32:40 PM] Omkar Raut: <!-- authority -->
   <field name="authority" type="text_general" indexed="true" stored="true"/>
   
   <!-- stats -->
   <field name="user" type="text_general" indexed="true" stored="true"/>
   <field name="year" type="int" indexed="true" stored="true"/>
   <field name="month" type="int" indexed="true" stored="true"/>
   <field name="storage" type="long" indexed="true" stored="true"/>
   <field name="uploadedBytes" type="long" indexed="true" stored="true"/>
   <field name="downloadedBytes" type="long" indexed="true" stored="true"/>
[3/27/14 1:47:55 PM] Omkar Raut: <!-- activity -->
    <field name="date" type="date" indexed="true" stored="true"/>
    <field name="activity" type="text_general" indexed="true" stored="true"/>
	<field name="objId" type="int" indexed="true" stored="true"/>
	<field name="size" type="long" indexed="true" stored="true"/>
    */
}
