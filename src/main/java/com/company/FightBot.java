package com.company;

import com.company.backend.Player;
import com.company.backend.Race;
import com.company.frontend.ButtonMaker;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.*;

import static com.company.ButtonText.*;
import static com.company.backend.Race.*;
import static java.lang.Math.toIntExact;


public class FightBot extends TelegramLongPollingBot {
    Map<Long, Player> players;

    FightBot() {
        super();
        players = new HashMap<>();
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        System.out.println(update.toString());


        if (update.hasMessage()) {
            if (!players.containsKey(update.getMessage().getChatId())) {
                startMess(update);
            }
        } else if (update.hasCallbackQuery()) {
            if (Race.isRace(update.getCallbackQuery().getData())){
                startCallbackHandler(update);
            }
        }
    }


    private void startMess(Update update) {
        long chatId = update.getMessage().getChatId();
        String name = update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName();
        players.put(chatId, new Player(chatId, name, false));
        SendMessage message = new SendMessage()
                .setChatId(chatId)
                .setText(chooseRaceMessage);
        String[] buttonText = {elfButton, humanButton, orcButton};
        String[] callbackData = {Race.ELF.name(), Race.HUMAN.name(), Race.ORC.name()};
        InlineKeyboardMarkup markup = ButtonMaker.makeInlineKeyboardMarkup(3, 1, buttonText, callbackData);
        message.setReplyMarkup(markup);
        sendMessageInChat(message);
    }

    private void startCallbackHandler(Update update) {
        Race race = Race.valueOf(update.getCallbackQuery().getData());
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        EditMessageText messageNew = new EditMessageText()
                .setChatId(chatId)
                .setMessageId((int) messageId)
                .setText(answerChooseRace);

        sendMessageInChat(messageNew);
    }

    private void sendMessageInChat(BotApiMethod message) {
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
