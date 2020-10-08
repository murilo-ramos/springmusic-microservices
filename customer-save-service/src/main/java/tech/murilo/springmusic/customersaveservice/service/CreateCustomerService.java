package tech.murilo.springmusic.customersaveservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.murilo.springmusic.customersaveservice.model.Customer;
import tech.murilo.springmusic.customersaveservice.repository.CustomerRepository;

import java.util.UUID;

@Service
public class CreateCustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void save(Customer customer) {
        customer.setId(UUID.randomUUID().toString());
        customerRepository.save(customer);
    }
}
