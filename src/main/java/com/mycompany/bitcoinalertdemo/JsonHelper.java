/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bitcoinalertdemo;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author himes
 */
public class JsonHelper {
    
    protected static JsonObject getJSON(String sURL) throws IOException {
        URL url = new URL(sURL);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();
        
        JsonParser jp = new JsonParser();
        //use parser to take in input stream by casting httpconnection to InputStream in ISR
        JsonElement dump = 
                jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject dumpobj = dump.getAsJsonObject();
        return dumpobj;
    }
}
