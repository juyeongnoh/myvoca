package com.example.myvoca;

public class Card {
    private String word;
    private String definition;
    private String urlfrom;

    public Card(String word, String definition, String urlfrom) {
        this.word = word;
        this.definition = definition;
        this.urlfrom = urlfrom;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getUrlfrom() {
        return urlfrom;
    }

    public void setUrlfrom(String urlfrom) {
        this.urlfrom = urlfrom;
    }
}
