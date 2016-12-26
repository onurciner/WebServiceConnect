package com.onurciner.webserviceconnect;

import android.os.StrictMode;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Onur.Ciner on 22.12.2016.
 */

public class WebServiceSendData {

    public HttpURLConnection urlConnection;
    private String uri;
    private String data;

    private ReturnType returnType = ReturnType.STRING;
    private String requestMethod = "POST";
    private ArrayList<String> requestPropertyKey = new ArrayList<>();
    private ArrayList<String> requestPropertyValue = new ArrayList<>();
    private String requestPropertyType = null;
    private String character = "UTF-8";

    public WebServiceSendData setRequestMethod(MethodType methodType) {
        if (methodType.equals(MethodType.GET))
            requestMethod = "GET";
        else if (methodType.equals(MethodType.POST))
            requestMethod = "POST";
        else if (methodType.equals(MethodType.DELETE))
            requestMethod = "DELETE";
        else if (methodType.equals(MethodType.PATCH))
            requestMethod = "PATCH";
        else if (methodType.equals(MethodType.PUT))
            requestMethod = "PUT";
        return this;
    }

    public WebServiceSendData setRequestProperty(RequestPropertyType requestPropertyType) {
        if (requestPropertyType.equals(RequestPropertyType.APPLICATION_JSON))
            this.requestPropertyType = "application/json";
        else if (requestPropertyType.equals(RequestPropertyType.MULTIPART_FORM_DATA))
            this.requestPropertyType = "multipart-form-data";
        else if (requestPropertyType.equals(RequestPropertyType.APPLICATION_X_WWW_FORM_URLENCODED))
            this.requestPropertyType = "application/x-www-form-urlencoded";
        else if (requestPropertyType.equals(RequestPropertyType.APPLICATION_XML))
            this.requestPropertyType = "application/xml";
        else if (requestPropertyType.equals(RequestPropertyType.APPLICATION_BASE64))
            this.requestPropertyType = "application/base64";
        else if (requestPropertyType.equals(RequestPropertyType.APPLICATION_OCTET_STREAM))
            this.requestPropertyType = "application/octet-stream";
        else if (requestPropertyType.equals(RequestPropertyType.TEXT_PLAIN))
            this.requestPropertyType = "text/plain";
        else if (requestPropertyType.equals(RequestPropertyType.TEXT_CSS))
            this.requestPropertyType = "text/css";
        else if (requestPropertyType.equals(RequestPropertyType.TEXT_HTML))
            this.requestPropertyType = "text/html";
        else if (requestPropertyType.equals(RequestPropertyType.APPLICATION_JAVASCRIPT))
            this.requestPropertyType = "application/javascript";
        return this;
    }

    public WebServiceSendData setHeader(String key, String value) {
        this.requestPropertyKey.add(key);
        this.requestPropertyValue.add(value);
        return this;
    }

    public WebServiceSendData setRequestProperty(String key, String value) {
        this.requestPropertyKey.add(key);
        this.requestPropertyValue.add(value);
        return this;
    }

    public WebServiceSendData setCharacter(String character) {
        this.character = character;
        return this;
    }

    public WebServiceSendData setUrl(String uri) {
        this.uri = uri;
        return this;
    }


    public WebServiceSendData setData(String data) {
        this.data = data;
        return this;
    }

    public WebServiceSendData setReturnType(ReturnType returnType) {
        this.returnType = returnType;
        return this;
    }

    public Object connect() throws IOException {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //Connect
        urlConnection = (HttpURLConnection) ((new URL(uri).openConnection()));
        urlConnection.setDoOutput(true);
        if (requestPropertyType != null)
            urlConnection.setRequestProperty("Content-Type", requestPropertyType);
        for (int i = 0; i < requestPropertyKey.size(); i++) {
            urlConnection.setRequestProperty(requestPropertyKey.get(i), requestPropertyValue.get(i));
        }
        urlConnection.setRequestMethod(requestMethod);
        urlConnection.connect();

        //Write
        OutputStream outputStream = urlConnection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, this.character));
        writer.write(data);
        writer.close();
        outputStream.close();

        //Read
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), this.character));
        String line = null;
        StringBuilder sb = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
        }

        bufferedReader.close();

        if (returnType.equals(ReturnType.STRING))
            return sb.toString();
        else if (returnType.equals(ReturnType.JSON)) {
            Gson gson = new Gson();
            Object gsonResponse = gson.fromJson(sb.toString(), Object.class);
            return gsonResponse;
        }
        return null;
    }

}
