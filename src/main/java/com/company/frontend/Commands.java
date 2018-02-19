package com.company.frontend;

import com.company.FightBot;
import com.company.backend.Player;
import com.company.backend.Race;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Map;

import static com.company.SendMessageText.*;
import static com.company.SendMessageText.cancel;
import static com.company.SendMessageText.waitingToFight;

public enum Commands {
    START,
    SHOW_ME,
    ERROR,
    FIGHT;//{
//        @Override
//        public SendMessage makeCommand(Update update, Map<Integer, Player> players) {
//            int playerId = update.getMessage().getFrom().getId();
//            long chatId = update.getMessage().getChatId();
//            if (players.get(playerId).getBattle() == null){
//                return getSendMessage(chatId, inBattleYet);
//            }
//            for (Player player : players.values()){
//                if (player.isWaitingFight()){
//                    getSendMessage(player.getChatId(), startBattle + player.getNickName());
//
//                }
//            }
//
//            players.get(playerId).setWaitingFight(true);
//
//            String textMessage = waitingToFight;
//            SendMessage sendMessage = getSendMessage(chatId, textMessage);
//            String[] buttonText = {cancel};
//            String[] callbackData = {cancel};
//            InlineKeyboardMarkup markup = ButtonMaker.makeInlineKeyboardMarkup(1, 1, buttonText, callbackData);
//            sendMessage.setReplyMarkup(markup);
//            return sendMessage;
//        }
//    };

    public static boolean isCommands(String string) {
        for (Commands commands : Commands.values()) {
            if (commands.name().toLowerCase().equals(string.toLowerCase())) return true;
        }
        return false;
    }
}
