package com.example.service;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
        given(repository.findById(1)).willReturn(Optional.of(customer1));
    }

    // Test /customers endpoint
    @Test
    public void testGetCustomers() throws Exception {
        mvc.perform(get("/customers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    // This isn't in our code, but note that it's built into the repository interface
    // Test /customers/{id} endpoint
    @Test
    public void testGetCustomerById() throws Exception {
        mvc.perform(get("/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
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
