package com.deni.transaction.springcrudtransaction.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MBooksResponseDto {

    private String id;
    private String nameBooks;
    private String titleBooks;
    private String contentBooks;
    private BigDecimal payBooks;
    private Integer totalPages;
}
