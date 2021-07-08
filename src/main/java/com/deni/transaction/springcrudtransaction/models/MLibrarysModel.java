package com.deni.transaction.springcrudtransaction.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "library")
public class MLibrarysModel {


    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true, length = 64)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "total_books")
    private Integer totalBooks;

    @ManyToOne
    @JoinColumn(name = "books_id")
    private MBooksModel books;
}
