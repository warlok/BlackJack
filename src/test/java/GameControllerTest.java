
import com.blackjack.controllers.GameController;
import com.blackjack.objects.Card;
import com.blackjack.objects.Dealer;
import com.blackjack.objects.Player;
import com.blackjack.objects.Result;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.Assert.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class GameControllerTest {

    @Autowired
    GameController controller;
    Gson gson = new Gson();
    MockMvc mock;
    @Autowired
    Dealer dealer;
    Player player;

    @Before
    public void init() {
        mock = standaloneSetup(controller).build();
        player = controller.getPlayer(1);
        try {
            mock.perform(get("/newset").param("playerId", "1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMoney() throws Exception {
        double money = player.getMoney();
        player.setMoney(10.0);
        mock.perform(get("/deal").param("playerId", "1").param("bet", "50"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/money"));
        player.setMoney(money);
    }

    @Test
    public void testGameStarted() throws Exception {
        dealer.setScore(20);
        mock.perform(get("/deal").param("playerId", "1").param("bet", "50"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/gamestarted"));
    }

    @Test
    public void testCashIn() throws Exception {
        mock.perform(get("/cashin").param("playerId", "1").param("amount", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gson.toJson(player)));
    }

    @Test
    public void testPlayerBlackJack() throws Exception {
        Card card1 = new Card();
        card1.setPoints(11);
        card1.setValue("A");
        Card card2 = new Card();
        card2.setPoints(10);
        card2.setValue("10");
        player.takeCard(card1);
        player.takeCard(card2);
        player.setBet(40);
        dealer.takeCard(card2);
        dealer.takeCard(card2);
        Result result = new Result();
        result.setDealer(dealer);
        result.setPlayer(player);
        result.setGameFinished(true);
        result.setWinner(player);
        result.setIsBlackJack(true);
        mock.perform(get("/firstdeal").param("playerId", "1").param("bet", "40"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gson.toJson(result)));
    }

    @Test
    public void testDealerBlackJack() throws Exception {
        Card card1 = new Card();
        card1.setPoints(11);
        card1.setValue("A");
        Card card2 = new Card();
        card2.setPoints(10);
        card2.setValue("10");
        player.takeCard(card2);
        player.takeCard(card2);
        player.setBet(40);
        dealer.takeCard(card1);
        dealer.takeCard(card2);
        Result result = new Result();
        result.setDealer(dealer);
        result.setPlayer(player);
        result.setGameFinished(true);
        result.setWinner(dealer);
        result.setIsBlackJack(true);
        mock.perform(get("/firstdeal").param("playerId", "1").param("bet", "40"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gson.toJson(result)));
    }

    @Test
    public void testChangingAceValue() throws Exception {
        Card card1 = new Card();
        card1.setPoints(11);
        card1.setValue("A");
        Card card2 = new Card();
        card2.setPoints(11);
        card2.setValue("A");
        Card card3 = new Card();
        card3.setPoints(11);
        card3.setValue("A");
        player.takeCard(card2);
        player.takeCard(card2);
        player.takeCard(card3);
        player.setBet(40);
        dealer.takeCard(card1);
        dealer.takeCard(card2);
        Result result = new Result();
        result.setDealer(dealer);
        result.setPlayer(player);
        mock.perform(get("/score").param("playerId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(gson.toJson(result)));
        assertEquals(13,player.getScore());
    }

}
