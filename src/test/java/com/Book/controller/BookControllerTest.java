package com.Book.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.Book.entity.Book;
import com.Book.exception.Invalidbookdetails;
import com.Book.exception.InvaliedTitle;
import com.Book.exception.NoRecordFound;
import com.Book.model.Request.BookRequest;
import com.Book.model.Response.BookResponse;
import com.Book.service.BookService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;


@SpringBootTest(classes= {BookController.class})
@AutoConfigureMockMvc
@EnableWebMvc
public class BookControllerTest {
	
	@InjectMocks
	private BookController bookcontroller;
	
	@Autowired
	private MockMvc mock;
	
	@MockBean
	@Qualifier("impl")
	private BookService bookservice;
	
	static BookResponse bookresponse;
	static BookRequest bookreuest;
	static Book book;
	ObjectMapper objectMapper = new ObjectMapper();
	@BeforeAll
	static void setUp() {
		//for the response 
		bookresponse = new 		BookResponse();
		bookresponse.setId(1);
		bookresponse.setTitle("my life");
		bookresponse.setAuthor("manu");
		bookresponse.setPrice(400);
		
		//for the request 
		bookreuest = new BookRequest();
		bookreuest.setId(1);
		bookreuest.setTitle("my life");
		bookreuest.setAuthor("manu");
		bookreuest.setPrice(400);
		
		/// for book entity
		book = new Book();
		book.setId(1);
		book.setTitle("my life");
		book.setAuthor("manu");
		book.setPrice(450);
	}
	
	@Test
	public void gethai() throws Exception{
		MvcResult result = mock.perform(MockMvcRequestBuilders.get("/api/book")).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		assertEquals("Hai from book", content);
		
	}
	
	@Test
	@Disabled
	public void getbookbytitletest5() throws Exception {
		// Setup our mocked service
		String title ="my life";
		when(bookservice.FindBytitle(title)).thenReturn(bookresponse);
		this.mock.perform(MockMvcRequestBuilders.get("/api/book/{title}", title)).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
		.andExpect(MockMvcResultMatchers.jsonPath(".title").value("my life"));
   
        	}
	
	@Test
	public void getbookbytitletest() throws Exception {
		//mock the data form the service class
		String title ="my life";
		when(bookservice.FindBytitle(title)).thenReturn(bookresponse);
		//create a mock http reuest to verify the response
		MvcResult result =mock.perform(MockMvcRequestBuilders.get("/api/book/{title}", title))
		.andExpect(status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString(); 
		BookResponse response = objectMapper.readValue(content, BookResponse.class);
		
		assertEquals("my life", response.getTitle());
		assertEquals( bookresponse.getId() , response.getId());
	}


	
	@Test
	public void addbooktest() throws Exception {
		when(bookservice.addbook(bookreuest)).thenReturn(bookresponse);
		mock.perform(MockMvcRequestBuilders.post("/api/book")
				.content(new Gson().toJson(bookreuest)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isCreated());
		
	
	}
	
	@Test
	public void getbyauthortest() throws Exception {
		List<Book> book = new ArrayList<>();
		when(bookservice.FindByAuthorSorted(toString())).thenReturn(book);
		mock.perform(MockMvcRequestBuilders.get("/api/book/author/manu").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(book))).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void deletebooktest() throws Exception {
		when(bookservice.deletebook(bookresponse.getId())).thenReturn("");
		mock.perform(MockMvcRequestBuilders.delete("/api/book/delete/1")).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	
	@Test
	public void FilterBooksucess() throws Exception {
		List<Book> booklist = new ArrayList<>();
		booklist.add(book);
		when(bookservice.sortbyprice()).thenReturn(booklist);
		MvcResult result= mock.perform(MockMvcRequestBuilders.get("/api/book/Stream/").contentType(MediaType.APPLICATION_JSON)
				.content(new Gson().toJson(booklist))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn(); 
		// to convert the response to a list of book
		List<Book> actual = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<Book>>() {});
		List<Book> reult =actual.stream().filter(a -> a.getId() == 1).collect(Collectors.toList());
		assertEquals("my life", reult.get(0).getTitle());

	}
	
	

}
