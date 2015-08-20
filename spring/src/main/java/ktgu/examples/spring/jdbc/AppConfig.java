package ktgu.examples.spring.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by ktgu on 15/8/20.
 */
@Configuration
public class AppConfig {

    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/coconut-web");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        return dataSource;
    }
}
