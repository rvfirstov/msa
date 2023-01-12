package com.example.restservice.service;

import com.example.restservice.model.Customer;
import com.example.restservice.model.Order;
import com.example.restservice.repository.CustomerRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository customerRepository;

  public List<Customer> getAll() {
    return customerRepository.findAll();
  }

  public Optional<Customer> getById(Long id) {
    return customerRepository.findById(id);
  }

  public Customer saveOrUpdate(Customer customer) {
    return customerRepository.save(customer);
  }

  public void delete(Long id) {
    customerRepository.deleteById(id);
  }

  @Transactional
  public void updateCustomer(Order order) {
    Optional<Customer> optional = getById(order.getCustomerId());
    if (optional.isPresent()) {
      Customer customer = optional.get();

      Integer customerMoney = customer.getMoney();
      Integer price = order.getPrice();
      if (customerMoney < price) {
        throw new RuntimeException("Не хватает денег");
      }

      int remainingMoney = customer.getMoney() - order.getPrice();
      customer.setMoney(remainingMoney);
      saveOrUpdate(customer);
    } else {
      throw new RuntimeException("Покупатель не зарегистрирован");
    }
  }
}
