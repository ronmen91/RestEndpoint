package com.ronmen.restendpoint.controllers;

import com.ronmen.restendpoint.RestEndpointApplication;
import com.ronmen.restendpoint.persistence.entities.Book;
import com.ronmen.restendpoint.persistence.entities.User;
import com.ronmen.restendpoint.persistence.repository.BookRepository;
import com.ronmen.restendpoint.persistence.repository.UserRepository;
import com.ronmen.restendpoint.services.impl.BookService;
import com.ronmen.restendpoint.services.impl.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestEndpointApplication.class)
@WebAppConfiguration
public class BookRestControllerTest {
    private MediaType mediaType = new MediaType(MediaTypes.HAL_JSON.getType(),
            MediaTypes.HAL_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String userName = "rnemeth";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private User user;

    private List<Book> books = new ArrayList<>();

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(mc -> mc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);
    }

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.bookRepository.deleteAllInBatch();
        this.userRepository.deleteAllInBatch();

        this.user = userRepository.save(new User(userName, "password"));
        this.books.add(bookRepository.save(new Book(user, "Description")));
        this.books.add(bookRepository.save(new Book(user, "Description")));
        this.books.add(bookRepository.save(new Book(user, "Description")));
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(post("/books/glaszlo")
            .content(json(new Book(null, null)))
            .contentType(mediaType))
            .andExpect(status().isNotFound()
        );
    }

    @Test
    public void readBooks() throws Exception {
        mockMvc.perform(get("/books/" + userName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$._embedded.bookList", hasSize(3)))
                .andExpect(jsonPath("$._embedded.bookList[0].id", is(this.books.get(0).getId().intValue())))
                .andExpect(jsonPath("$._embedded.bookList[0].description", is("Description")))
                .andExpect(jsonPath("$._embedded.bookList[1].id", is(this.books.get(1).getId().intValue())))
                .andExpect(jsonPath("$._embedded.bookList[1].description", is("Description")))
                .andExpect(jsonPath("$._embedded.bookList[2].id", is(this.books.get(2).getId().intValue())))
                .andExpect(jsonPath("$._embedded.bookList[2].description", is("Description")));
    }

    @Test
    public void add() throws Exception {
        String bookmarkJson = json(new Book(this.user,
                "Description"));

        this.mockMvc.perform(post("/books/" + userName)
                .contentType(mediaType)
                .content(bookmarkJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void readBook() throws Exception {
        mockMvc.perform(get("/books/" + userName + "/" + this.books.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(mediaType))
                .andExpect(jsonPath("$.id", is(this.books.get(0).getId().intValue())))
                .andExpect(jsonPath("$.description", is("Description")));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}