package com.deni.transaction.springcrudtransaction.exception;

public class BooksFindNotFoundsException extends Exception {
    public BooksFindNotFoundsException() {
        super();
    }

    public BooksFindNotFoundsException(String message) {
        super(message);
    }
}
