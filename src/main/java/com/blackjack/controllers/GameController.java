package com.blackjack.controllers;

import com.blackjack.objects.Card;
import com.blackjack.objects.Dealer;
import com.blackjack.objects.IPlayer;
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
    Dealer dealer;
    Map<Integer,Player> players = new HashMap<>();
    int counter = 0;

    @RequestMapping(value = "/deal", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody List<List<Card>> deal(@RequestParam("playerId") Integer playerId
    ) {
        Player player = getPlayer(playerId);
        if (dealer.getScore() == 0) {
            firstHand(player);
        }
        ArrayList<List<Card>> firstSet = new ArrayList<>();
        firstSet.add(dealer.getCards());
        firstSet.add(player.getCards());
        return firstSet;
    }

    @RequestMapping(value = "/score", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody String getScore(@RequestParam("playerId") Integer playerId
    ) {
        Player player = getPlayer(playerId);
        if (counter == 0 && checkBlackJack(player)) {
            IPlayer viner = checkResult(player);
            counter ++;
            newSet(player);
            return viner.getClass().getSimpleName() + " has Black Jack and vin";
        } else if (checkPlayerPoints(player)) {
            checkDealerPoints();
            IPlayer viner = checkResult(player);
            return viner.getClass().getSimpleName() + " vin";
        }
        return "Dealer: " + dealer.getScore() + " Player:" + player.getScore();
    }

    private void firstHand(Player player) {
        newCard(dealer);
        newCard(player);
        newHiddenCard(dealer);
        newCard(player);
    }

    private IPlayer checkResult(Player player) {
        IPlayer result = null;
        if (player.getScore() > 21) {
            result = dealer;
        } else if (dealer.getScore() > 21) {
            result = player;
        } else if (player.getScore() > dealer.getScore()) {
            result = player;
        } else if (player.getScore() < dealer.getScore()) {
            result = dealer;
        } else if (player.getScore() == dealer.getScore()) {
            result = null;
        }
        return result;
    }

    private void newSet(Player player) {
        dealer.setAceAmount(0);
        dealer.setCards(new ArrayList<Card>());
        dealer.setScore(0);
        player.setAceAmount(0);
        player.setCards(new ArrayList<Card>());
        player.setScore(0);
    }

    private boolean checkBlackJack(Player player) {
        if (dealer.getScore() == 21 && player.getScore() != 21) {
            return true;
        } else if (player.getScore() == 21 && dealer.getScore() != 21) {
            return true;
        }
        return false;
    }

    private boolean checkDealerPoints() {
        boolean result = false;
        Card card = dealer.getCards().get(1);
        card.setHiden(false);
        dealer.updateScore(card.getPoints());
        if (dealer.getScore() == 21) {
            result = true;
        } else if (dealer.getScore() > 21 && dealer.hasAce()) {
            dealer.updateScore(-10);
            dealer.checkAce();
            checkDealerPoints();
        } else if (dealer.getScore() < 17) {
            newCard(dealer);
            checkDealerPoints();
        } else if (dealer.getScore() > 21) {
            result = true;
        }
        return result;
    }

    private boolean checkPlayerPoints(Player player) {
        boolean result = false;
        if (player.getScore() == 21) {
            result = true;
        } else if (player.getScore() > 21 && player.hasAce()) {
            player.updateScore(-10);
            player.checkAce();
            checkPlayerPoints(player);
        } else if (player.getScore() > 21) {
            result = true;
        }
        return result;
    }

    private void newCard(IPlayer player) {
        Card card = dealer.getCard();
        player.updateScore(card.getPoints());
        player.takeCard(card);
    }

    private void newHiddenCard(Dealer player) {
        Card card = dealer.getCard();
        card.setHiden(true);
        player.updateHiddenScore(player.getScore());
        player.updateHiddenScore(card.getPoints());
        player.takeCard(card);
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
