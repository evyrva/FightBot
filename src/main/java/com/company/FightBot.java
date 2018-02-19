package com.company;

import com.company.backend.Player;
import com.company.backend.Race;
import com.company.frontend.ButtonMaker;
import com.company.frontend.Commands;
import com.company.frontend.TextCommander;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.*;

import static com.company.SendMessageText.*;


public class FightBot extends TelegramLongPollingBot {
    Map<Integer, Player> players;

    FightBot() {
        super();
        players = new HashMap<>();
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        System.out.println(update.toString());


        if (update.hasMessage() && update.getMessage().hasText()) {
            TextCommander textCommander = new TextCommander(update, players, this);
            textCommander.sendReplyMessage();

        } else if (update.hasCallbackQuery()) {
            final String dataOfQuery = update.getCallbackQuery().getData();
            EditMessageText editMessageText = createEditMessage(update, "");
            if (Race.isRace(dataOfQuery)) {
                Race race = Race.valueOf(update.getCallbackQuery().getData());
                int playerId = update.getCallbackQuery().getFrom().getId();
                players.get(playerId).setRace(race);
                editMessageText = createEditMessage(update, answerChooseRace);
            }else if (cancel.equals(dataOfQuery)){
                editMessageText = createEditMessage(update, canselOperation);
            }
            sendMessageInChat(editMessageText);
        }
    }




    EditMessageText createEditMessage(Update update, String text) {
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        EditMessageText messageNew = new EditMessageText()
                .setChatId(chatId)
                .setMessageId((int) messageId)
                .setText(text);
        return messageNew;
    }

    public void sendMessageInChat(BotApiMethod message) {
        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearWebhook() throws TelegramApiRequestException {
        super.clearWebhook();
    }

    @Override
    public String getBotUsername() {
        return Config.NAME;
    }

    @Override
    public String getBotToken() {
        return Config.TOKEN;
    }



}
