package com.deni.transaction.springcrudtransaction.controllers;


import com.deni.transaction.springcrudtransaction.dtos.MBooksRequestDto;
import com.deni.transaction.springcrudtransaction.dtos.MBooksResponseDto;
import com.deni.transaction.springcrudtransaction.services.MBooksService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
@AllArgsConstructor
public class MBooksController {

    @Autowired
    MBooksService booksService;

    @PostMapping
    public MBooksResponseDto createBook(@RequestBody MBooksRequestDto requestDto) throws Exception{
        return  booksService.addSomeBooks(requestDto);
    }

    @PutMapping(value = "{id}")
    public MBooksResponseDto updateBook(@PathVariable(value = "id") String id, @RequestBody MBooksRequestDto requestDto) throws Exception{
        return booksService.updateSomeBooks(id, requestDto);

    }

    @DeleteMapping(value = "{id}")
    public String deleteBook( @PathVariable(value = "id") String id) throws Exception{
        booksService.deleteSomeBooks(id);
        return "delete buku berhasil";
    }
    @GetMapping
    public List<MBooksResponseDto> lihatBooks(){
        return booksService.tampilkanData();
    }

    @GetMapping(value = "/find-by-id")
    public MBooksResponseDto findById(@RequestParam(value = "id") String id) throws Exception{
        return  booksService.findById(id);
    }

    @GetMapping(value = "/find-by-name")
    public MBooksResponseDto findByName(@RequestParam(value = "name") String name) throws Exception{
        return booksService.findByName(name);
    }
}
