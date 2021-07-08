package com.deni.transaction.springcrudtransaction.exception;

public class LibraryFindByNameNotFoundException extends Exception {
    public LibraryFindByNameNotFoundException() {
        super();
    }

    public LibraryFindByNameNotFoundException(String message) {
        super(message);
    }
}
