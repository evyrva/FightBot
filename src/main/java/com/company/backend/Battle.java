package com.company.backend;

import java.util.HashSet;
import java.util.Set;

public class Battle {
    Set<Player> fighters;

    public Battle(Player player1, Player player2){
        fighters = new HashSet<>();
        fighters.add(player1);
        fighters.add(player2);
    }

}
