package com.company.backend;

import com.company.FightBot;
import com.company.frontend.ButtonMaker;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.company.SendMessageText.attackMessage;
import static com.company.SendMessageText.startBattle;

public class Battle {
    List<Fighter> fighters;
    FightBot fightBot;

    class Fighter{
        Player player;
        PartOfBody attackBody;
        Set<PartOfBody> blockBody;

        public Fighter(Player player) {
            this.player = player;
        }
    }

    public Battle(Player player1, Player player2, FightBot fightBot) {
        fighters = new ArrayList<>();
        fighters.add(new Fighter(player1));
        fighters.add(new Fighter(player2));
        this.fightBot = fightBot;
    }

    public void startBattle() {
        fighters.forEach(p -> {
            fightBot.sendMessageInChat(getSendMessage(p.player, startBattle));
            createFightMessage(p.player);
        });
    }


    SendMessage getSendMessage(Player player, String textMessage) {
        SendMessage message = new SendMessage()
                .setChatId(player.getChatId())
                .setText(textMessage);
        return message;
    }

    public SendMessage createFightMessage(Player player) {
        SendMessage sendMessage = getSendMessage(player, attackMessage);
        String[] buttonText = PartOfBody.getBodyTextList();
        String[] callBackData = PartOfBody.getBodyDataList();
        InlineKeyboardMarkup markup = ButtonMaker.makeInlineKeyboardMarkup(buttonText.length, 1, buttonText, callBackData);
        return sendMessage.setReplyMarkup(markup);
    }


}
