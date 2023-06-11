package com.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerHttpController.class)
public class TestController {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerRepository repository;

    @BeforeEach
    public void setUp() {
        Customer customer1 = new Customer(1, "Josh");
        Customer customer2 = new Customer(2, "Mark");
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        given(repository.findAll()).willReturn(customers);
        given(repository.findByName("Josh")).willReturn(customer1);
    }

    // Test /customers endpoint
    @Test
    public void testGetCustomers() throws Exception {
        mvc.perform(get("/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    // Test /customers/{name} endpoint
    @Test
    public void testGetCustomerByName() throws Exception {
        mvc.perform(get("/customers/Josh")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Josh")));
    }
}
