package com.deni.transaction.springcrudtransaction.exception;

public class BooksFindByIdNotFoundException extends Exception {

    public BooksFindByIdNotFoundException() {
        super();
    }

    public BooksFindByIdNotFoundException(String message) {
        super(message);
    }
}
