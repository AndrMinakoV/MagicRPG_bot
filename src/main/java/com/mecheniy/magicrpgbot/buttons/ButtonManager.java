package com.mecheniy.magicrpgbot.buttons;

import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class ButtonManager {

    public static Button createPlayersListButton() {
        return Button.primary("playersList", "Список игроков");
    }

    public static Button createRestartServerButton() {
        return Button.danger("restartServer", "Перезапустить сервер");
    }

    // Добавьте здесь другие методы для создания разных кнопок, если это необходимо.
}
