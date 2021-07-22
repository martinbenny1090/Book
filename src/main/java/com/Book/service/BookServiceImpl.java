package com.Book.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Book.entity.Book;
import com.Book.exception.Invalidbookdetails;
import com.Book.exception.InvaliedTitle;
import com.Book.exception.NoRecordFound;
import com.Book.exception.TitleAllreadyPresent;
import com.Book.model.Request.BookRequest;
import com.Book.model.Response.BookResponse;
import com.Book.repository.BookRepository;


@Service("impl")
public class BookServiceImpl implements BookService{
	
	@Autowired
	BookRepository bookrepo;
	
	@Autowired
	ModelMapper mapper;
	
	@Override
	public BookResponse addbook(BookRequest book) throws Invalidbookdetails, TitleAllreadyPresent{
		if(bookrepo.findByTitle(book.getTitle()) != null) {
			throw new TitleAllreadyPresent("Title Allready present in Data Base");
		}
		if( book.getTitle() == null) {
			throw new Invalidbookdetails(" Book Details are not good");
		}
		else {
			Book book1=mapper.map(book, Book.class);
			bookrepo.save(book1);
			
			BookResponse bookres=mapper.map(book1, BookResponse.class);
			return bookres;
		}
		
	}

	@Override
	public BookResponse FindBytitle(String title) throws InvaliedTitle {
		
		if(title.isBlank()) {
			throw new InvaliedTitle("Invalied title");
		}
		Book b1 =bookrepo.findByTitle(title);
		if (b1 == null) {
			throw new InvaliedTitle("InValied title");
		}
		BookResponse b2=mapper.map(b1, BookResponse.class);
		return b2;
	}





	@Override
	public List<Book> FindByAuthorSorted(String author) throws NoRecordFound {
		List<Book>list=bookrepo.findbyAuthorSorted(author);
		if(list.size() == 0) {
			throw new NoRecordFound("No record founded");
		}
		else {
			return list;
		}
				
	}

	@Override
	public String deletebook(Integer id) throws NoRecordFound {
		Book bookfind = bookrepo.findById(id).get();
		if(bookfind == null) {
			throw new NoRecordFound("No record founded");
		}
		else {
			bookrepo.delete(bookfind);
			return "The " + bookfind.getTitle()+ " is delete";
		}
		
	
	}

	@Override
	public List<Book> sortbyprice() throws NoRecordFound {
		List<Book> list = bookrepo.findAll();
//		if(list.size() == 0)
//		{
//			throw new NoRecordFound("no data present in DB");
//		}
		List<Book> listfilter = list.stream().filter(b -> b.getPrice() <= 500)
				.sorted(Comparator.comparingInt(Book::getPrice))
				.collect(Collectors.toList());
		
		if(listfilter.size() == 0)
		{
			throw new NoRecordFound("No data present in DataBase");
		}
		return listfilter;
	}
	
	
	
	
	
	
	

}
