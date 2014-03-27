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

}
