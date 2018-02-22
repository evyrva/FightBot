package com.company.backend;

public class Statement {
    private int level = 0;
    private int strength = 4;
    private int dexerity = 4;
    private int luck = 4;
    private int survivability = 4;
    private int health = survivability*5;
    private int currentHealth = health;

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
                "Здоровье=  " +currentHealth + "/" + health +"\n" ;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexerity() {
        return dexerity;
    }

    public void setDexerity(int dexerity) {
        this.dexerity = dexerity;
    }

    public int getLuck() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck = luck;
    }

    public int getSurvivability() {
        return survivability;
    }

    public void setSurvivability(int survivability) {
        this.survivability = survivability;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
}
