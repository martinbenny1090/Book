package com.Book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Book.entity.Book;
import com.Book.model.Request.BookRequest;

@Repository
public interface BookRepository  extends JpaRepository<Book, Integer>{
	
	public Book findByTitle(String title);
	
	@Query("from Book where author=?1 order by title")
	List<Book> findbyAuthorSorted(String author);


}
