package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.demo.model.Authorities;
import com.demo.model.Product;
import com.demo.model.Users;
import com.demo.repository.AuthoritiesRepository;
import com.demo.repository.ProductRepository;
import com.demo.repository.UserRepository;
import com.hazelcast.spi.impl.eventservice.impl.Registration;

@RestController
public class ProductController {

	@Autowired
	ProductRepository repository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthoritiesRepository authoritiesRepository;
	
//	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);
	
	@RequestMapping(value="/adduser", method=RequestMethod.POST)
	public ModelAndView addUser(Users user) {
		userRepository.save(user);
		Authorities authority = new Authorities();
		authority.setAuthority("ROLE_USER");
		authority.setUsername(user.getUsername());
		authoritiesRepository.save(authority);
		return new ModelAndView("redirect:http://localhost:5000/login");
	}
	
	@RequestMapping(value="/registration", method=RequestMethod.GET)
	public ModelAndView registration() {
		return new ModelAndView("registration");
	}
	
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	@RequestMapping(value = "/loginerror", method = RequestMethod.GET)
	public String error(){
		return "Login Failed!!";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView welcome() {
		return new ModelAndView("welcome");
	}
	
	@RequestMapping(value = "/products/get/all", method = RequestMethod.GET)
	public List<Product> getProducts() {
		return repository.findAll();
	}

//	@Cacheable("product-cache")
	@Transactional(readOnly = true)
	@RequestMapping(value = "/products/get/{id}", method = RequestMethod.GET)
	public Product getProduct(@PathVariable("id") int id) {
		System.out.println("Finding product by ID:"+id);
		return repository.findById(id).get();
	}

	@RequestMapping(value = "/products/modify/add", method = RequestMethod.POST)
	public Product createProduct(@RequestBody Product product) {
		return repository.save(product);
	}

//	@CachePut(value = "product-cache", key = "#id")
	@RequestMapping(value = "/products/modify/add/{id}", method = RequestMethod.PUT)
	public Product updateProduct(@RequestBody Product product, @PathVariable int id) {
		return repository.save(product);
	}

//	@CacheEvict("product-cache")
	@RequestMapping(value = "/products/modify/delete/{id}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable("id") int id) {
		repository.deleteById(id);
	}

}
