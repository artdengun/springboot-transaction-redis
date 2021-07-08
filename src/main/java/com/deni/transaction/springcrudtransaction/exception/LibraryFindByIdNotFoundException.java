package com.deni.transaction.springcrudtransaction.exception;

public class LibraryFindByIdNotFoundException extends Exception
{
    public LibraryFindByIdNotFoundException() {
        super();
    }

    public LibraryFindByIdNotFoundException(String message) {
        super(message);
    }
}
