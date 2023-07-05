package com.line.practice.chat;

import java.util.List; 
import java.lang.Thread;

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
import com.line.practice.EchoApplication;
import com.linecorp.bot.client.LineBlobClient;


import java.util.concurrent.ExecutionException;
import java.lang.InterruptedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.line.practice.RestAPI.UserData;



@LineMessageHandler
public class EchoController{
	private final Logger log = LoggerFactory.getLogger(EchoApplication.class);
	private final LineMessagingClient lineMessagingClient;
	private String id = "U65dd32085a623a3f0f00c862d15ff930";
	private String baseUrl = "http://localhost:8080/userdata";
	  
	public EchoController(LineMessagingClient lineMessagingClient) {
		this.lineMessagingClient = lineMessagingClient;
	}

	@EventMapping //runs when MessageEvent is recorded but will only echo when it's a "TextMessageContent"
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
    	log.info("Text message event: " + event);
    	
    	String userId = event.getSource().getUserId();
    	String userText = event.getMessage().getText();
    	String post_data_url = baseUrl + "/";
    	String post_id_url = "http://localhost:9090/getId";
    	
    	RestTemplate restTemplate = new RestTemplate();
    	
    	//sending to itself (8080 port)
    	HttpHeaders data_headers = new HttpHeaders();
    	data_headers.setContentType(MediaType.APPLICATION_JSON);
    	UserData tmpData = new UserData(userId, userText);
    	HttpEntity<UserData> data_requestEntity = new HttpEntity<>(tmpData, data_headers);
    	UserData post_data = restTemplate.postForObject(post_data_url, data_requestEntity, UserData.class);
    	System.out.println("Post id success: " + post_data.getUserId());
    	System.out.println("Post text success: " + post_data.getUserText());
    	
    	
    	//sending to 9090 port 
    	HttpHeaders id_headers = new HttpHeaders();
    	id_headers.setContentType(MediaType.TEXT_PLAIN);	
    	HttpEntity<String> id_requestEntity = new HttpEntity<>(userId, id_headers);
    	restTemplate.postForObject(post_id_url, id_requestEntity, String.class);

    	/*String get_url =  baseUrl + "/" + userId;
    	UserData get = restTemplate.getForObject(get_url, UserData.class);
    	
    	System.out.println("Get id success" + get.getUserId());
    	System.out.println("Get id success" + get.getUserText());*/
    	
    	/*
    	lineMessagingClient.replyMessage(new ReplyMessage(event.getReplyToken(), new TextMessage("I'm in handleTextMessage"),
                false));
        TextMessageContent message =  event.getMessage();
        final String originalMessageText = message.getText();
        lineMessagingClient.pushMessage(new PushMessage(id, new TextMessage(originalMessageText),false));*/
    }
	
	@EventMapping 
    public void handleAudioMessageEvent(MessageEvent<AudioMessageContent> event) {
		log.info("Audio Message Event: " + event);
		//lineMessagingClient.replyMessage(new ReplyMessage(event.getReplyToken(), new TextMessage("I'm in handleAudioMessage"),false));
    	AudioMessageContent message = (AudioMessageContent) event.getMessage();
		
		final LineBlobClient client = LineBlobClient
				.builder("cMazcT01i+TyKb6Pq7ZGKXvDKCAwduhg8jtS1yX+iwl4lVY2iZQEQsk9nrxF1JnFhGqNCnD2L+XcV7UQBbOBdCF6ZU6kXg6t82BTV51n8lL88bYjUBTVY6PqOZKkKfh7Zq8LY333ukRqnMkvEXN1jwdB04t89/1O/w1cDnyilFU=")
				.build();
		
		final MessageContentResponse messageContentResponse;
		
		try {
			String audio_url = "http://localhost:9090/getAudio";
			RestTemplate restTemplate = new RestTemplate();
			
			messageContentResponse = client.getMessageContent(message.getId()).get();
		    
			HttpHeaders id_headers = new HttpHeaders();
	    	id_headers.setContentType(MediaType.parseMediaType("audio/m4a"));	
	    	
	    	HttpEntity<InputStreamResource> id_requestEntity = new HttpEntity<>(new InputStreamResource(messageContentResponse.getStream()), id_headers);
	    	String transcribed_audio = restTemplate.postForObject(audio_url, id_requestEntity, String.class);

	    	 lineMessagingClient.pushMessage(new PushMessage(id, new TextMessage(transcribed_audio),false));
		    
		    //assign a variable to send data to port 9090
		    	//process data in python
		    //send push of the variable value
		    
		    //save in computer
	    	/*
		    String currentDir = System.getProperty("user.dir").replace("\\", "/");
		    Path audioFilePath = Paths.get(currentDir + "/AudioFiles/audio.m4a");
		    Files.copy(messageContentResponse.getStream(), audioFilePath, StandardCopyOption.REPLACE_EXISTING);
		    
		    
		    //tmp save
		    Path tempFile = Files.createTempFile("foo", "bar");
		    Files.copy(messageContentResponse.getStream(), tempFile);*/
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



