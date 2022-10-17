package com.example.restservice.service;

import com.example.restservice.repository.EmployeesRepository;
import com.example.restservice.model.Employees;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeesService {

  private final EmployeesRepository employeesRepository;

  public List<Employees> getAll() {
    return employeesRepository.findAll();
  }

  public Optional<Employees> getById(Long id) {
    return employeesRepository.findById(id);
  }

  public Employees save(Employees employees) {
    return employeesRepository.save(employees);
  }

  public void delete(Long id) {
    employeesRepository.deleteById(id);
  }
}
