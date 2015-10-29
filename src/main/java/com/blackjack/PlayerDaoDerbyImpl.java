package com.blackjack;

import com.blackjack.objects.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class PlayerDaoDerbyImpl implements PlayerDao {

//    private DataSource dataSource;
    private NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SELECT_PLAYER = "select * from PLAYERS where ID_PLAYER=:playerId";
    private static final String SELECT_MONEY = "select MONEY from PLAYERS where ID_PLAYER=:playerId";
    private static final String SELECT_PLAYERS = "select * from PLAYERS";
    private static final String INSERT_PLAYER = "insert into PLAYERS values (:playerId, :money)";
    private static final String UPDATE_MONEY = "update PLAYERS set MONEY= :money where ID_PLAYER = :playerId";
    private static final String LOG = "select * from ACTIONS where ID_PLAYER = :playerId";

    private static final class PlayerRowMapper
            implements RowMapper<Player> {
        @Override
        public Player mapRow(ResultSet resultSet, int i) throws SQLException {
            Player player = new Player();
            player.setPlayerId(resultSet.getInt("ID_PLAYER"));
            player.setMoney(resultSet.getDouble("MONEY"));
            return player;
        }
    }

    private static final class PlayersRowMapper
            implements RowMapper<Map<Integer,Player>> {
        @Override
        public Map<Integer,Player> mapRow(ResultSet resultSet, int i) throws SQLException {
            Map<Integer,Player> players = new HashMap<>();
            do {
                Player player = new Player();
                player.setPlayerId(resultSet.getInt("ID_PLAYER"));
                player.setMoney(resultSet.getDouble("MONEY"));
                players.put(player.getPlayerId(),player);
            } while (resultSet.next());
            return players;
        }
    }

//    @Autowired
//    public void setDataSource(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

//    public NamedParameterJdbcTemplate getJdbcTemplate() {
//        return jdbcTemplate;
//    }

    @Autowired
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void updateBalance(Player player) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("playerId",player.getPlayerId());
        paramMap.put("money",player.getMoney());
        jdbcTemplate.update(UPDATE_MONEY,paramMap);
    }

    @Override
    public double getBalance(Player player) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("playerId",player.getPlayerId());
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(SELECT_MONEY, paramMap);
        rowSet.next();
        return rowSet.getDouble("MONEY");
    }

    @Override
    public Map<Integer, Player> getPlayers() {
        Map<String, Object> paramMap = new HashMap<>();
        return jdbcTemplate.queryForObject(SELECT_PLAYERS, paramMap, new PlayersRowMapper());
    }

    @Override
    public Player getPlayer(int playerId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("playerId",playerId);
        return jdbcTemplate.queryForObject(SELECT_PLAYER, paramMap, new PlayerRowMapper());
    }

    @Override
    public void addPlayer(Player player) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("playerId",player.getPlayerId());
        paramMap.put("money",player.getMoney());
        jdbcTemplate.update(INSERT_PLAYER,paramMap);
    }

    @Override
    public String getOperations(Player player) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("playerId",player.getPlayerId());
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(LOG, paramMap);
        StringBuilder strBuilder = new StringBuilder();
        while (rowSet.next()) {
            strBuilder.append(rowSet.getInt(1) + " " + rowSet.getInt(2) + " " +
                    rowSet.getString(3) + " " + rowSet.getDouble(4) + " " +
                    rowSet.getDouble(5) + " " + rowSet.getString(6) + "\n");
        }
        return strBuilder.toString();
    }
}
