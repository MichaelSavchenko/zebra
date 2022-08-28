package com.mihadev.zebra.les.twitter;

public class Quote {
    private Book book;
    private String text;

    Quote(Book book, String text) {
        this.book = book;
        this.text = text;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
