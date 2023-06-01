package com.library.main.service;

import com.library.main.vo.BookResponse;
import com.library.main.vo.SaveBookVO;

import java.util.List;

public interface BookService {
    void saveBooks(List<SaveBookVO> books);
    BookResponse getBookById(Long bookId);
    void addBook(SaveBookVO bookVO);
    List<BookResponse> listAllBooks();
}
