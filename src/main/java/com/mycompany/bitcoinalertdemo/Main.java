/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bitcoinalertdemo;

import java.util.Date;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import com.google.gson.*;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.mail.Authenticator;

/**
 *
 * @author himes
 */
public class Main {
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        final float upperBound = 9500;
        final float lowerBound = 9000;
        
        Scanner input = new Scanner(System.in);
        System.out.println("Enter username to send email from:");
        final String username = input.nextLine();
        System.out.println("Enter password to send username from:");
        final String password = input.nextLine();
        
        Runnable getPrice = new Runnable()  {
            @Override
            public void run() {    
                try {
                    
                    JsonObject priceIndex =
                        JsonHelper.getJSON("https://api.coindesk.com/v1/bpi/currentprice/USD.json");
                    JsonElement bpiElement = priceIndex.get("bpi");
                    JsonElement uSDElement = bpiElement.getAsJsonObject().get("USD");
                    JsonObject uSDObj = uSDElement.getAsJsonObject();
                    float rate = uSDObj.get("rate_float").getAsFloat();
                    System.out.println(rate);
                    
                    if(rate > upperBound) {
                        sendMail(rate, upperBound, username, password, "above");
                    }
                    else if(rate < lowerBound) {
                        sendMail(rate, lowerBound, username, password, "below");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(getPrice, 0, 5, TimeUnit.MINUTES);
        
    }
    public static void sendMail
        (float rate, float bound, String username, String password, String msg) {
        
               
        Date date=java.util.Calendar.getInstance().getTime();  
        
        Properties smtp = new Properties();
        smtp.put("mail.smtp.auth", "true");
        smtp.put("mail.smtp.starttls.enable", "true");
        smtp.put("mail.smtp.host", "smtp.gmail.com");
        smtp.put("mail.smtp.port", "587");
        
        System.out.println("Sending notification email.");
        
        Session session = Session.getInstance(smtp, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        }); 
        
        try {
            System.out.println("Building Message.");
            DecimalFormat usd = new DecimalFormat();
            usd.setMaximumFractionDigits(2);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(username)); //send to self for now
            message.setSubject("Bitcoin just crossed " + msg + "  $" + usd.format(bound) + "!");
            message.setText("Bitcoin spot price is currently $" + usd.format(rate) + " on " + date + 
                " and has crossed " + msg + " the limit you set of " + usd.format(bound) + ".");

            Transport.send(message);

            System.out.println("Done!");
        } 
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

