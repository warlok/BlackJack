package com.blackjack;

import com.blackjack.objects.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;

@Component
public class PlayerDaoImpl implements PlayerDAO {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Player getUserByUserName(String userName) {
        return null;
    }

}
