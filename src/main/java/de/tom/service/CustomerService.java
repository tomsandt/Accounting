package de.tom.service;

import de.tom.domain.Customer;
import de.tom.domain.CustomerDTO;
import de.tom.repository.DatabaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private DatabaseRepository databaseRepository;

    public Customer createCustomer(CustomerDTO customerDTO) {

        Customer newCustomer = new Customer();

        newCustomer.setFirstName(customerDTO.getFirstName());
        newCustomer.setLastName(customerDTO.getLastName());
        newCustomer.setAddress(customerDTO.getAddress());
        newCustomer.setZipCode(customerDTO.getZipCode());
        newCustomer.setCity(customerDTO.getCity());
        newCustomer.setEmailAddress(customerDTO.getEmailAddress());
        newCustomer.setPhoneNumber(customerDTO.getPhoneNumber());

        return databaseRepository.save(newCustomer);
    }

    public void deleteCustomer(int id) {
        databaseRepository.delete(Customer.class, id);
    }

    public Customer getCustomerById(int id) {
        return databaseRepository.findById(Customer.class, id);
    }

    public List<Customer> getAllCustomers() {
        return databaseRepository.findAll(Customer.class);
    }

}
