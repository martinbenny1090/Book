package com.Book.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.Book.BookApplication;
import com.Book.controller.BookController;
import com.Book.entity.Book;
import com.Book.exception.Invalidbookdetails;
import com.Book.exception.InvaliedTitle;
import com.Book.exception.NoRecordFound;
import com.Book.exception.TitleAllreadyPresent;
import com.Book.model.Request.BookRequest;
import com.Book.model.Response.BookResponse;
import com.Book.repository.BookRepository;






@RunWith(SpringRunner.class)
@SpringBootTest(classes= {BookServiceImpl.class, BookApplication.class})
public class BookServiceTest {
	@Mock
    BookRepository bookrepo;


    Book book;
    BookResponse bookres;
    BookRequest bookrequest;
    List<Book> booklist = new ArrayList<>();
    @Autowired
    private BookServiceImpl bookservice;


	

	@BeforeEach
	public void setUp() {

		book = new Book();
		book.setId(1);
		book.setTitle("my life");
		book.setAuthor("manu");
		book.setPrice(400);

		bookres = new BookResponse();
		bookres.setId(1);
		bookres.setTitle("my life");
		bookres.setAuthor("manu");
		bookres.setPrice(400);

		bookrequest = new BookRequest();
		bookrequest.setAuthor("manu");
		bookrequest.setId(1);
		bookrequest.setPrice(400);
		bookrequest.setTitle("my life");
		
		booklist.add(book);
	}
	

	@Test
	public void addbooksucesstest() throws Invalidbookdetails, TitleAllreadyPresent {
		when(bookrepo.save(book)).thenReturn(book);
		BookResponse bookr =bookservice.addbook(bookrequest);
		assertEquals(book.getId(), bookr.getId());
	}

	
	
	
	@Test
	public void FindBytitleSucess() throws InvaliedTitle {
		when(bookrepo.findByTitle("my life")).thenReturn(book);
		BookResponse bookre = bookservice.FindBytitle("my life");
		Assert.assertEquals(book.getAuthor(), bookre.getAuthor());
	}

	@Test
	public void DeleteSucess() throws NoRecordFound {
		when(bookrepo.save(book)).thenReturn(book);
		String except = "The " + book.getTitle()+ " is delete";
		String delreturn = bookservice.deletebook(book.getId());
		Assert.assertEquals(except, delreturn);
	}
	
	@Test
	public void sortbypriceSucess() throws NoRecordFound {
		when(bookrepo.findAll()).thenReturn(booklist);
		List<Book> list =bookservice.sortbyprice();
		assertNotNull(list);
	}
		
	@Test
	public void FindByAuthorSortedSucess() throws NoRecordFound, Invalidbookdetails, TitleAllreadyPresent {
		bookservice.addbook(bookrequest);
//		when(bookrepo.findbyAuthorSorted(toString())).thenReturn(booklist);
		List<Book> booklistres = bookservice.FindByAuthorSorted("manu");
		assertNotNull(booklistres);
	}
	
	@Test 
	public void addbookfalier() throws TitleAllreadyPresent, Invalidbookdetails{
		Exception exception = assertThrows(
				TitleAllreadyPresent.class, 
	            () -> bookservice.addbook(bookrequest));
	            
	        assertTrue(exception.getMessage().contains("Title Allready present in Data Base"));
	}
	
	@Test
	@Disabled
	public void Sortbypricefalier() throws NoRecordFound{
		Exception exception = assertThrows(
				NoRecordFound.class, 
	            () -> bookservice.sortbyprice());
	            
	        assertTrue(exception.getMessage().contains("No data present in DataBase"));
	}
	
	
	@Test 
	@Disabled
	public void deletebookalier()throws NoRecordFound{
		Exception exception = assertThrows(
				NoRecordFound.class, 
	            () -> bookservice.deletebook(2));
	            
	        assertTrue(exception.getMessage().contains("No record founded"));
	}
	
	
	

	
	
	
    
	
}
