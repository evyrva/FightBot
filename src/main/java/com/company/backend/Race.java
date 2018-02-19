package com.company.backend;

import java.util.Arrays;

public enum Race {
    ELF{
        @Override
        public String toString() {
            return "Эльф";
        }
    },
    HUMAN{
        @Override
        public String toString() {
            return "Человек";
        }
    },
    ORC{
        @Override
        public String toString() {
            return "Орк";
        }
    };
    public static boolean isRace(String string){
        for (Race race : Race.values()){
            if (race.name().toLowerCase().equals(string.toLowerCase())) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Race{}";
    }
}
