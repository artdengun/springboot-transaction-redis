package com.deni.transaction.springcrudtransaction.controllers;

import com.deni.transaction.springcrudtransaction.dtos.MLibrarysRequestDto;
import com.deni.transaction.springcrudtransaction.dtos.MLibrarysResponseDto;
import com.deni.transaction.springcrudtransaction.services.MLibraryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/library")
@AllArgsConstructor
public class MLibraryController {

    @Autowired private MLibraryService libraryService;

    @PostMapping
    public MLibrarysResponseDto add( @RequestBody  MLibrarysRequestDto requestDto) throws Exception{
        return  libraryService.add(requestDto);
    }

    @PutMapping(value = "{id}")
    public MLibrarysResponseDto update(@PathVariable(value = "id") String id,
                                              @RequestBody MLibrarysRequestDto requestDto) throws Exception{
        return  libraryService.update(id, requestDto);
    }


    @DeleteMapping(value = "{id}")
    public String delete( @PathVariable(value = "id") String id) throws Exception{
        libraryService.delete(id);
        return "delete buku berhasil";
    }

    @GetMapping
    public List<MLibrarysResponseDto> findAll(){

        return  libraryService.read();
    }

    @GetMapping(value = "/find-by-id")
    public MLibrarysResponseDto findById(@Param(value = "id") String id) throws Exception{
        return libraryService.findById(id);
    }

    @GetMapping(value = "/find-by-name")
    public MLibrarysResponseDto findByName(@Param(value = "name_books") String nameBooks) throws Exception{
        return libraryService.findByName(nameBooks);
    }

}
