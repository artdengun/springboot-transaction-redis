package com.deni.transaction.springcrudtransaction.services.Impl;

import com.deni.transaction.springcrudtransaction.dtos.MBooksRequestDto;
import com.deni.transaction.springcrudtransaction.dtos.MBooksResponseDto;
import com.deni.transaction.springcrudtransaction.exception.BooksFindByIdNotFoundException;
import com.deni.transaction.springcrudtransaction.exception.BooksFindByNameNotFoundException;
import com.deni.transaction.springcrudtransaction.exception.BooksFindNotFoundsException;
import com.deni.transaction.springcrudtransaction.models.MBooksModel;
import com.deni.transaction.springcrudtransaction.repositories.MBooksRepository;
import com.deni.transaction.springcrudtransaction.services.MBooksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@CacheConfig(cacheNames = "booksCache")
public class MBooksServiceImpl implements MBooksService {

    @Autowired
    public MBooksRepository booksRepository;


    @CacheEvict(cacheNames = "booksCache", allEntries = true)
    @Override
    public MBooksResponseDto addSomeBooks(MBooksRequestDto requestDto)  {
            // 1. tranfer data dari request ke model
            MBooksModel booksModel = new MBooksModel();
            booksModel.setNameBooks(requestDto.getNameBooks());
            booksModel.setTitleBooks(requestDto.getTitleBooks());
            booksModel.setContentBooks(requestDto.getContentBooks());
            booksModel.setPayBooks(requestDto.getPayBooks());
            booksModel.setTotalPages(requestDto.getTotalPages());
            // 2. save ke database
            MBooksModel saveKeDatabase = booksRepository.save(booksModel);
            // 3. transfer dari model ke response
            MBooksResponseDto responseDto = new MBooksResponseDto();
            responseDto.setId(saveKeDatabase.getId());
            responseDto.setTitleBooks(saveKeDatabase.getTitleBooks());
            responseDto.setNameBooks(saveKeDatabase.getNameBooks());
            responseDto.setTitleBooks(saveKeDatabase.getTitleBooks());
            responseDto.setPayBooks(saveKeDatabase.getPayBooks());
            responseDto.setContentBooks(saveKeDatabase.getContentBooks());
            responseDto.setTotalPages(saveKeDatabase.getTotalPages());
            // 4. return datanya
        return responseDto;
    }

    @CacheEvict(cacheNames = "booksCache", key = "#id", allEntries = true)
    @Override
    public MBooksResponseDto updateSomeBooks(String id, MBooksRequestDto requestDto) throws Exception {

        // 1. get datanya berdasarkan id
        Optional<MBooksModel> update = booksRepository.findById(id);

        // 2. kita melakukan check apakah data yang kita punya ada
        if (update.isEmpty()) {
            throw  new BooksFindByIdNotFoundException("Id tidak ditemukan");
        }

        // 3. jika ditemukan set data lama ke data yang terbaru dan kemudian save datanya
            update.get().setNameBooks(requestDto.getNameBooks());
            update.get().setTitleBooks(requestDto.getTitleBooks());
            update.get().setPayBooks(requestDto.getPayBooks());
            update.get().setContentBooks(requestDto.getContentBooks());
            update.get().setTotalPages(requestDto.getTotalPages());


        MBooksModel saveKeDatabase = booksRepository.save(update.get());

        // 4. kita berikan nilai response
        MBooksResponseDto responseDto = new MBooksResponseDto();

        responseDto.setId(saveKeDatabase.getId());
        responseDto.setNameBooks(saveKeDatabase.getNameBooks());
        responseDto.setTitleBooks(saveKeDatabase.getTitleBooks());
        responseDto.setPayBooks(saveKeDatabase.getPayBooks());
        responseDto.setContentBooks(saveKeDatabase.getContentBooks());
        responseDto.setTotalPages(saveKeDatabase.getTotalPages());

        // 5. berikan return data
        return responseDto;
    }

    @Cacheable(cacheNames = "booksCache")
    @Override
    public List<MBooksResponseDto> tampilkanData() {


        // 1. kita melakuan cek datanya dahulu
            Iterable<MBooksModel> booksModels = booksRepository.findAll();
        // 2. Kita Tampung Responsya
            List<MBooksResponseDto> responseDtos = new ArrayList<>();
        // 3. kita feeding datanya/ kita tranfer data dari models ke dto
            booksModels.forEach(books -> {
                MBooksResponseDto responseDto = new MBooksResponseDto();
                responseDto.setId(books.getId());
                responseDto.setNameBooks(books.getNameBooks());
                responseDto.setTitleBooks(books.getTitleBooks());
                responseDto.setPayBooks(books.getPayBooks());
                responseDto.setContentBooks(books.getContentBooks());
                responseDto.setTotalPages(books.getTotalPages());

                responseDtos.add(responseDto);
            });
        // 4. kita berikan return
        return responseDtos;
    }

    @Caching(evict = { @CacheEvict(cacheNames = "books", key = "#id"),
            @CacheEvict(cacheNames = "booksCache", allEntries = true) })
    @Override
    public void deleteSomeBooks(String id) throws Exception {
        // 1. cari data by id
        Optional<MBooksModel> findBook= booksRepository.findById(id);
        // 2. kita melakukan cek apakah id yang kita maksud itu ada
        if (findBook.isEmpty()) {
            throw  new BooksFindNotFoundsException("Data yang anda cari tidak ditemukan");
        }
        // 3. jika ditemukan maka eksekusi datanya
        booksRepository.deleteById(id);
    }

    @Cacheable(cacheNames = "booksCache", key = "#id", unless = "#result == null")
    @Override
    public MBooksResponseDto findById(String id) throws Exception {

        // 1.  menari data by id
        Optional<MBooksModel> mencariDataByIdBooks = booksRepository.findById(id);
        // 2. cek apakah datanya ada
        if (mencariDataByIdBooks.isEmpty()) {
            throw new BooksFindByIdNotFoundException("Id yang kamu cari tidak ditemukan");
        }
        // 3. jika ada balikan balikan responses
        MBooksResponseDto responseDto = new MBooksResponseDto();
        responseDto.setId(mencariDataByIdBooks.get().getId());
        responseDto.setNameBooks(mencariDataByIdBooks.get().getNameBooks());
        responseDto.setTitleBooks(mencariDataByIdBooks.get().getTitleBooks());
        responseDto.setPayBooks(mencariDataByIdBooks.get().getPayBooks());
        responseDto.setContentBooks(mencariDataByIdBooks.get().getTitleBooks());
        responseDto.setTotalPages(mencariDataByIdBooks.get().getTotalPages());

        // 4. return data
        return responseDto;

    }

    @Cacheable(cacheNames = "booksCache", key = "#nameBooks", unless = "#result == null")
    @Override
    public MBooksResponseDto findByName(String nameBooks) throws Exception {

        Optional<MBooksModel> mencariDataByNama = booksRepository.findByName(nameBooks);

        if(mencariDataByNama.isEmpty()){
            throw new BooksFindByNameNotFoundException("nama tidak tidak ditemukan");
        }
        MBooksResponseDto responseDto = new MBooksResponseDto();
        responseDto.setId(mencariDataByNama.get().getId());
        responseDto.setNameBooks(mencariDataByNama.get().getNameBooks());
        responseDto.setTitleBooks(mencariDataByNama.get().getTitleBooks());
        responseDto.setPayBooks(mencariDataByNama.get().getPayBooks());
        responseDto.setContentBooks(mencariDataByNama.get().getContentBooks());
        responseDto.setTotalPages(mencariDataByNama.get().getTotalPages());

        return responseDto;

    }

}
