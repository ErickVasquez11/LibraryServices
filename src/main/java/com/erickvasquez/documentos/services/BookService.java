package com.erickvasquez.documentos.services;

import java.util.List;

import com.erickvasquez.documentos.models.entities.Book;

public interface BookService {
	// Save, delete, getOneById, getAll
		void save(Book book);
		void delete(String isbn);
		Book findOneById(String isbn);
		List<Book> findAll();
}
