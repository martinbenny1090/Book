package com.Book.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.Book.entity.Book;
import com.Book.exception.Invalidbookdetails;
import com.Book.exception.InvaliedTitle;
import com.Book.exception.NoRecordFound;
import com.Book.exception.TitleAllreadyPresent;
import com.Book.model.Request.BookRequest;
import com.Book.model.Response.BookResponse;
import com.Book.service.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.jfr.ContentType;

//@Controller
@RestController
@Api
@RequestMapping("/api/book")
public class BookController {
	
	@Autowired
	@Qualifier("impl")
	BookService bookservice;
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> hai() {
		return new ResponseEntity<String>("Hai from book", HttpStatus.OK);
		
	}
	
//	@RequestMapping(consumes="application/json",method=RequestMethod.POST)
	@PostMapping()
	@ApiOperation(value = "Insert Book Data")
	//@ResponseBody @Valid
	public  ResponseEntity<?> newbook(@RequestBody  @Valid BookRequest book) throws Invalidbookdetails, TitleAllreadyPresent {
		return new ResponseEntity<>(bookservice.addbook(book), HttpStatus.CREATED);
	}
	
//		if(bookservice.addbook(book)) {
//			return new ResponseEntity<String>("book added", HttpStatus.OK);
//		}
//		return new ResponseEntity<String>("book not added", HttpStatus.BAD_GATEWAY);
		
//		try {
//			return new ResponseEntity<>(bookservice.addbook(book), HttpStatus.OK);
//		}catch(Exception e){
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
//		}
	
	
//	@RequestMapping(method=RequestMethod.GET, value="{title}")
	@GetMapping("/{title}")
	@ApiOperation(value = "Search book by title")
	public @ResponseBody ResponseEntity<?> getbytitle(@PathVariable String title) throws InvaliedTitle{
		return new ResponseEntity<BookResponse>(bookservice.FindBytitle(title), HttpStatus.OK);
//		try {
//			return new ResponseEntity<>(bookservice.FindBytitle(title), HttpStatus.OK);
			
//		}catch(Exception e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//		}
//		
	}
	
	@RequestMapping( method=RequestMethod.GET, value="author/{author}")
	@ApiOperation(value = "Search book by author / sorting")
	public @ResponseBody ResponseEntity<?> getbyauthorsort(@PathVariable String author) throws NoRecordFound{
		return new ResponseEntity<>(bookservice.FindByAuthorSorted(author), HttpStatus.OK);
//		try {
//			return new ResponseEntity<>(bookservice.FindByAuthorSorted(author), HttpStatus.OK);
			
//		}catch(Exception e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//		}
		
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/delete/{id}")
	public ResponseEntity<?> DeleteBook(@PathVariable Integer id) throws NoRecordFound{
		return new ResponseEntity<>(bookservice.deletebook(id), HttpStatus.OK);
		
	}
	
	@RequestMapping( method=RequestMethod.GET, value="Stream/")
	@ApiOperation("Filter book price by 500 and sort by price")
	public ResponseEntity<?> FilterBook() throws NoRecordFound{
		return new ResponseEntity<List<Book>>(bookservice.sortbyprice(), HttpStatus.OK);
		
	}
	
}
