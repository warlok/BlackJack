import com.blackjack.controllers.GameController;
import com.blackjack.objects.Dealer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class GameControllerTest {

    GameController controller;
    MockMvc mock;

    @Before
    public void init() {
        controller = new GameController();
        mock = standaloneSetup(controller).build();
    }

    @Test
    public void testMainPage() throws Exception {
        mock.perform(get("/deal").param("playerId","1")).andExpect(content().contentType(MediaType.APPLICATION_JSON));
//                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(jsonPath("$[0][0].suit").value(dealer.getCards().get(0).getSuit()));
    }

    @Test
    public void testCashIn() throws Exception {
        mock.perform(get("/cashin").param("playerId","1").param("amount","5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("On your account 5005.0$ now"));
    }

}
