package com.onurciner.webserviceconnect;

import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Onur.Ciner on 22.12.2016.
 */

public class WebServiceGetData {

    public HttpURLConnection urlConnection;

    private ReturnType returnType = ReturnType.STRING;
    private String requestMethod = "GET";
    private Integer readTimeout = 20000;
    private Integer connectTimeout = 30000;
    private String urlString = null;

    public WebServiceGetData setUrl(String url) {
        this.urlString = url;
        return this;
    }

    public WebServiceGetData setRequestMethod(MethodType methodType) {
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

    public WebServiceGetData setReadTimeout(Integer milliseconds) {
        this.readTimeout = milliseconds;
        return this;
    }

    public WebServiceGetData setConnectTimeout(Integer milliseconds) {
        this.connectTimeout = milliseconds;
        return this;
    }

    public WebServiceGetData setReturnType(ReturnType returnType) {
        this.returnType = returnType;
        return this;
    }

    public Object connect() throws IOException {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        URL url = new URL(this.urlString);

        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod(requestMethod);
        urlConnection.setReadTimeout(readTimeout);
        urlConnection.setConnectTimeout(connectTimeout);
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String jsonString = new String();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        jsonString = sb.toString();

        if (returnType.equals(ReturnType.STRING)) {
            return jsonString;
        } else if (returnType.equals(ReturnType.JSON)) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Object>>() {
            }.getType();
            ArrayList<Object> gsonResponse = gson.fromJson(jsonString, type);
            return gsonResponse;
        }
        return null;
    }

}
