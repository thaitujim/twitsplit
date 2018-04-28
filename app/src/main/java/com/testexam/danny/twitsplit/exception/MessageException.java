package com.testexam.danny.twitsplit.exception;

public class MessageException extends Exception {

    private String message;

    public MessageException(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
