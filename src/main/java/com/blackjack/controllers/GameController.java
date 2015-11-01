package com.blackjack.controllers;

import com.blackjack.PlayerDao;
import com.blackjack.objects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.*;

@Controller
@RequestMapping("/")
public class GameController {

    @Autowired
    Dealer dealer;
    @Autowired
    PlayerDao playerDao;
    @Autowired
    Result result;
    Map<Integer,Player> players;
    int counter = 0;

    @PostConstruct
    public void init(){
        players = playerDao.getPlayers();
    }

    @RequestMapping(value = "/deal", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody Result deal(@RequestParam("playerId") Integer playerId,
                                     @RequestParam("bet") double bet) {
        Player player = getPlayer(playerId);
        player.setBet(bet);
        if (dealer.getScore() == 0) {
            firstHand(player);
        }
        return getResult(player);
    }

    @RequestMapping(value = "/score", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody Result getScore(@RequestParam("playerId") Integer playerId
    ) {
        Player player = getPlayer(playerId);
        return getResult(player);
    }

    @RequestMapping(value = "/hit", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody Result hit(@RequestParam("playerId") Integer playerId) {
        Player player = getPlayer(playerId);
        newCard(player);
        counter ++;
        checkPlayerPoints(player);
        return getResult(player);
    }

    @RequestMapping(value = "/stand", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody Result stand(@RequestParam("playerId") Integer playerId) {
        Player player = getPlayer(playerId);
        player.setStand(true);
        return getResult(player);
    }

    @RequestMapping(value = "/rebet", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody Player rebet(@RequestParam("playerId") Integer playerId) {
        Player player = getPlayer(playerId);
        if (player.getBet() == 0) {
            player.setBet(player.getLastBet());
        }
        return player;
    }

    @RequestMapping(value = "/newbet", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody Player newbet(@RequestParam("bet") double bet,
                                           @RequestParam("playerId") Integer playerId) {
        Player player = getPlayer(playerId);
        if (player.getBet() == 0) {
            player.setBet(bet);
        }
        return player;
    }

    @RequestMapping(value = "/cashin", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody Player cashin(@RequestParam("playerId") Integer playerId,
                                       @RequestParam("amount") double amount ) {
        Player player = getPlayer(playerId);
        player.cashIn(amount);
        playerDao.updateBalance(player);
        return player;
    }

    @RequestMapping(value = "/newset", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody void newSet(@RequestParam("playerId") Integer playerId) {
        Player player = getPlayer(playerId);
        newSet(player);
    }

    private Result getResult(Player player) {
        result.setPlayer(player);
        if (!result.isGameFinished()) {
            if (counter == 0 && checkBlackJack(player)) {
                IPlayer winner = checkResult(player);
                result.setWinner(winner);
                result.setIsBlackJack(true);
                result.setGameFinished(true);
            } else if (checkPlayerPoints(player) || player.isStand()) {
                checkDealerPoints();
                IPlayer winner = checkResult(player);
                result.setWinner(winner);
                result.setGameFinished(true);
            }
        }
        return result;
    }

    public Player getPlayer(Integer playerId) {
        Player player = null;
        if (players.containsKey(playerId)) {
            player = players.get(playerId);
        } else {
            player = new Player();
            player.setPlayerId(playerId);
            player.setMoney(5000.0);
            players.put(playerId,player);
            playerDao.addPlayer(player);
        }
        return player;
    }

    private void firstHand(Player player) {
        newCard(dealer);
        newCard(player);
        newHiddenCard(dealer);
        newCard(player);
//        checkBlackJack(player);
//        checkPlayerPoints(player);
    }

    private IPlayer checkResult(Player player) {
        IPlayer result = null;
        if (player.getScore() > 21) {
            player.updateMoney(-player.getBet());
            playerDao.updateBalance(player);
            result = dealer;
        } else if (dealer.getScore() > 21) {
            player.updateMoney(player.getBet());
            playerDao.updateBalance(player);
            result = player;
        } else if (player.getScore() > dealer.getScore()) {
            player.updateMoney(player.getBet());
            playerDao.updateBalance(player);
            result = player;
        } else if (player.getScore() < dealer.getScore()) {
            player.updateMoney(-player.getBet());
            playerDao.updateBalance(player);
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
        dealer.setHiddenScore(0);
        result = new Result();
        result.setDealer(dealer);
        player.setAceAmount(0);
        player.setCards(new ArrayList<Card>());
        player.setScore(0);
        player.setLastBet(player.getBet());
        player.setBet(0);
        player.setStand(false);
        counter = 0;
    }

    private boolean checkBlackJack(Player player) {
        if (dealer.getScore() == 21 && player.getScore() != 21) {
            player.updateMoney(-player.getBet());
            playerDao.updateBalance(player);
            return true;
        } else if (player.getScore() == 21 && dealer.getScore() != 21) {
            player.updateMoney(1.5 * player.getBet());
            playerDao.updateBalance(player);
            return true;
        }
        return false;
    }

    private boolean checkDealerPoints() {
        boolean result = false;
        Card card = dealer.getCards().get(1);
        if (card.isHiden()) {
            card.setHiden(false);
            dealer.updateScore(card.getPoints());
        }
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
        Card card = dealer.card();
        player.takeCard(card);
    }

    private void newHiddenCard(Dealer player) {
        Card card = dealer.card();
        player.updateHiddenScore(player.getScore());
        player.takeHiddenCard(card);
    }

}
