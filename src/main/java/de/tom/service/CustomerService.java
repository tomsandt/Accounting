package de.tom.service;

import de.tom.domain.Article;
import de.tom.domain.Cost;
import de.tom.domain.Customer;
import de.tom.domain.CustomerDTO;
import de.tom.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(int id) {
        return customerRepository.findById(id);
    }

    public Customer createCustomer(CustomerDTO customerDTO) {

        Customer newCustomer = new Customer();

        newCustomer.setFirstName(customerDTO.getFirstName());
        newCustomer.setLastName(customerDTO.getLastName());
        newCustomer.setAddress(customerDTO.getAddress());
        newCustomer.setZipCode(customerDTO.getZipCode());
        newCustomer.setCity(customerDTO.getCity());
        newCustomer.setEmailAddress(customerDTO.getEmailAddress());
        newCustomer.setPhoneNumber(customerDTO.getPhoneNumber());

        return customerRepository.save(newCustomer);
    }

    public void deleteCustomer(int id) {
        if(!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with ID: " + id);
        }else {
            customerRepository.deleteById(id);
        }

    }

}
