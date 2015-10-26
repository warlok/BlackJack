package com.blackjack;

import com.blackjack.objects.Player;

/**
 * Created by dean on 10/26/15.
 */
public interface PlayerDAO {

    public Player getUserByUserName(String userName);

}
