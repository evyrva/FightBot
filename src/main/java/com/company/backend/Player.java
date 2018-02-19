package com.company.backend;

public class Player {
    private long chatId;
    private String nickName;
    private int experiance;
    private boolean isWaitingFight;
    private Race race;

    public Player(long chatId, String nickName, boolean isWaitingFight) {
        this.chatId = chatId;
        this.nickName = nickName;
        this.isWaitingFight = isWaitingFight;
    }


    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getExperiance() {
        return experiance;
    }

    public void setExperiance(int experiance) {
        this.experiance = experiance;
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

    public void setRace(Race race) {
        this.race = race;
    }
}
