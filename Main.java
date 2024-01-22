import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetMe;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Objects;

public class Main extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "6952504597:AAGuc_f4JWx3XuQS7EPrYz4Ie_u2Uk-fJ-k";
    private static final long ADMIN_CHAT_ID = 1081950449;

    private static String NameInApp;
    private static String Code;

    public static void main(String[] args) {
        Main bot = new Main();
        NameInApp= "vlas77";
        Code = "xxxxxxxxxx";
        bot.runBot();
    }

    private void runBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void verification(String userName, long chatId)
    {
        userName = userName.toLowerCase();
        NameInApp = NameInApp.toLowerCase();
        if(Objects.equals(userName, NameInApp)) {
            sendTelegramMessage(chatId, Code);
        } else
            sendTelegramMessage(chatId, "you was banned");
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            User user = update.getMessage().getFrom();
            long chatId = update.getMessage().getChatId();
            verification(user.getUserName(), chatId);
        }
    }
    private void sendUsernameToAdmin(String username) {
        String message = "Новый пользователь: @" + username;
        sendTelegramMessage(ADMIN_CHAT_ID, message);
    }

    @Override
    public String getBotUsername() {
        User botUser = null;
        try {
            GetMe getMe = new GetMe();
            botUser = execute(getMe);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        if (botUser != null) {
            return botUser.getUserName();
        } else {
            return "";
        }
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    private void sendTelegramMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
