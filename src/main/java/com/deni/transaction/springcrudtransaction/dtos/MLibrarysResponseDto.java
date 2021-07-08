package com.deni.transaction.springcrudtransaction.dtos;

import lombok.Data;

@Data
public class MLibrarysResponseDto {

    private String id;
    private String name;
    private String address;
    private Integer totalBooks;
    private MBooksResponseDto books;
}
