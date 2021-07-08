package com.deni.transaction.springcrudtransaction.dtos;

import lombok.Data;

@Data
public class MLibrarysRequestDto {

    private String name;
    private String address;
    private Integer totalBooks;
    private String booksId;
}
