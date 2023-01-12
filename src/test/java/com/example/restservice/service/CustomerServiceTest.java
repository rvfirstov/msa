package com.example.restservice.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.restservice.model.Customer;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@WebMvcTest
class CustomerServiceTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CustomerService customerService;

  @MockBean
  private OrderService orderService;

  @Test
  void save() throws Exception {
    given(customerService.saveOrUpdate(any(Customer.class)))
            .willAnswer((invocation)-> invocation.getArgument(0));

    ResultActions response = mockMvc.perform(post("/v1/customers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createCustomer())));
    response.andExpect(status().isCreated());
  }

  @Test
  void getById() throws Exception {
    Customer customer = createCustomer();
    given(customerService.getById(customer.getId())).willReturn(Optional.of(createCustomer()));

    ResultActions response = mockMvc.perform(get("/v1/customers/{id}", customer.getId()));
    response.andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.money", is(customer.getMoney())))
            .andExpect(jsonPath("$.name", is(customer.getName())));
  }

  @Test
  void update() throws Exception{
    Customer customer = createCustomer();
    given(customerService.getById(customer.getId())).willReturn(Optional.of(createCustomer()));
    given(customerService.saveOrUpdate(any(Customer.class)))
            .willAnswer((invocation)-> invocation.getArgument(0));
    ResultActions response = mockMvc.perform(put("/v1/customers/{id}", customer.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(createCustomer())));
    response.andExpect(status().isOk());
  }

  @Test
  void deleteCustomer() throws Exception {
    Customer customer = createCustomer();
    willDoNothing().given(customerService).delete(customer.getId());

    ResultActions response = mockMvc.perform(delete("/v1/customers/{id}", customer.getId()));
    response.andExpect(status().isOk());
  }

  private Customer createCustomer(){
    Customer customer = new Customer();
    customer.setId(1L);
    customer.setMoney(10);
    customer.setName("Ivan");
    return customer;
  }
}
