package com.blackjack.controllers;

import com.blackjack.objects.Card;
import com.blackjack.objects.Dealer;
import com.blackjack.objects.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by dean on 10/26/15.
 */
@Controller
@RequestMapping("/")
public class GameController {

    @Autowired
    Dealer dealer = new Dealer();
    Map<Integer,Player> players = new HashMap<>();

    @RequestMapping(value = "/deal", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody List<List<Card>> deal(@RequestParam("playerId") Integer playerId
    ) {
        Player player = getPlayer(playerId);
        dealer.takeCard(dealer.getCard());
        player.takeCard(dealer.getCard());
        Card hidenCard = dealer.getCard();
        hidenCard.setHiden(true);
        dealer.takeCard(hidenCard);
        player.takeCard(dealer.getCard());
        ArrayList<List<Card>> firstSet = new ArrayList<>();
        firstSet.add(dealer.getCards());
        firstSet.add(player.getCards());
        return firstSet;
    }

    @RequestMapping(value = "/hit", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody Card hit(@RequestParam("playerId") Integer playerId) {
        Player player = getPlayer(playerId);
        Card card = dealer.getCard();
        player.takeCard(dealer.getCard());
        return card;
    }

    @RequestMapping(value = "/stand", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody String stand() {
        return "You loose";
    }

    @RequestMapping(value = "/rebet", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody List<Card> rebet() {
        return null;
    }

    @RequestMapping(value = "/newbet", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody List<Card> newbet() {
        return null;
    }

    @RequestMapping(value = "/cashin", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody String cashin(@RequestParam("playerId") Integer playerId,
                                       @RequestParam("amount") double amount ) {
        Player player = getPlayer(playerId);
        player.cashIn(amount);
        return "On your account " + player.getMoney() + "$ now";
    }

    private Player getPlayer(Integer playerId) {
        Player player = null;
        if (players.containsKey(playerId)) {
            player = players.get(playerId);
        } else {
            player = new Player();
            player.setPlayerId(playerId);
            player.setMoney(5000.0);
            players.put(playerId,player);
        }
        return player;
    }

}
