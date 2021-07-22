package com.Book.model.Request;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class BookRequest {
	
//	@Id 
//	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotNull(message="give a price to book")
	private Integer price;
	@NotNull(message="give a title to book")
//	@Column(name="book_title", length=10)
	private String title;
	
//	@Column(name="book_author", nullable=true, unique = false)
	@NotNull(message="author for book is null")
	private String author;
	

	

	

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the price
	

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	


}
