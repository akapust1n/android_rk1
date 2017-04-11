package com.example.alexey.myapp;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;


public class MemLoader {
    private final OkHttpClient httpClient = new OkHttpClient();

    public MemLoader() {
    }

    public String loadMem(String kind) throws IOException {
        Request request = (new Builder()).url("https://ronreiter-meme-generator.p.mashape.com/meme?bottom=Bottom+text&font=Impact&font_size=50&top=Top+text&meme=" + kind).addHeader("X-Mashape-Key", "meiCj6xmshe4yeLhbsDDHjd1bp12dS56jsnl3X89gnnbBQE").build();
        Response response = this.httpClient.newCall(request).execute();

        String var4;
        try {
            if (!response.isSuccessful()) {
                throw new IOException("Wrong status: " + response.code() + "; body: " + response.body().string());
            }

            var4 = response.body().toString();
        } finally {
            response.body().close();
        }

        return var4;
    }
}