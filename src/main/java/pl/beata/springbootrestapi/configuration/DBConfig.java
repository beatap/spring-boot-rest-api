package pl.beata.springbootrestapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Configuration
public class DBConfig {

    private DataSource dataSource;

    @Autowired
    public DBConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

//    @Bean
//    public DataSource getDataSource() {
//        return DataSourceBuilder.create()
//                .url("jdbc:mysql://remotemysql.com:3306/pIrTe4gZgw")
//                .username("pIrTe4gZgw")
//                .password("M7AbHuGeJq")
//                .driverClassName("com.mysql.jdbc.Driver")
//                .build();
//
//    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void init() {
//        String sql = "CREATE TABLE cars(car_id int, mark varchar(100), model varchar(100), " +
//                "color varchar(100), year timestamp, PRIMARY KEY (car_id))";
//
//        getJdbcTemplate().update(sql);
//    }
}
