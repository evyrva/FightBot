package com.company.backend;

import com.company.FightBot;
import com.company.frontend.ButtonMaker;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.*;

import static com.company.SendMessageText.*;

public class Battle {
    Map<Integer, Fighter> fighters;
    FightBot fightBot;

    public class Fighter {
        Player player;
        Integer messegeId;
        PartOfBody attackBody;
        Set<PartOfBody> blockBody = new HashSet<>();
        boolean roundIsOver;

        public Fighter(Player player, Integer messegeId) {
            this.player = player;
            this.messegeId = messegeId;
        }

        public void startFirstAttack() {
            fightBot.sendMessageInChat(editIntoAttackMessage(this));
        }

        public EditMessageText startAttack() {
            return editIntoAttackMessage(this);
        }

        public EditMessageText startBlock() {
            return editIntoBlockMessage(this);
        }
    }

    public Battle(Player player, Integer messageId, FightBot fightBot) {
        fighters = new HashMap<>();
        fighters.put(messageId, new Fighter(player, messageId));
        this.fightBot = fightBot;
    }

    public void addFighter(Player player, Integer messageId) {
        fighters.put(messageId, new Fighter(player, messageId));
    }


    SendMessage getSendMessage(Player player, String textMessage) {
        SendMessage message = new SendMessage()
                .setChatId(player.getChatId())
                .setText(textMessage);
        return message;
    }

    public EditMessageText editIntoAttackMessage(Fighter fighter) {
        EditMessageText messageNew = new EditMessageText()
                .setChatId(fighter.player.getChatId())
                .setMessageId(fighter.messegeId)
                .setText(attackMessage);
        String[] buttonText = PartOfBody.getBodyTextList();
        String[] callBackData = PartOfBody.getBodyDataList();
        InlineKeyboardMarkup markup = ButtonMaker.makeInlineKeyboardMarkup(buttonText.length, 1, buttonText, callBackData);
        return messageNew.setReplyMarkup(markup);
    }

    public EditMessageText editIntoBlockMessage(Fighter fighter) {
        EditMessageText messageNew = new EditMessageText()
                .setChatId(fighter.player.getChatId())
                .setMessageId(fighter.messegeId)
                .setText(fighter.blockBody.isEmpty() ? block1Message : block2Message);
        String[] buttonText, callBackData;
        if (fighter.blockBody.isEmpty()) {
            buttonText = PartOfBody.getBodyTextList();
            callBackData = PartOfBody.getBodyDataList();
        } else {
            System.out.println("Первое место для блокировки :" + fighter.blockBody.toArray(new PartOfBody[1])[0]);
            buttonText = PartOfBody.getBodyTextList(fighter.blockBody.toArray(new PartOfBody[1])[0]);
            System.out.println("места для второй блокировки;");
            Arrays.stream(buttonText).forEach(System.out::println);
            callBackData = PartOfBody.getBodyDataList(fighter.blockBody.toArray(new PartOfBody[1])[0]);
        }
        InlineKeyboardMarkup markup = ButtonMaker.makeInlineKeyboardMarkup(buttonText.length, 1, buttonText, callBackData);
        return messageNew.setReplyMarkup(markup);
    }



    public void runBattle(Update update) {
        final String textOfMessage = update.getCallbackQuery().getMessage().getText();
        final String dataOfQuery = update.getCallbackQuery().getData();
        Fighter fighter = fighters.get(update.getCallbackQuery().getMessage().getMessageId());
        switch (textOfMessage) {
            case attackMessage:
                fighter.attackBody = PartOfBody.valueOf(dataOfQuery);
                fightBot.sendMessageInChat(fighter.startBlock());
                break;
            case block1Message:
                fighter.blockBody.add(PartOfBody.valueOf(dataOfQuery));
                fightBot.sendMessageInChat(fighter.startBlock());
                break;
            case block2Message:
                fighter.blockBody.add(PartOfBody.valueOf(dataOfQuery));
//                fightBot.sendMessageInChat(fighter.startBlock());
                endOfRound(fighter);
                break;
        }
    }

    public void endOfRound(Fighter fighter) {
        fighter.roundIsOver = true;
        Fighter partner = fighters.values().stream().filter(f -> f.messegeId != fighter.messegeId).findFirst().get();
        String messageText = "";
        if (partner.roundIsOver) {
            if (!fighter.blockBody.contains(partner.attackBody)) {
                messageText += partner.player.hit(fighter.player, partner.attackBody);
            } else {
                messageText += fighter.player.getNickName() + blockText + partner.attackBody;
            }
            if (!fighter.blockBody.contains(fighter.attackBody)) {
                messageText += fighter.player.hit(partner.player, fighter.attackBody);
            } else {
                messageText += partner.player.getNickName() + blockText + fighter.attackBody;
            }
            partner.attackBody = null;
            partner.blockBody = new HashSet<>();
            partner.roundIsOver = false;
            fighter.attackBody = null;
            fighter.blockBody = new HashSet<>();
            fighter.roundIsOver = false;
            SendMessage message = new SendMessage()
                    .setChatId(fighter.player.getChatId())
                    .setText(messageText);
            fightBot.sendMessageInChat(message);
            fightBot.sendMessageInChat(message.setChatId(partner.player.getChatId()));

            if (partner.player.getStatement().getCurrentHealth() <= 0 || fighter.player.getStatement().getCurrentHealth() <= 0){
                if (partner.player.getStatement().getCurrentHealth() <= 0 && fighter.player.getStatement().getCurrentHealth() <= 0){
                    messageText += drawText;
                    partner.player.setExperiance(partner.player.getExperiance() + 2);
                    fighter.player.setExperiance(fighter.player.getExperiance() + 2);
                }else if (partner.player.getStatement().getCurrentHealth() <= 0){
                    sendEditMessage(partner, loserText);
                    sendEditMessage(fighter, winnerText);
                    fighter.player.setExperiance(fighter.player.getExperiance() + 5);
                }else if (fighter.player.getStatement().getCurrentHealth() <= 0) {
                    sendEditMessage(fighter, loserText);
                    sendEditMessage(partner, winnerText);
                    partner.player.setExperiance(partner.player.getExperiance() + 5);
                }
                partner.player.setBattle(null);
                partner.player.getStatement().setCurrentHealth(partner.player.getStatement().getHealth());
                fighter.player.setBattle(null);
                fighter.player.getStatement().setCurrentHealth(fighter.player.getStatement().getHealth());
            }else{
                fighter.startFirstAttack();
                partner.startFirstAttack();
            }
        }else {
            sendEditMessage(fighter, waitingEndOfRounds);
            return;
        }
    }

    private void sendEditMessage(Fighter fighter, String text) {
        EditMessageText messageNew = new EditMessageText()
                .setChatId(fighter.player.getChatId())
                .setMessageId(fighter.messegeId)
                .setText(text);
        fightBot.sendMessageInChat(messageNew);
    }

    public Collection<Fighter> getFighters() {
        return fighters.values();
    }


}
