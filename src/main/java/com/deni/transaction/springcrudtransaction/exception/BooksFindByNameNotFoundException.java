package com.deni.transaction.springcrudtransaction.exception;

public class BooksFindByNameNotFoundException extends Exception {

    public BooksFindByNameNotFoundException() {
        super();
    }

    public BooksFindByNameNotFoundException(String message) {
        super(message);
    }
}
