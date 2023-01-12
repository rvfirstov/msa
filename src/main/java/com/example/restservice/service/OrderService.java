package com.example.restservice.service;

import com.example.restservice.model.Order;
import com.example.restservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final CustomerService customerService;

  @Transactional
  public void createOrder(Order order) {
    /*проверка хватает ли денег у покупателя, если да, то выполняется обновление записи покупателя в БД*/
    customerService.updateCustomer(order);
    /*сохрание записи заказа в БД*/
    orderRepository.save(order);
  }
}


