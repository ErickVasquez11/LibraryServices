package com.erickvasquez.documentos.controllers;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.erickvasquez.documentos.models.dtos.SaveBookDTO;
import com.erickvasquez.documentos.models.entities.Book;
import com.erickvasquez.documentos.services.BookService;
import com.erickvasquez.documentos.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/library")
public class LibraryController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private RequestErrorHandler errorHandler;
	
	  @RequestMapping(value = "/all", method = RequestMethod.GET)
	    public String getBookList(Model model) {
	    	model.addAttribute("books", bookService.findAll());
	    	return "book-list";
	    }
	    
	    @GetMapping("/")
	    public String getMainpage(Model model) {
	    	String time = Calendar.getInstance().getTime().toString();
	    	model.addAttribute("time", time);
	    	
	    	return "main";
	    }
	    
	    @PostMapping("/save")
	    public String saveBook(@ModelAttribute @Valid SaveBookDTO bookInfo,
	    		BindingResult validations, Model model) {
	    	
	    	if(validations.hasErrors()) {
	    		System.out.println("Hay errores");
	    		
	    		model.addAllAttributes(
	    				errorHandler.mapErrors(validations.getFieldErrors())
	    			);
	    		
	    		model.addAttribute("hasErrors", true);
	    		
	    		return "main";
	    	}
	    	
	    	System.out.println(bookInfo);
	    	
	    	Book newBook = new Book(bookInfo.getIsbn(), bookInfo.getTitle());
	    	bookService.save(newBook);
	    	
	    	return "redirect:/library/all";
	    }
}
