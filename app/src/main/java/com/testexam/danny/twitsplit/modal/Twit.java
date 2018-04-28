package com.testexam.danny.twitsplit.modal;

import java.io.Serializable;

public class Twit implements Serializable {
    private int id;
    private String message;
    private int total;

    public Twit(){

    }

    public Twit(int id, int total, String message){
        this.id = id;
        this.total = total;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
