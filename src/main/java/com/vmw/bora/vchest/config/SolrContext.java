package com.vmw.bora.vchest.config;

import java.net.MalformedURLException;
import java.util.Date;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.beans.Field;
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
    
    
    <field name="users_username" type="text_general" indexed="true" stored="true"/>
    <field name="users_password" type="text_general" indexed="true" stored="true"/>
    <field name="users_enabled" type="boolean" indexed="true" stored="true"/>
    <field name="users_tenantId" type="text_general" indexed="true" stored="true"/>
    <field name="users_groupId" type="text_general" indexed="true" stored="true"/>


    <field name="username" type="text_general" indexed="true" stored="true"/>
    <field name="password" type="text_general" indexed="true" stored="true"/>
    <field name="enabled" type="boolean" indexed="true" stored="true"/>
    
    <field name="tenantId" type="text_general" indexed="true" stored="true"/>
    <field name="groupId" type="text_general" indexed="true" stored="true"/>

    <field name="userId" type="text_general" indexed="true" stored="true"/>
    <field name="authority" type="text_general" indexed="true" stored="true"/>
	<!-- you might see this already defined.
	<field name="name" type="text_general" indexed="true" stored="true"/>
	-->
	<field name="location" type="text_general" indexed="true" stored="true"/>
	<field name="size" type="long" indexed="true" stored="true"/>
	<field name="modified" type="date" indexed="true" stored="true"/>
	<field name="chunkCount" type="long" indexed="true" stored="true"/>
	<field name="kind" type="text_general" indexed="true" stored="true"/>
	<field name="shared" type="text_general" indexed="true" stored="true"/>
	
	<field name="actionDate" type="date" indexed="true" stored="true"/>
	<field name="actionType" type="text_general" indexed="true" stored="true"/>
	
	<field name="activity_username" type="text_general" indexed="true" stored="true"/>
	<field name="activity_actionDate" type="date" indexed="true" stored="true"/>
	<field name="activity_actionType" type="text_general" indexed="true" stored="true"/>
	<field name="activity_objId" type="text_general" indexed="true" stored="true"/>
	<field name="activity_objName" type="text_general" indexed="true" stored="true"/>
	<field name="activity_size" type="long" indexed="true" stored="true"/>
	<field name="activity_groupId" type="text_general" indexed="true" stored="true"/>
	<field name="activity_tenantId" type="text_general" indexed="true" stored="true"/>
	
	<field name="stats_username" type="text_general" indexed="true" stored="true"/>
	<field name="stats_groupId" type="text_general" indexed="true" stored="true"/>
	<field name="stats_tenantId" type="text_general" indexed="true" stored="true"/>
	
	<field name="objId" type="text_general" indexed="true" stored="true"/>
	<field name="objName" type="text_general" indexed="true" stored="true"/>
	
    <field name="year" type="long" indexed="true" stored="true"/>
    <field name="month" type="long" indexed="true" stored="true"/>
    <field name="storage" type="long" indexed="true" stored="true"/>
    <field name="uploadedBytes" type="long" indexed="true" stored="true"/>
    <field name="downloadedBytes" type="long" indexed="true" stored="true"/>
    
    <field name="parent" type="text_general" indexed="true" stored="true"/>
    <field name="owner" type="text_general" indexed="true" stored="true"/>
    
    */
}
