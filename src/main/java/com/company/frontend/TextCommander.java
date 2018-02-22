package com.company.frontend;

import com.company.FightBot;
import com.company.backend.Battle;
import com.company.backend.PartOfBody;
import com.company.backend.Player;
import com.company.backend.Race;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Map;

import static com.company.SendMessageText.*;

public class TextCommander {
    Update update;
    Map<Integer, Player> players;
    FightBot fightBot;

    public TextCommander(Update update, Map<Integer, Player> players, FightBot fightBot) {
        this.fightBot = fightBot;
        this.update = update;
        this.players = players;
    }

    private Message sendMessageInChat(SendMessage sendMessage) {
        try {
            return fightBot.execute(sendMessage); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendReplyMessage() {
        String textMessage = update.getMessage().getText();
        if (textMessage.length() < 1 || !Commands.isCommands(textMessage.substring(1))) {
            sendMessageInChat(createErrorMessage());
            return;
        }

        if (!players.containsKey(update.getMessage().getFrom().getId())) {
            sendMessageInChat(createStartMessage());
            return;
        }

        final Commands commands = Commands.valueOf(textMessage.substring(1).toUpperCase());

        switch (commands) {
            case START:
                sendMessageInChat(createStartMessage());
                break;
            case SHOW_ME:
                sendMessageInChat(createShowMeMessage());
                break;
            case FIGHT:
                Player player = players.get(update.getMessage().getFrom().getId());
                if (player.getBattle()!=null){
                    sendMessageInChat(getSendMessage(update.getMessage().getChatId(), inBattleYet));
                }
                Player partner;
                if ((partner = getFightWaiting()) != null) {
                    partner.setWaitingFight(false);
                    System.out.println("want to set Battle at player" + player.getNickName());
                    player.setBattle(partner.getBattle());
                    System.out.println("set Battle at player" + player.getNickName());

                    partner.getBattle().addFighter(player,sendMessageInChat(createFightWaitingMessage()).getMessageId());
                    sendMessageInChat(getSendMessage(partner.getChatId(), "Битва началась!!"));
                    player.getBattle().getFighters().forEach(p -> p.startFirstAttack());
                } else {
                    Message msg = sendMessageInChat(createFightWaitingMessage());
                    player.setWaitingFight(true);
                    System.out.println("Create new battle at player" + player.getNickName());
                    player.setBattle(new Battle(player, msg.getMessageId(), fightBot));
                }
                break;
            default:
                sendMessageInChat(createErrorMessage());
        }

    }

    public SendMessage createStartMessage() {
        long chatId = update.getMessage().getChatId();
        int playerId = update.getMessage().getFrom().getId();
        String name = update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName();
        players.put(playerId, new Player(playerId, chatId, name, false));
        String textMessage = chooseRaceMessage;
        SendMessage message = getSendMessage(chatId, textMessage);
        String[] buttonText = {elfButton, humanButton, orcButton};
        String[] callbackData = {Race.ELF.name(), Race.HUMAN.name(), Race.ORC.name()};
        InlineKeyboardMarkup markup = ButtonMaker.makeInlineKeyboardMarkup(3, 1, buttonText, callbackData);
        message.setReplyMarkup(markup);
        return message;
    }

    public SendMessage createShowMeMessage() {
        long chatId = update.getMessage().getChatId();
        int playerId = update.getMessage().getFrom().getId();
        return getSendMessage(chatId, players.get(playerId).toString());
    }

    public SendMessage createErrorMessage() {
        long chatId = update.getMessage().getChatId();
        String textMessage = errorText;
        return getSendMessage(chatId, textMessage);
    }

    public Player getFightWaiting() {
        for (Player player : players.values()) {
            if (player.isWaitingFight()) {
                return player;
            }
        }
        return null;
    }

    public SendMessage createFightWaitingMessage() {
        long chatId =update.hasMessage() ? update.getMessage().getChatId() : update.getCallbackQuery().getMessage().getChatId();
        String textMessage = waitingToFight;
        SendMessage sendMessage = getSendMessage(chatId, textMessage);
        String[] buttonText = {cancel};
        String[] callbackData = {cancel};
        InlineKeyboardMarkup markup = ButtonMaker.makeInlineKeyboardMarkup(1, 1, buttonText, callbackData);
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }



    SendMessage getSendMessage(long chatId, String textMessage) {
        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(textMessage);
        return message;
    }
}
