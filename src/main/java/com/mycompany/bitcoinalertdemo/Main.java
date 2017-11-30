/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bitcoinalertdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import com.google.gson.*;
import java.net.HttpURLConnection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author himes
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
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
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(getPrice, 0, 1, TimeUnit.MINUTES);
    }
}
