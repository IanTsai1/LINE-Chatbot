package com.line.practice;

import java.util.List;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.linecorp.bot.model.event.*;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.client.MessageContentResponse;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.*;
import com.linecorp.bot.model.message.AudioMessage;
import com.linecorp.bot.client.LineBlobClient;
import com.linecorp.bot.client.LineBlobClient;



import java.util.concurrent.ExecutionException;
import java.lang.InterruptedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@LineMessageHandler
public class EchoController{
	private final Logger log = LoggerFactory.getLogger(EchoApplication.class);
	private final LineMessagingClient lineMessagingClient;
	private String id = "U65dd32085a623a3f0f00c862d15ff930";
	  
	public EchoController(LineMessagingClient lineMessagingClient) {
		this.lineMessagingClient = lineMessagingClient;
	}

	@EventMapping //runs when MessageEvent is recorded but will only echo when it's a "TextMessageContent"
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
    	log.info("Text message event: " + event);
    	lineMessagingClient.replyMessage(new ReplyMessage(event.getReplyToken(), new TextMessage("I'm in handleTextMessage"),
                false));
    	
        TextMessageContent message =  event.getMessage();
        final String originalMessageText = message.getText();
        System.out.println("\n" + "I'm after first one " + "\n");
        lineMessagingClient.pushMessage(new PushMessage(id, new TextMessage(originalMessageText),false));
       
    }
	
	@EventMapping 
    public void handleAudioMessageEvent(MessageEvent<AudioMessageContent> event) {
		log.info("Audio Message Event: " + event);
		lineMessagingClient.replyMessage(new ReplyMessage(event.getReplyToken(), new TextMessage("I'm in handleAudioMessage"),
                false));
    	AudioMessageContent message = (AudioMessageContent) event.getMessage();
		
		final LineBlobClient client = LineBlobClient
				.builder("cMazcT01i+TyKb6Pq7ZGKXvDKCAwduhg8jtS1yX+iwl4lVY2iZQEQsk9nrxF1JnFhGqNCnD2L+XcV7UQBbOBdCF6ZU6kXg6t82BTV51n8lL88bYjUBTVY6PqOZKkKfh7Zq8LY333ukRqnMkvEXN1jwdB04t89/1O/w1cDnyilFU=")
				.build();
		
		final MessageContentResponse messageContentResponse;
		
		try {
		    messageContentResponse = client.getMessageContent(message.getId()).get();
		    
		    //save in computer
		    Path audioFilePath = Paths.get("C:/Users/Ian/Desktop/Intumit/Tasks/Java/line_chatbot-1/AudioFiles/audio.m4a");
		    Files.copy(messageContentResponse.getStream(), audioFilePath, StandardCopyOption.REPLACE_EXISTING);
		    
		    //tmp save
		    Path tempFile = Files.createTempFile("foo", "bar");
		    Files.copy(messageContentResponse.getStream(), tempFile);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}
    }
	    
    @EventMapping //runs when event is recorded
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }
}



