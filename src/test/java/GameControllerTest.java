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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by dean on 10/27/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class GameControllerTest {

    GameController controller;
    MockMvc mock;

//    @Autowired
//    Dealer dealer;

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

}
