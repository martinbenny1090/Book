package com.Book.service;

import java.util.List;

import com.Book.entity.Book;
import com.Book.exception.Invalidbookdetails;
import com.Book.exception.InvaliedTitle;
import com.Book.exception.NoRecordFound;
import com.Book.exception.TitleAllreadyPresent;
import com.Book.model.Request.BookRequest;
import com.Book.model.Response.BookResponse;

public interface BookService {
	
	public BookResponse addbook(BookRequest book) throws Invalidbookdetails, TitleAllreadyPresent;
	public BookResponse FindBytitle(String title) throws InvaliedTitle;
	public List<Book> FindByAuthorSorted(String author) throws NoRecordFound;
	public String deletebook(Integer id) throws NoRecordFound;
	public List<Book> sortbyprice() throws NoRecordFound;


}
