package com.blackjack;

import com.blackjack.objects.Player;

import java.util.Map;

public interface PlayerDao {

    public void updateBalance(Player player);
    public double getBalance(Player player);
    public Map<Integer,Player> getPlayers();
    public Player getPlayer(int playerId);
    public void addPlayer(Player player);
    public String getOperations(Player player);

}
