package com.dremio.exec.store.jdbc.conf;

import com.dremio.connector.metadata.DatasetHandle;
import com.dremio.connector.metadata.DatasetHandleListing;
import com.dremio.connector.metadata.DatasetMetadata;
import com.dremio.connector.metadata.EntityPath;
import com.dremio.exec.store.jdbc.JdbcSchemaFetcher;
import com.dremio.exec.store.jdbc.JdbcStoragePlugin;
import com.google.common.collect.ImmutableList;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class FirebirdSchemaFetcher extends JdbcSchemaFetcher {

    private final DataSource mDataSource;
    private final String mStoragePluginName;
    private final JdbcStoragePlugin.Config mConfig;
    private final int mTimeout;
    private final String mName;

    public FirebirdSchemaFetcher(String name, DataSource dataSource, int timeout, JdbcStoragePlugin.Config config) {
        super(name, dataSource, timeout, config);
        this.mDataSource = dataSource;
        this.mName=name;
        this.mStoragePluginName = name;
        this.mTimeout = timeout;
        this.mConfig = config;
    }

    @Override
    public DatasetMetadata getTableMetadata(DatasetHandle datasetHandle) {
        DatasetMetadata md= super.getTableMetadata(datasetHandle);
       return md;
    }

    @Override
    protected boolean usePrepareForColumnMetadata() {
        return true;
    }

    @Override
    protected boolean usePrepareForGetTables() {
        return false;
    }

    @Override
    public DatasetHandleListing getTableHandles() {
        ArrayList<String> tableNames=new ArrayList<>();
        try {
            DatabaseMetaData metaData = mDataSource.getConnection().getMetaData();
            String[] types = {"TABLE"};
            //Retrieving the columns in the database
            ResultSet tables = metaData.getTables(null, null, "%", types);
            while (tables.next()) {
                tableNames.add(tables.getString("TABLE_NAME"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new DatasetHandleListing() {
            @Override
            public Iterator<? extends DatasetHandle> iterator() {
                return tableNames.stream().map(tableName -> {
                    final EntityPath entityPath = new EntityPath(ImmutableList.of(mName, tableName));
                    return new JdbcSchemaFetcher.JdbcDatasetHandle(entityPath);//new OmnisciDatasetHandle(entityPath);
                }).iterator();
            }

            @Override
            public void close() {
                try {
                    mDataSource.getConnection().close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
