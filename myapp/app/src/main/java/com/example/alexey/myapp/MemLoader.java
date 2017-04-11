package com.example.alexey.myapp;

import android.util.Base64;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;


public class MemLoader {
    private final OkHttpClient httpClient = new OkHttpClient();

    public MemLoader() {
    }

    public String loadMem(String category, String topText, String botText) throws IOException {


        Request request = (new Builder()).url("https://ronreiter-meme-generator.p.mashape.com/meme?bottom=" + botText + "&font=Impact&font_size=50&top="+topText+"&meme=" + category).addHeader("X-Mashape-Key", "O49meiCj6xmshe4yeLhbsDDHjd1bp12dS56jsnl3X89gnnbBQE").build();
        Response response = this.httpClient.newCall(request).execute();

        byte[] var;
        String result;

        try {
            if (!response.isSuccessful()) {
                throw new IOException("Wrong status: " + response.code() + "; body: " + response.body().string());
            }

            var = response.body().bytes();
            result = Base64.encodeToString(var, Base64.DEFAULT);
            System.out.println("BODY");
            System.out.println(result.length());


        } finally {
            response.body().close();
        }

        return result;
    }
}