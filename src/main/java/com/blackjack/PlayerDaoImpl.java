package com.blackjack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Component
public class PlayerDaoImpl implements PlayerDAO {

//    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

//    public DataSource getDataSource() {
//        return dataSource;
//    }
//
//    @Autowired
//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void updateBalance(int playerId){
        jdbcTemplate.update("");
    }

}
