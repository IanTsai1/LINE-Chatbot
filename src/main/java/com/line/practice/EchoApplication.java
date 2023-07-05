package com.line.practice;

import java.io.BufferedReader; 
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.lang.Thread;

import org.springframework.boot.SpringApplication; 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.line.practice.RestAPI.UserDataAPIService;


@Import(UserDataAPIService.class)
@SpringBootApplication
public class EchoApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(EchoApplication.class, args);
        
        String script = "C:\\Users\\Ian\\Desktop\\Intumit\\Tasks\\Java\\Line_ChatBot\\src\\main\\python\\app.py";
        //ProcessBuilder processBuilder = new ProcessBuilder("python", "/src/main/python/app.py");
        ProcessBuilder processBuilder = new ProcessBuilder("python",script);
        processBuilder.redirectErrorStream(true);
        
        
        Process process = processBuilder.start();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}


