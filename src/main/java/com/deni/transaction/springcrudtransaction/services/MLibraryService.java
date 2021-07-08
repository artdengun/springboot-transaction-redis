package com.deni.transaction.springcrudtransaction.services;

import com.deni.transaction.springcrudtransaction.dtos.MLibrarysRequestDto;
import com.deni.transaction.springcrudtransaction.dtos.MLibrarysResponseDto;

import java.util.List;

public interface MLibraryService {
    // menambahkan data
    MLibrarysResponseDto add(MLibrarysRequestDto requestDto) throws Exception;
    // update data
    MLibrarysResponseDto update(String id, MLibrarysRequestDto requestDto) throws Exception;
    // tampilkan data
    List<MLibrarysResponseDto> read();
    // delete data by id
    void delete(String id) throws Exception;
    // mencari data by id
    MLibrarysResponseDto findById(String id) throws Exception;
    // mencari data by nama
    MLibrarysResponseDto findByName(String name) throws Exception;
}
