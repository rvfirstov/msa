package com.example.restservice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

  private int updateCounter = 1;

  private List<Map<String, String>> orders = new ArrayList<>() {{
    add(new HashMap<>() {{
      put("1", "burger");
    }});
    add(new HashMap<>() {{
      put("2", "pizza");
    }});
  }};

  private Map<String, String> getOrder(@PathVariable String id) {
    return orders.stream()
        .filter(o -> o.containsKey(id))
        .findFirst()
        .orElse(new HashMap<>() {{
          put(id, "Order not found");
        }});
  }

  @GetMapping
  public List<Map<String, String>> list() {
    return orders;
  }

  @GetMapping("{id}")
  public Map<String, String> getOne(@PathVariable String id) {
    return getOrder(id);
  }

  @PostMapping
  public Map<String, String> create(@RequestBody Map<String, String> order) {
    order.put("id", String.valueOf(updateCounter++));
    orders.add(order);
    return order;
  }

  @PutMapping("{id}")
  public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> order) {
    Map<String, String> orders = getOrder(id);
    orders.putAll(order);
    return order;
  }

  @DeleteMapping("{id}")
  public void delete(@PathVariable String id) {
    Map<String, String> order = getOrder(id);
    orders.remove(order);
  }
}
