package com.library.main.controller;

import com.library.main.service.BookService;
import com.library.main.vo.BookResponse;
import com.library.main.vo.SaveBookVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/book/")
public class BookController {

    private final BookService bookService;

    @PostMapping("save-all-books")
    public ResponseEntity<String> saveAllBooks(@RequestBody List<SaveBookVO> books) {
        bookService.saveBooks(books);
        return ResponseEntity.ok().body("SAVED_SUCCESSFULLY");
    }

    @PostMapping("add-book")
    public ResponseEntity<String> addBook(@RequestBody SaveBookVO bookVO) {
        bookService.addBook(bookVO);
        return ResponseEntity.accepted().body("BOOK_SAVED");
    }

    @GetMapping("get-books")
    public ResponseEntity<List<BookResponse>> getBooks(){
        return ResponseEntity.ok().body(bookService.listAllBooks());
    }

    @GetMapping("get-book")
    public ResponseEntity<BookResponse> getBookById(@RequestParam Long id) {
        return ResponseEntity.ok().body(bookService.getBookById(id));
    }


}
