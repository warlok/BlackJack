import com.blackjack.PlayerDao;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import com.blackjack.objects.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.SchemaOutputResolver;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class PlayerDaoTest {

    @Autowired
    PlayerDao playerDao;


    @Test
    public void testGetPlayer() {
        assertEquals(1,playerDao.getPlayer(1).getPlayerId());
        assertEquals(2,playerDao.getPlayer(2).getPlayerId());
    }

    @Test
    public void testInsertPlayer() {
        Player player = new Player();
        player.setPlayerId(7);
        player.setMoney(7000);
        playerDao.addPlayer(player);
        assertTrue(player.getMoney() == playerDao.getPlayer(7).getMoney());
    }

    @Test
    public void testGetPlayers() {
        Map<Integer,Player> players = playerDao.getPlayers();
        assertTrue(players.size() == 3);
    }

    @Test
    public void testGetBalance() {
        Player player = playerDao.getPlayer(1);
        assertTrue(1000 == playerDao.getBalance(player));
    }

    @Test
    public void testUpdateBalance() {
        Player player = playerDao.getPlayer(1);
        player.setMoney(7000);
        playerDao.updateBalance(player);
        assertTrue(7000 == playerDao.getPlayer(1).getMoney());
    }

    @Test
    public void testGetOperations() {
        Player player = playerDao.getPlayer(1);
        System.out.println(playerDao.getOperations(player));
        String str = "1 1 deal 50.0 1000.0 2015-10-29\n" +
                "2 1 HIT 50.0 1000.0 2015-10-29\n" +
                "3 1 STAND 50.0 1000.0 2015-10-29\n" +
                "4 1 WIN 0.0 1040.0 2015-11-02\n";
        assertEquals(str, playerDao.getOperations(player));
    }

    @Test
    public void testAddOperation() {
        Player player = playerDao.getPlayer(1);
        player.setMoney(1040);
        player.setBet(0);
        playerDao.addLogOperation(player,"WIN");
        System.out.println(playerDao.getOperations(player));
        String str = "1 1 deal 50.0 1000.0 2015-10-29\n" +
                "2 1 HIT 50.0 1000.0 2015-10-29\n" +
                "3 1 STAND 50.0 1000.0 2015-10-29\n" +
                "4 1 WIN 0.0 1040.0 2015-11-02\n";
        assertEquals(str, playerDao.getOperations(player));
    }

}
