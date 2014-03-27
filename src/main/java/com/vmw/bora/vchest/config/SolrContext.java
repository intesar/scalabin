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
    <field name="username" type="text_general" indexed="true" stored="true"/>
    <field name="joinDate" type="date" indexed="true" stored="true"/>
    <field name="storageSize" type="float" indexed="true" stored="true"/>

    <field name="enabled" type="boolean" indexed="true" stored="true"/>
    <field name="tenantId" type="text_general" indexed="true" stored="true"/>

    <field name="bucketName" type="text_general" indexed="true" stored="true"/>
    <field name="kind" type="text_general" indexed="true" stored="true"/>
    <field name="locationUri" type="text_general" indexed="true" stored="true"/>
    <field name="size" type="text_general" indexed="true" stored="true"/>
    <field name="parent" type="text_general" indexed="true" stored="true"/>
    <field name="dateModified" type="text_general" indexed="true" stored="true"/>
    <field name="chunkCount" type="text_general" indexed="true" stored="true"/>
    <field name="owner" type="text_general" indexed="true" stored="true"/>
    <field name="tenant" type="text_general" indexed="true" stored="true"/>
    <field name="group" type="text_general" indexed="true" stored="true"/>
    <field name="shared" type="text_general" indexed="true" stored="true"/>
    <field name="authority" type="text_general" indexed="true" stored="true"/>
    <field name="user" type="text_general" indexed="true" stored="true"/>
    <field name="year" type="text_general" indexed="true" stored="true"/>
    <field name="month" type="text_general" indexed="true" stored="true"/>
    <field name="storage" type="text_general" indexed="true" stored="true"/>
    <field name="uploadedBytes" type="text_general" indexed="true" stored="true"/>
    <field name="downloadedBytes" type="text_general" indexed="true" stored="true"/>
    <field name="date" type="text_general" indexed="true" stored="true"/>
    <field name="activity" type="text_general" indexed="true" stored="true"/>
	<field name="objId" type="text_general" indexed="true" stored="true"/>
    */
}
