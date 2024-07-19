package de.taimos.dvalin.jpa.config;

import de.taimos.daemon.spring.conditional.OnSystemProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

/**
 * Copyright 2024 Cinovo AG<br>
 * <br>
 *
 * @author mweise
 */
@Configuration
@OnSystemProperty(propertyName = "ds.demodata", propertyValue = "true")
public class DemoDataConfig {
    @Value("${ds.demodata.encoding:UTF-8}")
    private String encoding;
    @Value("${ds.demodata.location:classpath*:sql/demodata_*${ds.type}.sql}")
    private Resource[] scripts;

    /**
     * @param dataSource the datasource
     * @return DataSourceInitializer for demo data
     */
    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(this.scripts);
        populator.setSqlScriptEncoding(this.encoding);
        initializer.setDatabasePopulator(populator);
        return initializer;
    }
}
