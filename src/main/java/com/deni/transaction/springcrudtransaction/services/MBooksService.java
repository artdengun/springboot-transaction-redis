package com.deni.transaction.springcrudtransaction.services;

import com.deni.transaction.springcrudtransaction.dtos.MBooksRequestDto;
import com.deni.transaction.springcrudtransaction.dtos.MBooksResponseDto;

import java.util.List;

public interface MBooksService {
    // menambahkan buku
    MBooksResponseDto addSomeBooks(MBooksRequestDto requestDto) throws Exception;
    // update buku diambil dari berapa idnya
    MBooksResponseDto updateSomeBooks(String id, MBooksRequestDto requestDto) throws Exception;
    // menampilkan semua data buku
    List<MBooksResponseDto> tampilkanData();
    // delete data berdasarkan id
    void deleteSomeBooks(String id) throws Exception;
    // mencari data by id
    MBooksResponseDto findById(String id) throws Exception;
    // mencari data by nama
    MBooksResponseDto findByName(String nameBooks) throws Exception;


}
