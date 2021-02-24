package com.dremio.exec.store.jdbc.conf;

import com.dremio.exec.store.jdbc.JdbcSchemaFetcher;
import com.dremio.exec.store.jdbc.JdbcStoragePlugin;
import com.dremio.exec.store.jdbc.dialect.arp.ArpDialect;
import com.dremio.exec.store.jdbc.dialect.arp.ArpYaml;

import javax.sql.DataSource;

public class FirebirdDialect  extends ArpDialect {
    public FirebirdDialect(ArpYaml yaml) {
        super(yaml);
    }

    @Override
    public ContainerSupport supportsCatalogs() {
        return ContainerSupport.AUTO_DETECT;
    }

    @Override
    public ContainerSupport supportsSchemas() {
        return ContainerSupport.AUTO_DETECT;
    }

    @Override
    public JdbcSchemaFetcher getSchemaFetcher(String name, DataSource dataSource, int timeout, JdbcStoragePlugin.Config config) {
        return new FirebirdSchemaFetcher(name, dataSource, timeout, config);
    }

    @Override
    public boolean supportsNestedAggregations() {
        return false;
    }

}
