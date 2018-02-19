package com.company.backend;

public class Player {
    private int playerId;
    private long chatId;
    private String nickName;
    private int experience;
    private boolean isWaitingFight;
    private Race race;
    private Statement statement;
    private Battle battle= null;

    public Player(int playerId, long chatId, String nickName, boolean isWaitingFight) {
        this.playerId = playerId;
        this.chatId = chatId;
        this.nickName = nickName;
        this.isWaitingFight = isWaitingFight;

    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(int playerId) {
        this.chatId = chatId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getExperiance() {
        return experience;
    }

    public void setExperiance(int experiance) {
        this.experience = experiance;
    }

    public boolean isWaitingFight() {
        return isWaitingFight;
    }

    public void setWaitingFight(boolean waitingFight) {
        isWaitingFight = waitingFight;
    }

    public Race getRace() {
        return race;
    }

    public Battle getBattle() {
        return battle;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }

    public void setRace(Race race) {
        this.race = race;
        statement = new Statement(race);
    }

    @Override
    public String toString() {
        return "Игрок" +"\n" +
                "nick: " + nickName +"\n" +
                "Раса: " + race +"\n" +
                "опыт=" + experience +"\n" +
                statement +"\n";
    }
}
