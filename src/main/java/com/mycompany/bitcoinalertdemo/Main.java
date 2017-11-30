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
/**
 *
 * @author himes
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        JsonObject bpi = 
                getJSON("https://api.coindesk.com/v1/bpi/currentprice/USD.json");
        String bpiPrint = bpi.toString();
        System.out.println(bpiPrint);
    }
    
    private static JsonObject getJSON(String sURL) throws IOException {
        URL url = new URL(sURL);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();
        
        JsonParser jp = new JsonParser();
        //use parser to take in input stream by casting httpconnection to InputStream in ISR
        JsonElement root = 
                jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject rootobj = root.getAsJsonObject();
        return rootobj;
    }
}
