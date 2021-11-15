package com.springboot.demo.httpmethods.service;

import com.springboot.demo.httpmethods.pojo.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookService extends CrudRepository<Book, Integer> {
}
