package com.company.backend;

import java.util.Arrays;

public enum Race {
    ELF,
    HUMAN,
    ORC;
    public static boolean isRace(String string){
        for (Race race : Race.values()){
            if (race.name().equals(string)) return true;
        }
        return false;
    }
}
