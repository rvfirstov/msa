package com.example.restservice.controller;

import com.example.restservice.model.Employees;
import com.example.restservice.service.EmployeesService;
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
@RequestMapping("/v1/employees")
@RequiredArgsConstructor
public class EmployeesController {

  private final EmployeesService employeesService;

  @GetMapping
  public ResponseEntity<List<Employees>> getAll() {
    List<Employees> list = employeesService.getAll();
    if (list.isEmpty()) {
      return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(list, HttpStatus.OK);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employees> getById(@PathVariable("id") Long id) {
    Optional<Employees> optional = employeesService.getById(id);
    if (optional.isPresent()) {
      return new ResponseEntity<>(optional.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping
  public ResponseEntity<Employees> create(@RequestBody Employees employees) {
    return new ResponseEntity<>(employeesService.save(employees), HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Employees employeesDto) {
    Optional<Employees> optional = employeesService.getById(id);
    if (optional.isPresent()) {
      Employees employees = optional.get();
      employees.setName(employeesDto.getName());
      employees.setAge(employeesDto.getAge());
      return new ResponseEntity<>(employeesService.save(employees), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id) {
    try {
      employeesService.delete(id);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
}
