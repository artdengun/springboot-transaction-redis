package com.deni.transaction.springcrudtransaction.services.Impl;

import com.deni.transaction.springcrudtransaction.dtos.MBooksResponseDto;
import com.deni.transaction.springcrudtransaction.dtos.MLibrarysRequestDto;
import com.deni.transaction.springcrudtransaction.dtos.MLibrarysResponseDto;
import com.deni.transaction.springcrudtransaction.exception.LibraryFindByIdNotFoundException;
import com.deni.transaction.springcrudtransaction.models.MBooksModel;
import com.deni.transaction.springcrudtransaction.models.MLibrarysModel;
import com.deni.transaction.springcrudtransaction.repositories.MBooksRepository;
import com.deni.transaction.springcrudtransaction.repositories.MLibrarysRepository;
import com.deni.transaction.springcrudtransaction.services.MLibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = "libraryCache")
public class MLibrarysServiceImpl implements MLibraryService {

    private MBooksRepository booksRepository;
    private MLibrarysRepository librarysRepository;

    @Autowired
    public MLibrarysServiceImpl(MBooksRepository booksRepository, MLibrarysRepository librarysRepository) {
        this.booksRepository = booksRepository;
        this.librarysRepository = librarysRepository;
    }

    private MLibrarysModel buildLibraryModelFromRequest(MLibrarysRequestDto requestDto, MBooksModel books){

        MLibrarysModel library = new MLibrarysModel();

        library.setName(requestDto.getName());
        library.setAddress(requestDto.getAddress());
        library.setTotalBooks(requestDto.getTotalBooks());
        library.setBooks(books);


        return library;

    }

    private MLibrarysResponseDto buildLibraryResponseFromModel(MLibrarysModel perpustakaan, MBooksResponseDto responseDto){

        MLibrarysResponseDto librarysResponseDto = new MLibrarysResponseDto();


        librarysResponseDto.setId(perpustakaan.getId());
        librarysResponseDto.setName(perpustakaan.getName());
        librarysResponseDto.setAddress(perpustakaan.getAddress());
        librarysResponseDto.setTotalBooks(perpustakaan.getTotalBooks());
        librarysResponseDto.setBooks(responseDto);

        return librarysResponseDto;
    }

    private  MBooksResponseDto buildBookResponseFromModel(MBooksModel booksModel){

        MBooksResponseDto responseDto = new MBooksResponseDto();

        responseDto.setId(booksModel.getId());
        responseDto.setNameBooks(booksModel.getNameBooks());
        responseDto.setTitleBooks(booksModel.getTitleBooks());
        responseDto.setPayBooks(booksModel.getPayBooks());
        responseDto.setTotalPages(booksModel.getTotalPages());
        responseDto.setContentBooks(booksModel.getContentBooks());

        return responseDto;
    }


    @CacheEvict(cacheNames = "libraryCache", allEntries = true)
    @Override
    public MLibrarysResponseDto add(MLibrarysRequestDto library) throws Exception {
        //1.  kita cari by id
        Optional<MBooksModel> findBooksModels = booksRepository.findById(library.getBooksId());
        //2. kita melakukan cek exception jika id ditemukan
        if(findBooksModels.isEmpty()){
            throw  new LibraryFindByIdNotFoundException("id not found ");
        }
        // 3. jika id ditemukan maka ambil dinya
        MBooksModel booksModel = findBooksModels.get();

        // 4. definisikan employee
        MLibrarysModel librarysModel = buildLibraryModelFromRequest(library, booksModel);
        MLibrarysModel libraryResponse = librarysRepository.save(librarysModel);
        MBooksResponseDto booksResponseDto= buildBookResponseFromModel(booksModel);

        MLibrarysResponseDto responseDtos = buildLibraryResponseFromModel(libraryResponse, booksResponseDto);
        return responseDtos;
    }

    @CacheEvict(cacheNames = "libraryCache", key = "#id", allEntries = true)
    @Override
    public MLibrarysResponseDto update(String id, MLibrarysRequestDto requestDto) throws Exception {
        // 1. mencari data by id
        Optional<MBooksModel> findBooks = booksRepository.findById(requestDto.getBooksId());
        if(findBooks.isEmpty()){
            throw new LibraryFindByIdNotFoundException("id not founds");
        }

        MBooksModel booksModel = findBooks.get();

        Optional<MLibrarysModel> findLibrary = librarysRepository.findById(id);
        if(findLibrary.isEmpty()){
            throw new LibraryFindByIdNotFoundException("id tidak ditemukan");
        }

        findLibrary.get().setName(requestDto.getName());
        findLibrary.get().setAddress(requestDto.getAddress());
        findLibrary.get().setTotalBooks(requestDto.getTotalBooks());
        findLibrary.get().setBooks(booksModel);

        MLibrarysModel saveLibrary = librarysRepository.save(findLibrary.get());
        MBooksResponseDto booksResponseDto = buildBookResponseFromModel(booksModel);
        MLibrarysResponseDto responseDto = buildLibraryResponseFromModel(saveLibrary, booksResponseDto);

        return responseDto;
    }

    @Cacheable(cacheNames = "libraryCache")
    @Override
    public List<MLibrarysResponseDto> read() {

        Iterable<MLibrarysModel> librarys = librarysRepository.findAll();
        List<MLibrarysResponseDto> libraryResponse = new ArrayList<>();

        librarys.forEach(library -> {
            MBooksResponseDto booksResponseDto = buildBookResponseFromModel(library.getBooks());
            MLibrarysResponseDto librarysResponseDto = buildLibraryResponseFromModel(library, booksResponseDto);

            libraryResponse.add(librarysResponseDto);
        });

        return libraryResponse;
    }

    @Caching(evict = { @CacheEvict(cacheNames = "library", key = "#id"),
            @CacheEvict(cacheNames = "libraryCache", allEntries = true) })
    @Override
    public void delete(String id) throws Exception {
        Optional<MLibrarysModel> findBook = librarysRepository.findById(id);
        if(findBook.isEmpty()){
            throw  new LibraryFindByIdNotFoundException("id not found");
        }

        librarysRepository.deleteById(id);
    }

    @Cacheable(cacheNames = "libraryCache", key = "#id", unless = "#result == null")
    @Override
    public MLibrarysResponseDto findById(String id) throws Exception {
        Optional<MLibrarysModel> findBook = librarysRepository.findById(id);
        if(findBook.isEmpty()){
            throw  new LibraryFindByIdNotFoundException("data tidak ditemukan");
        }
        MBooksResponseDto responseDto = buildBookResponseFromModel(findBook.get().getBooks());
        MLibrarysResponseDto librarysResponseDto = buildLibraryResponseFromModel(findBook.get(), responseDto);

        return librarysResponseDto;
    }

    @Cacheable(cacheNames = "libraryCache", key = "#name", unless = "#result == null")
    @Override
    public MLibrarysResponseDto findByName(String name) throws Exception {

        Optional<MLibrarysModel> findBook = librarysRepository.findByName(name);
        if(findBook.isEmpty()){
            throw  new LibraryFindByIdNotFoundException("data tidak ditemukan");
        }
        MBooksResponseDto responseDto = buildBookResponseFromModel(findBook.get().getBooks());
        MLibrarysResponseDto librarysResponseDto = buildLibraryResponseFromModel(findBook.get(), responseDto);

        return librarysResponseDto;
    }
}
