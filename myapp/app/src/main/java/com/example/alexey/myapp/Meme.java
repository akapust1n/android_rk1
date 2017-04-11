package com.example.alexey.myapp;

public class Meme {
    private String topText;
    private String botText;
    private String category;
    public Meme(String _topText,String _botText, String _category) {
        topText = _topText;
        botText = _botText;
        category = _category;
    }

    public String getTopText() {
        return this.topText;
    }

    public String getBotText() {
        return this.botText;
    }

    public String getCategory() {
        return this.category;
    }
}
