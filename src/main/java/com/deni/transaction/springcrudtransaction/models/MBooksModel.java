package com.deni.transaction.springcrudtransaction.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class MBooksModel {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    @Column(name = "id", unique = true, length = 64)
    private String id;

    @Column(name = "name_books")
    private String nameBooks;

    @Column(name = "title_books")
    private String titleBooks;

    @Column(name = "content_books")
    private String contentBooks;

    @Column(name = "pay_books", precision = 18, scale = 3)
    private BigDecimal payBooks;

    @Column(name = "total_pages")
    private Integer totalPages;
}
