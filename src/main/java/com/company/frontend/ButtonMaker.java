package com.company.frontend;

import com.company.backend.Race;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static com.company.ButtonText.elfButton;

public class ButtonMaker {
    public static InlineKeyboardMarkup makeInlineKeyboardMarkup(int numbersOfRows, int numbersOfColumns,
                                                                String[] buttonsText, String[] callbackData){

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < numbersOfRows; i++) {
            List<InlineKeyboardButton> rowInLine = new ArrayList<>(numbersOfColumns);
            for (int j = 0; j < numbersOfColumns; j++) {
                rowInLine.add(new InlineKeyboardButton()
                        .setText(buttonsText[count])
                        .setCallbackData(callbackData[count++]));
            }
            rowsInLine.add(rowInLine);
        }
        return markup;
    }
}
