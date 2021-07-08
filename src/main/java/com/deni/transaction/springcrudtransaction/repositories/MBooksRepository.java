package com.deni.transaction.springcrudtransaction.repositories;

import com.deni.transaction.springcrudtransaction.models.MBooksModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MBooksRepository extends JpaRepository<MBooksModel, String> {


    // query contoh ajah untuk mencari data by id
    @Query(value = "SELECT * FROM books d WHERE d.id= :id ", nativeQuery = true)
    Optional<MBooksModel> findById(@Param("id") String id);

    // query contoh ajah untuk mencari data Nama
    @Query(value = "SELECT * FROM books d WHERE d.name_books= :name_books", nativeQuery = true)
    Optional<MBooksModel> findByName(@Param("name_books") String nameBooks);
}
