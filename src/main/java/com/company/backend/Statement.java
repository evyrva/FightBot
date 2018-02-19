package com.company.backend;

public class Statement {
    private int level = 0;
    private int strength = 4;
    private int dexerity = 4;
    private int luck = 4;
    private int survivability = 4;
    private int health = survivability*5;

    public Statement(Race race) {
        getLevel(race);
    }

    void getLevel(Race race) {
        level++;
        switch (race) {
            case ELF: dexerity++;
            break;
            case ORC: strength++;
            break;
            case HUMAN: luck++;
        }
    }

    @Override
    public String toString() {
        return "Показатели персонажа" +"\n" +
                "Уровень=   " + level +"\n" +
                "Сила=      " + strength +"\n" +
                "Ловкость=  " + dexerity +"\n" +
                "Удача=     " + luck +"\n" +
                "Живучесть= " + survivability +"\n" +
                "Здоровье=  " + health +"\n" ;
    }
}
