package com.company.frontend;

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
//            String textMessage = 0;
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
