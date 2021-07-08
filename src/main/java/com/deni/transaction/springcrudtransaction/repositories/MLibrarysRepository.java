package com.deni.transaction.springcrudtransaction.repositories;

import com.deni.transaction.springcrudtransaction.models.MLibrarysModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MLibrarysRepository extends JpaRepository<MLibrarysModel, String> {

    // query contoh ajah untuk mencari data Id
    @Query(value = "SELECT * FROM library d WHERE d.id= :id", nativeQuery = true)
    Optional<MLibrarysModel> findById(@Param("id") String id);

    // query contoh ajah untuk mencari data Nama
    @Query(value = "SELECT * FROM library d where d.name= :name", nativeQuery = true)
    Optional<MLibrarysModel> findByName(@Param("name") String name);
}
