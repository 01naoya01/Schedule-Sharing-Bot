package com.example.vol6;

import static java.util.Collections.singletonList;
import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;


import org.springframework.beans.factory.annotation.Autowired;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.JoinEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

//botに送った何らかのメッセージを扱うコントローラー
@Slf4j
@LineMessageHandler
public class ScheduleSharingController {
    boolean debug = true;
    
    @Autowired
    private LineMessagingClient lineMessagingClient;


    // botに送ったテキストメッセージを処理のコントロール
    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent message = event.getMessage();
        handleTextContent(event.getReplyToken(), event, message);
    }

    // グループに参加したときに呼び出されるメソッド
    @EventMapping
    public void handleJoinEvent(JoinEvent event) {
        String replyToken = event.getReplyToken();
        this.replyText(replyToken, "はじめまして！予定共有Botです！\nGoogle Calendarで予定を共有します！\n「ヘルプ」と打てばヘルプを表示します！");
    }

    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, singletonList(message));
    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        reply(replyToken, messages, false);
    }

    private void reply(@NonNull String replyToken,
                       @NonNull List<Message> messages,
                       boolean notificationDisabled) {
        try {
            BotApiResponse apiResponse = lineMessagingClient
                    .replyMessage(new ReplyMessage(replyToken, messages, notificationDisabled))
                    .get();
            log.info("Sent messages: {}", apiResponse);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private void replyText(@NonNull String replyToken, @NonNull String message) {
        if (replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken must not be empty");
        }
        if (message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "……";
        }
        this.reply(replyToken, new TextMessage(message));
    }
    
    //botに送ったテキストメッセージを処理するところ
    private void handleTextContent(String replyToken, Event event, TextMessageContent content)
            throws Exception {
        final String text = content.getText();

        log.info("Got text message from replyToken:{}: text:{} emojis:{}", replyToken, text, content.getEmojis());
        
        if (text.equals("ヘルプ")) {
            this.reply(replyToken, new FlexMessageSupplier().help_get());  
            return;
        }

        if(text.length() < 4){
            return;
        }

        if (text.substring(0, 4).equals("予定追加")) {
            FlexMessageSupplier calendarMessage = new FlexMessageSupplier();
            calendarMessage.CalendarParam = createURL.get(text);
            this.reply(replyToken, calendarMessage.get());
        }
    }

    @Value
    private static class DownloadedContent {
        Path path;
        URI uri;
    }
}
