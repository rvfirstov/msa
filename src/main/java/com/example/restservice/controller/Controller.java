package com.example.restservice.controller;

import com.example.restservice.model.Customer;
import com.example.restservice.model.Order;
import com.example.restservice.service.CustomerService;
import com.example.restservice.service.OrderService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class Controller {

  private final CustomerService customerService;
  private final OrderService orderService;

  @GetMapping
  public ResponseEntity<List<Customer>> getAll() {
    List<Customer> list = customerService.getAll();
    if (list.isEmpty()) {
      return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(list, HttpStatus.OK);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Customer> getById(@PathVariable("id") Long id) {
    Optional<Customer> optional = customerService.getById(id);
    if (optional.isPresent()) {
      return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<Customer> create(@RequestBody Customer customer) {
    return new ResponseEntity<>(customerService.saveOrUpdate(customer), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Customer customerDto) {
    Optional<Customer> optional = customerService.getById(id);
    if (optional.isPresent()) {
      Customer customer = optional.get();
      customer.setName(customerDto.getName());
      customer.setMoney(customerDto.getMoney());
      return new ResponseEntity<>(customerService.saveOrUpdate(customer), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    try {
      customerService.delete(id);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/buyGoods")
  public ResponseEntity<String> buyGoods(@RequestBody Order order) {
    /*создание заказа на покупку товара*/
    orderService.createOrder(order);
    return new ResponseEntity<>("заказ успешно оплачен и ожидает обработки", HttpStatus.CREATED);
  }
}
