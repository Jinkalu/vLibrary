package com.library.main.service.impl;

import com.library.main.entity.Books;
import com.library.main.exception.ErrorVO;
import com.library.main.exception.ValidationException;
import com.library.main.mappers.BookMapper;
import com.library.main.repo.BooksRepository;
import com.library.main.service.BookService;
import com.library.main.vo.BookResponse;
import com.library.main.vo.SaveBookVO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BooksRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public void saveBooks(List<SaveBookVO> books) {
      repository.saveAll(books.stream()
                .map(bookVO -> modelMapper.map(bookVO, Books.class))
                .collect(Collectors.toList()));
    }

    @Override
    public BookResponse getBookById(Long bookId) {
        Optional<Books> books = repository.findById(bookId);
        if (books.isPresent()) {
            return modelMapper.map(books.get(),BookResponse.class);
        }
        throw new ValidationException(List.of(new ErrorVO("NOT_FOUND",
                "BOOK_NOT_FOUND")));
    }

    @Override
    public void addBook(SaveBookVO bookVO) {
        repository.save(modelMapper.map(bookVO,Books.class));
    }

    @Override
    public List<BookResponse> listAllBooks() {
        return repository.findAll().stream()
                .map(book -> modelMapper.map(book,BookResponse.class))
                .collect(Collectors.toList());
    }
}